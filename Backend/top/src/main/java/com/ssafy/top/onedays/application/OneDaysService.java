package com.ssafy.top.onedays.application;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.onedays.dto.request.TimeGoalRequest;
import com.ssafy.top.onedays.dto.response.*;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OneDaysService {

    private final OneDaysRepository oneDaysRepository;

    private final UsersRepository usersRepository;

    // 집중 시간 조회
    public CommonResponseDto<?> findTimeGoalByEmailAndPeriod(String email, String period) {

        Long userId = getUserByEmail(email).getId();
        if (!List.of("day", "week", "month").contains(period)) {
            throw new CustomException(INVALID_QUERY_STRING);
        }

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate startDay = switch (period) {
            case "week" -> today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            case "month" -> today.withDayOfMonth(1);
            default -> today;
        };

        int totalTimeGoal = oneDaysRepository.findTotalTimeGoalByUserIdAndDateDataBetween(userId, startDay, today);
        TimeGoalResponse timeGoalResponse = new TimeGoalResponse(totalTimeGoal);

        return new CommonResponseDto<>(timeGoalResponse, "목표 시간을 조회했습니다.", 200);
    }

    // 집중 시간 수정 및 추가
    public CommonResponseDto<?> updateTimeGoal(String email, TimeGoalRequest timeGoal){

        Users user = getUserByEmail(email);
        validateTimeGoal(timeGoal.getTimeGoal());

        OneDays oneDays = findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));
        oneDays.updateTargetTime(timeGoal.getTimeGoal() * 60);

        return new CommonResponseDto<>("정상적으로 목표 시간이 수정되었습니다.", 200);
    }

    private void validateTimeGoal(int timeGoalData) {
        if (!(0 <= timeGoalData && timeGoalData <= 1440)) {
            throw new CustomException(INVALID_TIME_GOAL);
        }
    }

    private Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    @Transactional
    public OneDays findOneDayByUserAndDateData(Users user, LocalDate date) {
        return oneDaysRepository.findByUserIdAndDateData(user.getId(), date)
                .orElseGet(() -> {
                    OneDays newOneDay = OneDays.builder()
                            .dateData(date)
                            .targetTime(0)
                            .focusTime(0)
                            .user(user)
                            .build();
                    oneDaysRepository.save(newOneDay);
                    return newOneDay;
                });
    }
}
