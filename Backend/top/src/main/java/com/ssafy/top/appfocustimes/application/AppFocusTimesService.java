package com.ssafy.top.appfocustimes.application;

import com.ssafy.top.appfocustimes.domain.AppFocusTimes;
import com.ssafy.top.appfocustimes.domain.AppFocusTimesRepository;
import com.ssafy.top.appfocustimes.dto.request.AppNameAndTimeRequest;
import com.ssafy.top.appfocustimes.dto.request.AppNameRequest;
import com.ssafy.top.appfocustimes.dto.response.AppAndTimeResponse;
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

    public CommonResponseDto<?> findAppFocusTimeListByLoginId(String loginId) {
        List<AppFocusTimes> appFocusTimesList = findAppFocusTimesByLoginId(loginId);
        int totalFocusTime = appFocusTimesList.stream()
                .mapToInt(AppFocusTimes::getFocusTime)
                .sum();

        AppListResponse[] appListResponses = appFocusTimesList.stream()
                .sorted(Comparator.comparingInt(AppFocusTimes::getFocusTime).reversed())
                .map(appFocusTimes -> {
                    int focusTime = appFocusTimes.getFocusTime();
                    int percentage = (int) Math.round((double) focusTime * 100 / totalFocusTime);
                    return new AppListResponse(
                            appFocusTimes.getApp(),
                            focusTime,
                            percentage
                    );
                })
                .toArray(AppListResponse[]::new);
        return new CommonResponseDto<>(appListResponses, "프로그램별 통계 조회에 성공했습니다.", 200);
    }

    private List<AppFocusTimes> findAppFocusTimesByLoginId(String loginId) {
        Long userId = usersRepository.findByEmail(loginId)
                .map(Users::getId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        LocalDate today = LocalDate.now();
        Long oneDayId = oneDaysRepository.findByUserIdAndDateData(userId, today)
                .map(OneDays::getId)
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

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

    private CommonResponseDto<?> saveFocusTime(String beforeAppName, OneDays oneDay, int timeInSeconds, String nowAppName) {
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

    public CommonResponseDto<?> saveCustomApp(String loginId, AppNameAndTimeRequest appNameAndTimeRequest){
        Long userId = usersRepository.findByEmail(loginId)
                .map(Users::getId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysRepository.findByUserIdAndDateData(userId, LocalDate.now())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        Optional<AppFocusTimes> appFocusTimes = appFocusTimesRepository.findByOneDaysIdAndApp(oneDay.getId(), appNameAndTimeRequest.getAppName());
        if(appFocusTimes.isPresent()){
            AppFocusTimes app = appFocusTimes.get();
            app.updateFocusTime(app.getFocusTime() + appNameAndTimeRequest.getFocusTime());
            return new CommonResponseDto<>(appFocusTimesRepository.save(app).getFocusTime(), "집중시간 데이터가 갱신되었습니다.", 200);
        } else {
            AppFocusTimes newAppFocusTimes = AppFocusTimes.builder()
                    .app(appNameAndTimeRequest.getAppName())
                    .startTime(0)
                    .focusTime(appNameAndTimeRequest.getFocusTime())
                    .oneDays(oneDay)
                    .build();
            return new CommonResponseDto<>(appFocusTimesRepository.save(newAppFocusTimes).getFocusTime(), "집중시간 데이터가 추가되었습니다.", 201);
        }
    }
}
