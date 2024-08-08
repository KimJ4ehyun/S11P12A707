package com.ssafy.top.appfocustimes.application;

import com.ssafy.top.appfocustimes.domain.AppFocusTimes;
import com.ssafy.top.appfocustimes.domain.AppFocusTimesRepository;
import com.ssafy.top.appfocustimes.dto.request.AppNameAndTimeRequest;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.ssafy.top.global.exception.ErrorCode.*;

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
                .orElseThrow(() -> new CustomException(ONE_DAY_NOT_FOUND));

        return appFocusTimesRepository.findByOneDaysId(oneDayId);
    }

    public CommonResponseDto<?> save(String loginId, AppNameRequest appNameRequest) {
        Long userId = usersRepository.findByEmail(loginId)
                .map(Users::getId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysRepository.findByUserIdAndDateData(userId, LocalDate.now())
                .orElseThrow(() -> new CustomException(ONE_DAY_NOT_FOUND));

        String prevAppName = getProcessedAppName(appNameRequest.getPrevAppName());
        String nowAppName = getProcessedAppName(appNameRequest.getNowAppName());

        return saveFocusTime(prevAppName, oneDay, nowAppName);
    }

    private CommonResponseDto<?> saveFocusTime(String prevAppName, OneDays oneDay, String nowAppName) {
        int timeInSeconds = LocalTime.now().toSecondOfDay();
        saveFocusTimePreviousApp(prevAppName, oneDay, timeInSeconds);
        boolean isCreated = isNowAppFocusTimeCreated(oneDay, timeInSeconds, nowAppName);

        String message = isCreated ? "집중시간 데이터가 생성되었습니다." : "집중시간 데이터가 갱신되었습니다.";
        int statusCode = isCreated ? 201 : 200;

        return new CommonResponseDto<>(message, statusCode);
    }

    private void saveFocusTimePreviousApp(String prevAppName, OneDays oneDay, int timeInSeconds) {
        if (prevAppName != null && !"None".equals(prevAppName)) {
            Optional<AppFocusTimes> optionalPrevAppFocusTime = appFocusTimesRepository.findByOneDaysIdAndApp(oneDay.getId(), prevAppName);
            if(optionalPrevAppFocusTime.isPresent()){
                AppFocusTimes prevAppFocusTime = optionalPrevAppFocusTime.get();
                int focusTime = timeInSeconds - prevAppFocusTime.getStartTime() + prevAppFocusTime.getFocusTime();
                prevAppFocusTime.updateFocusTime(focusTime);
                appFocusTimesRepository.save(prevAppFocusTime);
            } else {
                AppFocusTimes appFocusTime = appFocusTimesRepository.findLatestStartTimeByOneDaysIdOrderByFocusTime(oneDay.getId())
                        .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
                AppFocusTimes newAppFocusTime = AppFocusTimes.builder()
                        .app(prevAppName)
                        .startTime(timeInSeconds)
                        .focusTime(timeInSeconds - appFocusTime.getStartTime())
                        .oneDays(oneDay)
                        .build();
                appFocusTimesRepository.save(newAppFocusTime);
            }
        }
    }

    private boolean isNowAppFocusTimeCreated(OneDays oneDay, int timeInSeconds, String nowAppName) {
        if (nowAppName != null && !"None".equals(nowAppName)) {
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

    private static String getProcessedAppName(String appName) {
        if (!"None".equals(appName)) {
            return getDomainName(appName);
        }
        return appName;
    }

    private static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            if (domain == null) {
                return url;
            }
            return removePrefix(domain);
        } catch (URISyntaxException e) {
           return url;
        }
    }

    private static String removePrefix(String domain) {
        if (domain.startsWith("www.")) {
            domain = domain.substring(4);
        }
        int lastDotIndex = domain.lastIndexOf(".");
        if (lastDotIndex > 0) {
            int secondLastDotIndex = domain.lastIndexOf(".", lastDotIndex - 1);
            if (secondLastDotIndex > 0) {
                domain = domain.substring(secondLastDotIndex + 1);
            }
        }
        return domain;
    }
}
