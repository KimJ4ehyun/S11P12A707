package com.ssafy.top.appfocustimes.application;

import com.ssafy.top.appfocustimes.dao.AppAndTimeDao;
import com.ssafy.top.appfocustimes.domain.AppFocusTimes;
import com.ssafy.top.appfocustimes.domain.AppFocusTimesRepository;
import com.ssafy.top.appfocustimes.dto.request.AppNameAndTimeRequest;
import com.ssafy.top.appfocustimes.dto.request.AppNamePeriodRequest;
import com.ssafy.top.appfocustimes.dto.request.AppNameRequest;
import com.ssafy.top.appfocustimes.dto.response.*;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.onedays.application.OneDaysService;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AppFocusTimesService {

    private final OneDaysService oneDaysService;

    private final AppFocusTimesRepository appFocusTimesRepository;

    private final OneDaysRepository oneDaysRepository;

    private final UsersRepository usersRepository;

    public CommonResponseDto<?> findTotalFocusTimeByEmailAndPeriod(String email, String period) {

        Long userId = getUserByEmail(email).getId();

        if (!(period.equals("day") || period.equals("week") || period.equals("month"))) {
            throw new CustomException(INVALID_QUERY_STRING);
        }

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate yesterday = today.minusDays(1);
        LocalDate lastStartDay = yesterday;
        LocalDate lastEndDay = yesterday;

        int totalFocusTime = findTodayTotalFocusTimeByUserIdAndDateData(userId, today);

        if (period.equals("week")) {
            today = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            lastStartDay = today.minusWeeks(1);
            lastEndDay = today.minusDays(1);
        } else if(period.equals("month")) {
            today = today.withDayOfMonth(1);
            lastStartDay = today.minusMonths(1);
            lastEndDay = lastStartDay.with(TemporalAdjusters.lastDayOfMonth());
        }

        totalFocusTime += oneDaysRepository.findTotalFocusTimeByUserIdAndDateDataBetween(userId, today, yesterday);
        int lastTotalFocusTime = oneDaysRepository.findTotalFocusTimeByUserIdAndDateDataBetween(userId, lastStartDay, lastEndDay);

        PeriodTotalFocusTimeResponse totalFocusTimeResponse =
                new PeriodTotalFocusTimeResponse(formatTime(totalFocusTime), formatTime(lastTotalFocusTime));

        return new CommonResponseDto<>(totalFocusTimeResponse, "집중시간 통계 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> findFocusTimeList(String email, String period, Integer month) {
        Users user = getUserByEmail(email);
        LocalDate currentDay = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate today = currentDay;

        if(period != null){
            if (period.equals("day")) {
                OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

                List<Object[]> appFocusTimesList = appFocusTimesRepository.findUnitFocusTimeByOneDayId(oneDay.getId());

                FocusTimeListDayResponse[] focusTimeListDayResponses = appFocusTimesList.stream()
                        .map(appFocusTime -> new FocusTimeListDayResponse((int)appFocusTime[0], (long)appFocusTime[1]))
                        .toArray(FocusTimeListDayResponse[]::new);

                return new CommonResponseDto<>(focusTimeListDayResponses, "집중시간 통계 조회에 성공했습니다.", 200);

            } else if (period.equals("week")) {
                currentDay = currentDay.minusWeeks(1).plusDays(1);
            } else if (period.equals("month")) {
                currentDay = currentDay.minusMonths(1).plusDays(1);
            }
        } else if (month != null) {
            currentDay = currentDay.minusMonths(month).plusDays(1);
        }

        Long userId = user.getId();
        LocalDate yesterday = today.minusDays(1);

        List<Object[]> focusTimeList = oneDaysRepository.findFocusTimeListByUserIdAndDateDataBetween(userId, currentDay, yesterday);

        FocusTimeListResponse[] focusTimeListResponses = new FocusTimeListResponse[focusTimeList.size()+1];

        for(int i = 0; i < focusTimeListResponses.length-1; i++){
            LocalDate dateData = (LocalDate) focusTimeList.get(i)[0];
            String formattedDate = dateData.format(DateTimeFormatter.ofPattern("MM-dd"));
            focusTimeListResponses[i] = new FocusTimeListResponse(formattedDate, (long)focusTimeList.get(i)[1]);
        }

        String formattedDateToday = today.format(DateTimeFormatter.ofPattern("MM-dd"));
        focusTimeListResponses[focusTimeListResponses.length-1] = new FocusTimeListResponse(formattedDateToday, (long)findTodayTotalFocusTimeByUserIdAndDateData(userId, today));

        return new CommonResponseDto<>(focusTimeListResponses, "집중시간 통계 조회에 성공했습니다.", 200);
    }

    public int findTodayTotalFocusTimeByUserIdAndDateData(Long userId, LocalDate today){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Long oneDayId = oneDaysService.findOneDayByUserAndDateData(user, today).getId();

        return appFocusTimesRepository.findTodayTotalFocusTimeByOneDayId(oneDayId);
    }

    public int findTodayTotalFocusTimeForScheduleByUserIdAndDateData(Long userId, LocalDate today){
        Optional<OneDays> oneDaysOptional = oneDaysRepository.findByUserIdAndDateData(userId, today);

        if (oneDaysOptional.isEmpty()) {
            return 0;
        }

        Long oneDayId = oneDaysOptional.get().getId();

        return appFocusTimesRepository.findTodayTotalFocusTimeByOneDayId(oneDayId);
    }


    public CommonResponseDto<?> findWholeTotalFocusTimeByEmail(String email){

        Long userId = getUserByEmail(email).getId();

        int totalFocusTime = oneDaysRepository.findWholeTotalFocusTimeByUserId(userId);

        totalFocusTime += findTodayTotalFocusTimeByUserIdAndDateData(userId, LocalDate.now(ZoneId.of("Asia/Seoul")));

        TotalFocusTimeResponse totalFocusTimeResponse = new TotalFocusTimeResponse(formatTime(totalFocusTime));

        return new CommonResponseDto<>(totalFocusTimeResponse, "전체 집중시간의 합을 조회했습니다.", 200);
    }

    public CommonResponseDto<?> findFocusTimeListByEmailAndPeriod(String email, String period) {

        if (!(period.equals("day") || period.equals("week") || period.equals("month"))) {
            throw new CustomException(INVALID_QUERY_STRING);
        }

        return findFocusTimeList(email, period, null);
    }

    public CommonResponseDto<?> findFocusTimeListByEmailAndMonth(String email, int month) {

        if(!(month == 1 || month == 6)){
            throw new CustomException(INVALID_QUERY_STRING);
        }

        return findFocusTimeList(email, null, month);
    }

    public CommonResponseDto<?> findAppFocusTimeListByEmail(String email) {
        List<AppAndTimeDao> appFocusTimesList = findAppFocusTimesAndRateByEmail(email);
        long totalFocusTime = appFocusTimesList.stream()
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

    public CommonResponseDto<?> findFocusTimePercentByEmail(String email) {
        Long userId = getUserByEmail(email).getId();

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        int totalUser = usersRepository.findTotalUserCount();

        int dayPercent = appFocusTimesRepository.findRankByDateDataBetween(userId, today, today) * 100 / totalUser;

        LocalDate weekFirstDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        int weekPercent = appFocusTimesRepository.findRankByDateDataBetween(userId, weekFirstDate, today) * 100 / totalUser;

        LocalDate monthFirstDate = today.withDayOfMonth(1);
        int monthPercent = appFocusTimesRepository.findRankByDateDataBetween(userId, monthFirstDate, today) * 100 / totalUser;

        FocusTimePercentResponse focusTimePercentResponse = new FocusTimePercentResponse(dayPercent, weekPercent, monthPercent);
        return new CommonResponseDto<>(focusTimePercentResponse, "일간, 주간, 월간 백분율 조회에 성공했습니다.", 200);
    }

    @Transactional(readOnly = true)
    public CommonResponseDto<?> findFocusTimeListByEmailAndYearAndMonth(String email, int year, int month, int day){
        Long userId = getUserByEmail(email).getId();

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        isValidDate(year, month, day, today);

        LocalDate date = LocalDate.of(year, month, day);
        int time;
        if (!date.equals(LocalDate.now(ZoneId.of("Asia/Seoul")))) {
            time = appFocusTimesRepository.findTotalFocusTimeByUserIdAndDateData(userId, date);
        } else {
            time = oneDaysRepository.findTotalFocusTimeByUserIdAndDateData(userId, date);
        }


        return new CommonResponseDto<>(time, "캘린더 데이터 조회에 성공했습니다.", 200);
    }

    private List<AppAndTimeDao> findAppFocusTimesAndRateByEmail(String email) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

        return appFocusTimesRepository.findAppTimeByOneDaysId(oneDay.getId());
    }

    public CommonResponseDto<?> updateAppFocusTime(String email, AppNameRequest appNameRequest) {

        //유저 조회
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        //오늘의 집중시간 데이터 조회
        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

        //URI 소문자 변경
        String appName = appNameRequest.getAppName();

        // case: 이전 앱이 있을 경우
        if (appName != null && !"None".equals(appName)) {
            //이전 앱 이름과 같은 시간 중에 가장 최근의 시작 시간인 데이터 조회
            appName = getProcessedAppName(appName).toLowerCase();

            int startTime = convertToSeconds(appNameRequest.getStartTime());
            int endTime = convertToSeconds(appNameRequest.getEndTime());

            //하루를 기준으로 업데이트
            updateFocusTime(oneDay, appName, startTime, endTime);
        }
        return new CommonResponseDto<>("집중시간 데이터가 갱신되었습니다.", 200);
    }

    public int convertToSeconds(String dateTimeString) {
        // 시간 부분만 추출
        String timePart = dateTimeString.substring(11, 19);

        // 문자열을 LocalTime 객체로 변환
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse(timePart, timeFormatter);

        // 초 단위로 변환
        return time.toSecondOfDay();
    }

    public CommonResponseDto<?> saveCustomApp(String email, AppNameAndTimeRequest appNameAndTimeRequest) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));
        int nowTime = LocalTime.now(ZoneId.of("Asia/Seoul")).toSecondOfDay();

        updateFocusTime(oneDay, appNameAndTimeRequest.getAppName(), nowTime - appNameAndTimeRequest.getFocusTime(), nowTime);

        return new CommonResponseDto<>("집중시간 데이터가 처리되었습니다.", 200);
    }

    public CommonResponseDto<?> updateFocusTimePeriodically(String email, AppNamePeriodRequest appNamePeriodRequest) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        OneDays oneDay = oneDaysService.findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

        AppFocusTimes appFocusTimes = findAppFocusTimeByOneDaysAndNameAndTimeUnit(oneDay,
                appNamePeriodRequest.getAppName(),
                LocalTime.now(ZoneId.of("Asia/Seoul")).getHour(),
                LocalTime.now(ZoneId.of("Asia/Seoul")).toSecondOfDay());

        int nowTime = LocalTime.now(ZoneId.of("Asia/Seoul")).toSecondOfDay();
        appFocusTimes.updateFocusTime(nowTime - appFocusTimes.getStartTime() + appFocusTimes.getFocusTime());
        appFocusTimes.updateStartTime(nowTime);

        return new CommonResponseDto<>("집중 시간이 업데이트 되었습니다.", 200);
    }

    /**
     * @param oneDay    집중 시간 저장할 하루 데이터
     * @param app       집중한 앱
     * @param startTime 앱 시작 시간
     * @param endTime   앱 종료 시간
     */
    public void updateFocusTime(OneDays oneDay, String app, int startTime, int endTime) {

        if (startTime < 0) startTime += 3600 * 24;
        if (endTime < 0) endTime += 3600 * 24;

        if (startTime > endTime) {
            updateFocusTime(oneDay, app, startTime, 3600 * 24);
            updateFocusTime(oneDay, app, 0, endTime);
            return;
        }

        //time_unit을 계산하기 위한 로직
        int startHour = startTime / 3600;
        int endHour = endTime / 3600;

        //단위 시간이 같을 경우
        if (startHour == endHour) {

            //AppFocusTime 조회
            AppFocusTimes appFocusTimes = findAppFocusTimeByOneDaysAndNameAndTimeUnit(oneDay, app, startHour, endTime);

            //집중시간 endTime - startTime
            //+ 기존 집중 시간
            appFocusTimes.updateFocusTime(endTime - startTime + appFocusTimes.getFocusTime());

            // 다를 경우
        } else {

            //AppFocusTime 조회
            AppFocusTimes appFocusTimeStart = findAppFocusTimeByOneDaysAndNameAndTimeUnit(oneDay, app, startHour, endTime);

            //시작시간(넘어가기 전 단위시간 + 1)을 초단위 변환후 집중 시작시간을 빼고 집중시간을 더함
            appFocusTimeStart.updateFocusTime((startHour + 1) * 3600 - startTime + appFocusTimeStart.getFocusTime());

            //AppFocusTime 조회
            AppFocusTimes appFocusTimeEnd = findAppFocusTimeByOneDaysAndNameAndTimeUnit(oneDay, app, endHour, endTime);

            //집중이 끝난 시간에서 (넘어간 후 단위시간)을 초단위 변환후 뺴주고 집중 시간을 더함
            appFocusTimeEnd.updateFocusTime(endTime - endHour * 3600 + appFocusTimeEnd.getFocusTime());
        }
    }

    /**
     * @param oneDay    집중 시간 저장할 하루 데이터
     * @param app       집중한 어플
     * @param hour      단위 시간
     * @param startTime 어플 시작 시간
     * @return AppFocusTime 객체
     */
    public AppFocusTimes findAppFocusTimeByOneDaysAndNameAndTimeUnit(OneDays oneDay, String app, int hour, int startTime) {

        return appFocusTimesRepository.findByOneDaysIdAndTimeUnitAndApp(oneDay.getId(), hour, app)
                .orElseGet(() -> {
                    AppFocusTimes newHourFocusTimes = AppFocusTimes.builder()
                            .app(app)
                            .startTime(startTime)
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

    private static void isValidDate(int year, int month, int day, LocalDate today) {
        try {
            LocalDate date = LocalDate.of(year, month, day);

            if(date.isAfter(today)) { // 오늘 이후 날짜
                throw new CustomException(INVALID_QUERY_STRING);
            }
        } catch (DateTimeException e) { // 유효하지 않는 날짜 형식
            throw new CustomException(INVALID_QUERY_STRING);
        }
    }

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
