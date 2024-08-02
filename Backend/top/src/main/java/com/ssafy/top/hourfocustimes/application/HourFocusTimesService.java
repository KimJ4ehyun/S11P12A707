package com.ssafy.top.hourfocustimes.application;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.hourfocustimes.domain.HourFocusTimes;
import com.ssafy.top.hourfocustimes.domain.HourFocusTimesRepository;
import com.ssafy.top.hourfocustimes.dto.request.FocusTimeRequest;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import com.ssafy.top.hourfocustimes.dto.request.HourRequest;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.users.domain.Users;

import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class HourFocusTimesService {

    private final HourFocusTimesRepository hourFocusTimesRepository;

    private final OneDaysRepository oneDaysRepository;

    private final UsersRepository usersRepository;

    public CommonResponseDto<?> updateFocusTime(String loginId, FocusTimeRequest focusTimeRequest){
        Long userId = usersRepository.findByEmail(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND))
                .getId();

        Long oneDayId = oneDaysRepository.findByUserIdAndDateData(userId, LocalDate.now())
                .orElseThrow(() -> new CustomException(ONE_DAY_NOT_FOUND))
                .getId();

        HourFocusTimes hourFocusTimes = hourFocusTimesRepository.findByOneDaysIdAndTimeUnit(oneDayId, LocalTime.now().getHour())
                .orElseThrow(() -> new CustomException(HOUR_FOCUS_TIME_NOT_FOUND));

        int focusTime = focusTimeRequest.getFocusTime();

        HourFocusTimes updateHourFocusTimes = HourFocusTimes.builder()
                .id(hourFocusTimes.getId())
                .focusTime(hourFocusTimes.getFocusTime() + focusTime)
                .timeUnit(hourFocusTimes.getTimeUnit())
                .oneDays(hourFocusTimes.getOneDays())
                .build();
        hourFocusTimesRepository.save(updateHourFocusTimes);
        return new CommonResponseDto<>("집중 시간이 업데이트 되었습니다.", 200);
    }

    public CommonResponseDto<?> save(String loginId, HourRequest hourRequest){
        Long userId = usersRepository.findByEmail(loginId)
                .map(Users::getId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDays = oneDaysRepository.findByUserIdAndDateData(userId, LocalDate.now())
                .orElseThrow(() -> new CustomException(ONE_DAY_NOT_FOUND));

        int hour = hourRequest.getHour();

        if(hourFocusTimesRepository.findByOneDaysIdAndTimeUnit(oneDays.getId(), hour).isPresent()) {
            throw new CustomException(ALREADY_EXISTS);
        } else {
            HourFocusTimes hourFocusTimes = HourFocusTimes.builder()
                    .focusTime(0)
                    .timeUnit(hour)
                    .oneDays(oneDays)
                    .build();
            hourFocusTimesRepository.save(hourFocusTimes);
            return new CommonResponseDto<>("기준 시간이 추가되었습니다", 201);
        }
    }
}
