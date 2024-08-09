package com.ssafy.top.hourfocustimes.application;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.hourfocustimes.domain.HourFocusTimes;
import com.ssafy.top.hourfocustimes.domain.HourFocusTimesRepository;
import com.ssafy.top.hourfocustimes.dto.request.FocusTimeRequest;
import com.ssafy.top.onedays.application.OneDaysService;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import com.ssafy.top.hourfocustimes.dto.request.HourRequest;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.users.domain.Users;

import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class HourFocusTimesService {

    private final OneDaysService oneDaysService;

    private final HourFocusTimesRepository hourFocusTimesRepository;

    private final UsersRepository usersRepository;

    public CommonResponseDto<?> updateFocusTime(String email, FocusTimeRequest focusTimeRequest){
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

        HourFocusTimes hourFocusTimes = findHourFocusTimeByOneDays(oneDay);

        HourFocusTimes updateHourFocusTimes = HourFocusTimes.builder()
                .id(hourFocusTimes.getId())
                .focusTime(hourFocusTimes.getFocusTime() + focusTimeRequest.getFocusTime())
                .timeUnit(hourFocusTimes.getTimeUnit())
                .oneDays(hourFocusTimes.getOneDays())
                .build();
        hourFocusTimesRepository.save(updateHourFocusTimes);
        return new CommonResponseDto<>("집중 시간이 업데이트 되었습니다.", 200);
    }

    public CommonResponseDto<?> save(String email, HourRequest hourRequest){
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

        int hour = hourRequest.getHour();

        if(hourFocusTimesRepository.findByOneDaysIdAndTimeUnit(oneDay.getId(), hour).isPresent()) {
            throw new CustomException(ALREADY_EXISTS);
        } else {
            HourFocusTimes hourFocusTimes = HourFocusTimes.builder()
                    .focusTime(0)
                    .timeUnit(hour)
                    .oneDays(oneDay)
                    .build();
            hourFocusTimesRepository.save(hourFocusTimes);
            return new CommonResponseDto<>("기준 시간이 추가되었습니다", 201);
        }
    }

    public HourFocusTimes findHourFocusTimeByOneDays(OneDays oneDay){
        return hourFocusTimesRepository.findByOneDaysIdAndTimeUnit(oneDay.getId(), LocalTime.now(ZoneId.of("Asia/Seoul")).getHour())
                .orElseGet(() -> {
                    HourFocusTimes newHourFocusTimes = HourFocusTimes.builder()
                            .focusTime(0)
                            .timeUnit(LocalTime.now(ZoneId.of("Asia/Seoul")).getHour())
                            .oneDays(oneDay)
                            .build();
                    hourFocusTimesRepository.save(newHourFocusTimes);
                    return newHourFocusTimes;
                });
    }

}
