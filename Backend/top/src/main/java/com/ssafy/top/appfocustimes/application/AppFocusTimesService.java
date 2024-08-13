package com.ssafy.top.appfocustimes.application;

import com.ssafy.top.appfocustimes.dao.AppAndTimeDao;
import com.ssafy.top.appfocustimes.domain.AppFocusTimes;
import com.ssafy.top.appfocustimes.domain.AppFocusTimesRepository;
import com.ssafy.top.appfocustimes.dto.request.AppNameAndTimeRequest;
import com.ssafy.top.appfocustimes.dto.request.AppNamePeriodRequest;
import com.ssafy.top.appfocustimes.dto.request.AppNameRequest;
import com.ssafy.top.appfocustimes.dto.response.AppListResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.onedays.application.OneDaysService;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AppFocusTimesService {

    private final OneDaysService oneDaysService;

    private final AppFocusTimesRepository appFocusTimesRepository;

    private final UsersRepository usersRepository;

    public CommonResponseDto<?> findAppFocusTimeListByEmail(String email) {

        List<AppAndTimeDao> appFocusTimesList = findAppFocusTimesAndRateByEmail(email);
        Long totalFocusTime = appFocusTimesList.stream()
                .mapToLong(AppAndTimeDao::getFocusTime)
                .sum();

        AppListResponse[] appListResponses = appFocusTimesList.stream()
                .sorted(Comparator.comparingLong(AppAndTimeDao::getFocusTime).reversed())
                .map(appFocusTimes -> {
                    long focusTime = appFocusTimes.getFocusTime();
                    int percentage = (int) Math.round((double) focusTime * 100 / totalFocusTime);
                    return new AppListResponse(
                            appFocusTimes.getAppName(),
                            (int) focusTime,
                            percentage
                    );
                })
                .toArray(AppListResponse[]::new);

        return new CommonResponseDto<>(appListResponses, "프로그램별 통계 조회에 성공했습니다.", 200);
    }

    private List<AppAndTimeDao> findAppFocusTimesAndRateByEmail(String email) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

        return appFocusTimesRepository.findAppTimeByOneDaysId(oneDay.getId());
    }

    public CommonResponseDto<?> updateAppFocusTime(String email, AppNameRequest appNameRequest) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

        String prevAppName = getProcessedAppName(appNameRequest.getPrevAppName()).toLowerCase();
        String nowAppName = getProcessedAppName(appNameRequest.getNowAppName()).toLowerCase();

        return updateFocusTime(prevAppName, oneDay, nowAppName);
    }

    private CommonResponseDto<?> updateFocusTime(String prevAppName, OneDays oneDay, String nowAppName) {

        int timeInSeconds = LocalTime.now(ZoneId.of("Asia/Seoul")).toSecondOfDay();
        saveFocusTimePreviousApp(prevAppName, oneDay, timeInSeconds);
        boolean isCreated = isNowAppFocusTimeCreated(oneDay, timeInSeconds, nowAppName);

        String message = isCreated ? "집중시간 데이터가 생성되었습니다." : "집중시간 데이터가 갱신되었습니다.";
        int statusCode = isCreated ? 201 : 200;

        return new CommonResponseDto<>(message, statusCode);
    }

    private void saveFocusTimePreviousApp(String prevAppName, OneDays oneDay, int timeInSeconds) {

        if (prevAppName != null && !"none".equals(prevAppName)) {
            Optional<Integer> optionalPrevAppFocusTime =
                    appFocusTimesRepository.findLatestStartTimeByOneDaysIdAndApp(oneDay.getId(), prevAppName);

            if(optionalPrevAppFocusTime.isPresent()){
                Integer prevAppStartTime = optionalPrevAppFocusTime.get();
                updateFocusTime(oneDay, prevAppName, prevAppStartTime, timeInSeconds);
            } else {
                int appFocusTime = appFocusTimesRepository.findLatestStartTimeByOneDaysId(oneDay.getId())
                        .orElse(timeInSeconds);
                updateFocusTime(oneDay, prevAppName, appFocusTime, timeInSeconds);
            }
        }
    }

    private boolean isNowAppFocusTimeCreated(OneDays oneDay, int timeInSeconds, String nowAppName) {

        if (nowAppName != null && !"none".equals(nowAppName)) {
            Optional<AppFocusTimes> appFocusTimesOptional =
                    appFocusTimesRepository.findByOneDaysIdAndTimeUnitAndApp(oneDay.getId(), timeInSeconds / 3600, nowAppName);

            if (appFocusTimesOptional.isPresent()) {
                AppFocusTimes nowAppFocusTimes = appFocusTimesOptional.get();
                nowAppFocusTimes.updateStartTime(timeInSeconds);
            } else {
                AppFocusTimes newAppFocusTimes = AppFocusTimes.builder()
                        .app(nowAppName)
                        .startTime(timeInSeconds)
                        .focusTime(0)
                        .timeUnit(timeInSeconds / 3600)
                        .oneDays(oneDay)
                        .build();
                appFocusTimesRepository.save(newAppFocusTimes);
                return true;
            }
        }
        return false;
    }

    public CommonResponseDto<?> saveCustomApp(String email, AppNameAndTimeRequest appNameAndTimeRequest){

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));
        int nowTime = LocalTime.now(ZoneId.of("Asia/Seoul")).toSecondOfDay();

        updateFocusTime(oneDay, appNameAndTimeRequest.getAppName(), nowTime - appNameAndTimeRequest.getFocusTime() ,nowTime);

        return new CommonResponseDto<>("집중시간 데이터가 처리되었습니다.", 200);
    }

    public CommonResponseDto<?> updateFocusTimePeriodically(String email, AppNamePeriodRequest appNamePeriodRequest){

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

        AppFocusTimes appFocusTimes =
                findAppFocusTimeByOneDaysAndNameAndTimeUnit(oneDay, appNamePeriodRequest.getAppName(), LocalTime.now(ZoneId.of("Asia/Seoul")).getHour());

        int nowTime = LocalTime.now(ZoneId.of("Asia/Seoul")).toSecondOfDay();
        appFocusTimes.updateFocusTime(nowTime - appFocusTimes.getStartTime() + appFocusTimes.getFocusTime());
        appFocusTimes.updateStartTime(nowTime);

        return new CommonResponseDto<>("집중 시간이 업데이트 되었습니다.", 200);
    }

    public void updateFocusTime(OneDays oneDay, String app, int startTime, int endTime){

        if(startTime < 0) startTime += 3600 * 24;
        if(endTime < 0) endTime += 3600 * 24;

        if (startTime > endTime) {
            updateFocusTime(oneDay, app, startTime, 3600 * 24);
            updateFocusTime(oneDay, app,0, endTime);
            return;
        }

        int startHour = startTime / 3600;
        int endHour = endTime / 3600;

        if (startHour == endHour) {
            AppFocusTimes appFocusTimes = findAppFocusTimeByOneDaysAndNameAndTimeUnit(oneDay, app, startHour);
            appFocusTimes.updateFocusTime(endTime - startTime + appFocusTimes.getFocusTime());

        } else {
            AppFocusTimes appFocusTimeStart = findAppFocusTimeByOneDaysAndNameAndTimeUnit(oneDay, app, startHour);
            appFocusTimeStart.updateFocusTime((startHour + 1) * 3600 - startTime + appFocusTimeStart.getFocusTime());

            AppFocusTimes appFocusTimeEnd = findAppFocusTimeByOneDaysAndNameAndTimeUnit(oneDay, app, endHour);
            appFocusTimeEnd.updateFocusTime(endTime - endHour * 3600 + appFocusTimeEnd.getFocusTime());
        }
    }

    public AppFocusTimes findAppFocusTimeByOneDaysAndNameAndTimeUnit(OneDays oneDay, String app, int hour){

        return appFocusTimesRepository.findByOneDaysIdAndTimeUnitAndApp(oneDay.getId(), hour, app)
                .orElseGet(() -> {
                    AppFocusTimes newHourFocusTimes = AppFocusTimes.builder()
                            .app(app)
                            .startTime(0)
                            .focusTime(0)
                            .timeUnit(hour)
                            .oneDays(oneDay)
                            .build();
                    appFocusTimesRepository.save(newHourFocusTimes);
                    return newHourFocusTimes;
                });
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
            return domain;
        } catch (URISyntaxException e) {
           return url;
        }
    }
}
