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

    public CommonResponseDto<?> updateFocusTimePeriodically(String email, FocusTimeRequest focusTimeRequest){
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

        HourFocusTimes hourFocusTimes = findHourFocusTimeByOneDays(oneDay, LocalTime.now(ZoneId.of("Asia/Seoul")).getHour());

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

    public void updateFocusTime(OneDays oneDay, int startTime, int endTime){
        if(startTime < 0) startTime += 3600 * 24;
        if(endTime < 0) endTime += 3600 * 24;

        if (startTime > endTime) {
            updateFocusTime(oneDay, startTime, 3600 * 24);
            updateFocusTime(oneDay, 0, endTime);
            return;
        }

        int startHour = startTime / 3600;
        int endHour = endTime / 3600;

        if (startHour == endHour) {
            HourFocusTimes hourFocusTimes = findHourFocusTimeByOneDays(oneDay, startHour);
            hourFocusTimes.updateFocusTime(endTime - startTime + hourFocusTimes.getFocusTime());
            hourFocusTimesRepository.save(hourFocusTimes);
        } else {
            HourFocusTimes hourFocusTimeStart = findHourFocusTimeByOneDays(oneDay, startHour);
            hourFocusTimeStart.updateFocusTime((startHour + 1) * 3600 - startTime + hourFocusTimeStart.getFocusTime());
            hourFocusTimesRepository.save(hourFocusTimeStart);

            HourFocusTimes hourFocusTimeEnd = findHourFocusTimeByOneDays(oneDay, endHour);
            hourFocusTimeEnd.updateFocusTime(endTime - endHour * 3600 + hourFocusTimeEnd.getFocusTime());
            hourFocusTimesRepository.save(hourFocusTimeEnd);
        }
    }

    public HourFocusTimes findHourFocusTimeByOneDays(OneDays oneDay, int hour){
        return hourFocusTimesRepository.findByOneDaysIdAndTimeUnit(oneDay.getId(), hour)
                .orElseGet(() -> {
                    HourFocusTimes newHourFocusTimes = HourFocusTimes.builder()
                            .focusTime(0)
                            .timeUnit(hour)
                            .oneDays(oneDay)
                            .build();
                    hourFocusTimesRepository.save(newHourFocusTimes);
                    return newHourFocusTimes;
                });
    }

}
