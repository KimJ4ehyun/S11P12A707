package com.ssafy.top.appfocustimes.application;

import com.ssafy.top.appfocustimes.domain.AppFocusTimes;
import com.ssafy.top.appfocustimes.domain.AppFocusTimesRepository;
import com.ssafy.top.appfocustimes.dto.request.AppNameRequest;
import com.ssafy.top.appfocustimes.dto.response.AppListResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ssafy.top.global.exception.ErrorCode.DATA_NOT_FOUND;
import static com.ssafy.top.global.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AppFocusTimesService {

    private final AppFocusTimesRepository appFocusTimesRepository;

    private final OneDaysRepository oneDaysRepository;

    private final UsersRepository usersRepository;

    public AppListResponse[] findTopThreeByAppFocusTimeList(String loginId) {
        List<AppFocusTimes> appFocusTimesList = findAppFocusTimesByLoginId(loginId);
        int totalFocusTime = appFocusTimesList.stream()
                .mapToInt(AppFocusTimes::getFocusTime)
                .sum();

        return appFocusTimesList.stream()
                .sorted(Comparator.comparingInt(AppFocusTimes::getFocusTime).reversed())
                .limit(3)
                .map(appFocusTimes -> new AppListResponse(
                        appFocusTimes.getApp(),
                        appFocusTimes.getFocusTime(),
                        appFocusTimes.getFocusTime() * 100 / totalFocusTime
                ))
                .toArray(AppListResponse[]::new);
    }

    private List<AppFocusTimes> findAppFocusTimesByLoginId(String loginId) {
        Long userId = usersRepository.findByEmail(loginId)
                .map(Users::getId)
                .orElseThrow(() -> new NoSuchElementException("해당 로그인 아이디가 존재하지 않습니다: " + loginId));

        LocalDate today = LocalDate.now();
        Long oneDayId = oneDaysRepository.findByUserIdAndDateData(userId, today)
                .map(OneDays::getId)
                .orElseThrow(() -> new NoSuchElementException("해당 날짜에 해당하는 데이터가 없습니다: " + today));

        return appFocusTimesRepository.findByOneDaysId(oneDayId);
    }

    public CommonResponseDto<?> save(String loginId, AppNameRequest appNameRequest) {
        Long userId = usersRepository.findByEmail(loginId)
                .map(Users::getId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysRepository.findByUserIdAndDateData(userId, LocalDate.now())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        String beforeAppName = appNameRequest.getBeforeAppName();
        String nowAppName = appNameRequest.getNowAppName();

        return saveFocusTime(beforeAppName, oneDay, LocalTime.now().toSecondOfDay(), nowAppName);
    }

    private CommonResponseDto<Object> saveFocusTime(String beforeAppName, OneDays oneDay, int timeInSeconds, String nowAppName) {
        saveFocusTimeBeforeApp(beforeAppName, oneDay, timeInSeconds);
        boolean isCreated = isNowAppFocusTimeCreated(oneDay, timeInSeconds, nowAppName);

        String message = isCreated ? "집중시간 데이터가 생성되었습니다." : "집중시간 데이터가 갱신되었습니다.";
        int statusCode = isCreated ? 201 : 200;

        return new CommonResponseDto<>(message, statusCode);
    }

    private void saveFocusTimeBeforeApp(String beforeAppName, OneDays oneDay, int timeInSeconds) {
        if (beforeAppName != null) {
            AppFocusTimes beforeAppFocusTime = appFocusTimesRepository.findByOneDaysIdAndApp(oneDay.getId(), beforeAppName)
                    .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

            int focusTime = timeInSeconds - beforeAppFocusTime.getStartTime() + beforeAppFocusTime.getFocusTime();
            beforeAppFocusTime.updateFocusTime(focusTime);
            appFocusTimesRepository.save(beforeAppFocusTime);
        }
    }

    private boolean isNowAppFocusTimeCreated(OneDays oneDay, int timeInSeconds, String nowAppName) {
        if (nowAppName != null) {
            Optional<AppFocusTimes> appFocusTimesOptional = appFocusTimesRepository.findByOneDaysIdAndApp(oneDay.getId(), nowAppName);

            if (appFocusTimesOptional.isPresent()) {
                AppFocusTimes nowAppFocusTimes = appFocusTimesOptional.get();
                nowAppFocusTimes.updateStartTime(timeInSeconds);
                appFocusTimesRepository.save(nowAppFocusTimes);
            } else {
                AppFocusTimes newAppFocusTimes = AppFocusTimes.builder()
                        .app(nowAppName)
                        .startTime(timeInSeconds)
                        .focusTime(0)
                        .oneDays(oneDay)
                        .build();

                appFocusTimesRepository.save(newAppFocusTimes);
                return true;
            }
        }
        return false;
    }
}