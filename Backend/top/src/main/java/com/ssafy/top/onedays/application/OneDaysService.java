package com.ssafy.top.onedays.application;

import com.ssafy.top.appfocustimes.dao.AppFocusTimeSumDao;
import com.ssafy.top.appfocustimes.domain.AppFocusTimesRepository;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.onedays.dto.request.TimeGoalRequest;
import com.ssafy.top.onedays.dto.response.*;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OneDaysService {

    private final OneDaysRepository oneDaysRepository;

    private final UsersRepository usersRepository;

    private final AppFocusTimesRepository appFocusTimesRepository;

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

    public CommonResponseDto<?> findWholeTotalFocusTimeByEmail(String email){

        Long userId = getUserByEmail(email).getId();

        int totalFocusTime = oneDaysRepository.findWholeTotalFocusTimeByUserIdExcludingToday(userId);

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

    public CommonResponseDto<?> findFocusTimeList(String email, String period, Integer month) {
        Users user = getUserByEmail(email);
        LocalDate currentDay = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate today = currentDay;

        if(period != null){
            if (period.equals("day")) {
                OneDays oneDay = findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));

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

        List<OneDays> oneDaysList = oneDaysRepository.findByUserIdAndDateDataBetween(userId, currentDay, yesterday);

        FocusTimeListResponse[] focusTimeListResponses = new FocusTimeListResponse[oneDaysList.size()+1];

        for(int i = 0; i < focusTimeListResponses.length-1; i++){
            String formattedDate = oneDaysList.get(i).getDateData().format(DateTimeFormatter.ofPattern("MM-dd"));
            focusTimeListResponses[i] = new FocusTimeListResponse(formattedDate, oneDaysList.get(i).getFocusTime());
        }

        String formattedDateToday = today.format(DateTimeFormatter.ofPattern("MM-dd"));
        focusTimeListResponses[focusTimeListResponses.length-1] = new FocusTimeListResponse(formattedDateToday, findTodayTotalFocusTimeByUserIdAndDateData(userId, today));

        return new CommonResponseDto<>(focusTimeListResponses, "집중시간 통계 조회에 성공했습니다.", 200);
    }

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

        List<OneDays> oneDaysList = oneDaysRepository.findByUserIdAndDateDataBetween(userId, startDay, today);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        TimeGoalAndDayResponse[] timeGoalAndDayResponses = oneDaysList.stream()
                .map(oneDay -> new TimeGoalAndDayResponse(oneDay.getDateData().format(formatter), oneDay.getTargetTime()))
                .toArray(TimeGoalAndDayResponse[]::new);

        return new CommonResponseDto<>(timeGoalAndDayResponses, "목표 시간을 조회했습니다.", 200);
    }

    public CommonResponseDto<?> updateTimeGoal(String email, TimeGoalRequest timeGoal){

        Users user = getUserByEmail(email);
        validateTimeGoal(timeGoal.getTimeGoal());

        OneDays oneDays = findOneDayByUserAndDateData(user, LocalDate.now(ZoneId.of("Asia/Seoul")));
        oneDays.updateTargetTime(timeGoal.getTimeGoal());

        return new CommonResponseDto<>("정상적으로 목표 시간이 수정되었습니다.", 200);
    }

    public CommonResponseDto<?> findFocusTimePercentByEmail(String email) {

        Long userId = getUserByEmail(email).getId();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        List<AppFocusTimeSumDao> todayFocusTimeList = appFocusTimesRepository.findAllUsersFocusTimeSum(today);

        Map<Long, Long> todayFocusTimeMap = new HashMap<>();
        int dayPercent = calculatePercent(todayFocusTimeList, todayFocusTimeMap, userId);

        LocalDate yesterday = today.minusDays(1);
        LocalDate weekFirstDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate monthFirstDate = today.withDayOfMonth(1);

        List<FocusTimeSumResponse> focusTimeSumWeek = oneDaysRepository.findAllUsersFocusTimeSumByDateDataBetween(weekFirstDate, yesterday);
        int weekPercent = calculatePeriodPercent(focusTimeSumWeek, todayFocusTimeMap, userId);

        List<FocusTimeSumResponse> focusTimeSumMonth = oneDaysRepository.findAllUsersFocusTimeSumByDateDataBetween(monthFirstDate, yesterday);
        int monthPercent = calculatePeriodPercent(focusTimeSumMonth, todayFocusTimeMap, userId);

        FocusTimePercentResponse focusTimePercentResponse = new FocusTimePercentResponse(dayPercent, weekPercent, monthPercent);
        return new CommonResponseDto<>(focusTimePercentResponse, "일간, 주간, 월간 백분율 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> findFocusTimeListByEmailAndYearAndMonth(String email, int year, int month){
        Long userId = getUserByEmail(email).getId();

        if(!(1 <= month && month <= 12)){
            throw new CustomException(INVALID_QUERY_STRING);
        }

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        List<OneDays> oneDaysList = oneDaysRepository.findByUserIdAndDateDataBetween(userId, startDate, endDate);
        if (!today.isBefore(startDate) && !today.isAfter(endDate)) {
            int todayTotalFocusTime = findTodayTotalFocusTimeByUserIdAndDateData(userId, today);
            for (int i = 0; i < oneDaysList.size(); i++) {
                if (oneDaysList.get(i).getDateData().equals(today)) {
                    oneDaysList.set(i, OneDays.builder().dateData(today).focusTime(todayTotalFocusTime).build());
                    break;
                }
            }
        }

        FocusTimeListCalendarResponse[] responseData = oneDaysList.stream()
                .map(oneDays -> new FocusTimeListCalendarResponse(
                        oneDays.getDateData().getDayOfMonth(),
                        oneDays.getFocusTime()
                ))
                .toArray(FocusTimeListCalendarResponse[]::new);

        return new CommonResponseDto<>(responseData, "캘린더 데이터 조회에 성공했습니다.", 200);
    }

    private int calculatePercent(List<AppFocusTimeSumDao> focusTimeList, Map<Long, Long> focusTimeMap, Long userId) {
        long before = -1;
        int idx = 0;
        for (int i = 0; i < focusTimeList.size(); i++) {
            AppFocusTimeSumDao focusTime = focusTimeList.get(i);
            focusTimeMap.put(focusTime.getUserId(), focusTime.getFocusTimeSum());
            if (focusTime.getFocusTimeSum() != before) {
                idx = i;
            }
            if (userId.equals(focusTime.getUserId())) {
                return (idx + 1) * 100 / focusTimeList.size();
            }
            before = focusTime.getFocusTimeSum();
        }

        return 100;
    }

    private int calculatePeriodPercent(List<FocusTimeSumResponse> periodFocusTimeList, Map<Long, Long> todayFocusTimeMap, Long userId) {
        boolean[] visited = new boolean[todayFocusTimeMap.size()];
        for (int i = 0; i < periodFocusTimeList.size(); i++) {
            FocusTimeSumResponse userFocusTime = periodFocusTimeList.get(i);
            Long focusTimeSumUserId = userFocusTime.getUserId();

            if (todayFocusTimeMap.containsKey(focusTimeSumUserId)) {
                visited[i] = true;
                userFocusTime.updateFocusTimeSum(userFocusTime.getFocusTimeSum() + todayFocusTimeMap.get(focusTimeSumUserId));
            }
        }
        int idx = 0;
        for (Map.Entry<Long, Long> entry : todayFocusTimeMap.entrySet()) {
            if (!visited[idx]) {
                periodFocusTimeList.add(new FocusTimeSumResponse(entry.getKey(), entry.getValue()));
            }
            idx++;
        }
        Collections.sort(periodFocusTimeList);
        long before = -1;
        for (int i = 0; i < periodFocusTimeList.size(); i++) {
            FocusTimeSumResponse userFocusTime = periodFocusTimeList.get(i);

            if (userFocusTime.getFocusTimeSum() != before) {
                idx = i;
            }

            if (userId.equals(userFocusTime.getUserId())) {
                return (idx + 1) * 100 / periodFocusTimeList.size();
            }
            before = userFocusTime.getFocusTimeSum();
        }
        return 100;
    }

    public int findTodayTotalFocusTimeByUserIdAndDateData(Long userId, LocalDate today){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Long oneDayId = findOneDayByUserAndDateData(user, today).getId();

        return appFocusTimesRepository.findTodayTotalFocusTimeByOneDayId(oneDayId);
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

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public synchronized OneDays findOneDayByUserAndDateData(Users user, LocalDate date) {
        return oneDaysRepository.findByUserIdAndDateData(user.getId(), date)
                .orElseGet(() -> {
                    if (oneDaysRepository.findByUserIdAndDateData(user.getId(), date).isPresent()) {
                        return oneDaysRepository.findByUserIdAndDateData(user.getId(), date).get();
                    }
                    OneDays newOneDay = OneDays.builder()
                            .dateData(date)
                            .focusTime(0)
                            .targetTime(0)
                            .user(user)
                            .build();
                    oneDaysRepository.save(newOneDay);
                    return newOneDay;
                });
    }


}
