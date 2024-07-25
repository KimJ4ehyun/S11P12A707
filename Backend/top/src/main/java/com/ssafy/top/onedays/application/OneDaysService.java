package com.ssafy.top.onedays.application;

import com.ssafy.top.appfocustimes.domain.AppFocusTimes;
import com.ssafy.top.appfocustimes.dto.response.AppListResponse;
import com.ssafy.top.hourfocustimes.domain.HourFocusTimes;
import com.ssafy.top.hourfocustimes.domain.HourFocusTimesRepository;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.onedays.dto.response.FocusTimeListCalendarResponse;
import com.ssafy.top.onedays.dto.response.FocusTimeListDayResponse;
import com.ssafy.top.onedays.dto.response.FocusTimeListResponse;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OneDaysService {

    private final OneDaysRepository oneDaysRepository;

    private final HourFocusTimesRepository hourFocusTimesRepository;

    private final UsersRepository usersRepository;

    public String findTotalFocusTimeByLoginIdAndPeriod(String loginId, String period) {
        int totalFocusTime = 0;
        Long userId = usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NoSuchElementException("해당 로그인 아이디가 존재하지 않습니다: " + loginId))
                .getId();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        totalFocusTime = findTodayTotalFocusTimeByUserIdAndDateData(userId, today);

        if (period.equals("week")) {
            today = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        } else if (period.equals("month")) {
            today = today.withDayOfMonth(1);
        }

        List<OneDays> oneDaysList = oneDaysRepository.findByUserIdAndDateDataBetween(userId, today, yesterday);
        for(OneDays oneDays : oneDaysList){
            totalFocusTime += oneDays.getFocusTime();
        }

        int hours = totalFocusTime / 3600;
        int minutes = (totalFocusTime % 3600) / 60;
        int seconds = totalFocusTime % 60;

        String formattedHours;
        if (hours < 10) {
            formattedHours = String.format("0%d", hours);
        } else {
            formattedHours = String.valueOf(hours);
        }

        return String.format("%s:%02d:%02d", formattedHours, minutes, seconds);
    }
    public Object findFocusTimeListByLoginIdAndPeriod(String loginId, String period) {
        return findFocusTimeList(loginId, period, null);
    }

    public Object findFocusTimeListByLoginIdAndMonth(String loginId, int month) {
        return findFocusTimeList(loginId, null, month);
    }

    public Object findFocusTimeList(String loginId, String period, Integer month) {
        Long userId = usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NoSuchElementException("해당 로그인 아이디가 존재하지 않습니다: " + loginId))
                .getId();
        LocalDate currentDay = LocalDate.now();
        LocalDate today = currentDay;

        if(period != null){
            if (period.equals("day")) {
                Long oneDayId = oneDaysRepository.findByUserIdAndDateData(userId, today)
                        .map(OneDays::getId)
                        .orElseThrow(() -> new NoSuchElementException("해당 날짜에 해당하는 데이터가 없습니다: " + today));
                List<HourFocusTimes> hourFocusTimesList = hourFocusTimesRepository.findByOneDaysId(oneDayId);
                FocusTimeListDayResponse[] focusTimeListDayResponses = new FocusTimeListDayResponse[hourFocusTimesList.size()];
                for (int i = 0; i < focusTimeListDayResponses.length; i++) {
                    focusTimeListDayResponses[i] = new FocusTimeListDayResponse(hourFocusTimesList.get(i).getTimeUnit(), hourFocusTimesList.get(i).getFocusTime());
                }
                return focusTimeListDayResponses;
            } else if (period.equals("week")) {
                currentDay = currentDay.minusWeeks(1).plusDays(1);
            } else if (period.equals("month")) {
                currentDay = currentDay.minusMonths(1).plusDays(1);
            }
        } else if (month != null) {
            currentDay = currentDay.minusMonths(month).plusDays(1);
        }

        LocalDate yesterday = today.minusDays(1);
        List<OneDays> oneDaysList = oneDaysRepository.findByUserIdAndDateDataBetween(userId, currentDay, yesterday);
        FocusTimeListResponse[] focusTimeListResponses = new FocusTimeListResponse[oneDaysList.size()+1];
        for(int i = 0; i < focusTimeListResponses.length-1; i++){
            String formattedDate = oneDaysList.get(i).getDateData().format(DateTimeFormatter.ofPattern("MM-dd"));
            focusTimeListResponses[i] = new FocusTimeListResponse(formattedDate, oneDaysList.get(i).getFocusTime());
        }
        String formattedDateToday = today.format(DateTimeFormatter.ofPattern("MM-dd"));
        focusTimeListResponses[focusTimeListResponses.length-1] = new FocusTimeListResponse(formattedDateToday, findTodayTotalFocusTimeByUserIdAndDateData(userId, today));
        return focusTimeListResponses;
    }

    public int findTodayTotalFocusTimeByUserIdAndDateData(Long userId, LocalDate today){
        int totalFocusTime = 0;
        Long oneDayId = oneDaysRepository.findByUserIdAndDateData(userId, today)
                .map(OneDays::getId)
                .orElseThrow(() -> new NoSuchElementException("해당 날짜에 해당하는 데이터가 없습니다: " + today));

        List<HourFocusTimes> hourFocusTimesList = hourFocusTimesRepository.findByOneDaysId(oneDayId);

        for (HourFocusTimes hourFocusTime : hourFocusTimesList) {
            totalFocusTime += hourFocusTime.getFocusTime();
        }
        return totalFocusTime;
    }

    public FocusTimeListCalendarResponse[] findFocusTimeListByLoginIdAndYearAndMonth(String loginId, int year, int month){
        Long userId = usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NoSuchElementException("해당 로그인 아이디가 존재하지 않습니다: " + loginId))
                .getId();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate today = LocalDate.now();

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

        return oneDaysList.stream()
                .map(oneDays -> new FocusTimeListCalendarResponse(
                        oneDays.getDateData().getDayOfMonth(),
                        oneDays.getFocusTime()
                ))
                .toArray(FocusTimeListCalendarResponse[]::new);
    }
}
