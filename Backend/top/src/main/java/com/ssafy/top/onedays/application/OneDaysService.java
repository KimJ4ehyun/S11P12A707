package com.ssafy.top.onedays.application;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.global.exception.CustomException;
import com.ssafy.top.hourfocustimes.domain.HourFocusTimes;
import com.ssafy.top.hourfocustimes.domain.HourFocusTimesRepository;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.onedays.dto.request.TimeGoalRequest;
import com.ssafy.top.onedays.dto.response.*;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.undo.CannotUndoException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import static com.ssafy.top.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OneDaysService {

    private final OneDaysRepository oneDaysRepository;

    private final HourFocusTimesRepository hourFocusTimesRepository;

    private final UsersRepository usersRepository;

    public CommonResponseDto<?> findTotalFocusTimeByLoginIdAndPeriod(String loginId, String period) {
        Long userId = getUserByLoginId(loginId).getId();
        if (!(period.equals("day") || period.equals("week") || period.equals("month"))) {
            throw new CustomException(INVALID_QUERY_STRING);
        }
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        int totalFocusTime = findTodayTotalFocusTimeByUserIdAndDateData(userId, today);
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

        TotalFocusTimeResponse totalFocusTimeResponse = new TotalFocusTimeResponse(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        return new CommonResponseDto<>(totalFocusTimeResponse, "집중시간 통계 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> findFocusTimeListByLoginIdAndPeriod(String loginId, String period) {
        if (!(period.equals("day") || period.equals("week") || period.equals("month"))) {
            throw new CustomException(INVALID_QUERY_STRING);
        }
        return findFocusTimeList(loginId, period, null);
    }

    public CommonResponseDto<?> findFocusTimeListByLoginIdAndMonth(String loginId, int month) {
        if(!(month == 1 || month == 6)){
            throw new CustomException(INVALID_QUERY_STRING);
        }
        return findFocusTimeList(loginId, null, month);
    }

    public CommonResponseDto<?> findFocusTimeList(String loginId, String period, Integer month) {
        Long userId = getUserByLoginId(loginId).getId();
        LocalDate currentDay = LocalDate.now();
        LocalDate today = currentDay;

        if(period != null){
            if (period.equals("day")) {
                Long oneDayId = oneDaysRepository.findByUserIdAndDateData(userId, today)
                        .map(OneDays::getId)
                        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
                List<HourFocusTimes> hourFocusTimesList = hourFocusTimesRepository.findByOneDaysId(oneDayId);
                FocusTimeListDayResponse[] focusTimeListDayResponses = hourFocusTimesList.stream()
                        .map(hourFocusTime -> new FocusTimeListDayResponse(hourFocusTime.getTimeUnit(), hourFocusTime.getFocusTime()))
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

    public int findTodayTotalFocusTimeByUserIdAndDateData(Long userId, LocalDate today){
        Long oneDayId = oneDaysRepository.findByUserIdAndDateData(userId, today)
                .map(OneDays::getId)
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        List<HourFocusTimes> hourFocusTimesList = hourFocusTimesRepository.findByOneDaysId(oneDayId);

        return hourFocusTimesList.stream()
                .mapToInt(HourFocusTimes::getFocusTime)
                .sum();
    }

    public CommonResponseDto<?> findFocusTimeListByLoginIdAndYearAndMonth(String loginId, int year, int month){
        Long userId = getUserByLoginId(loginId).getId();
        if(!(1 <= month && month <= 12)){
            throw new CustomException(INVALID_QUERY_STRING);
        }

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

        FocusTimeListCalendarResponse[] responseData = oneDaysList.stream()
                .map(oneDays -> new FocusTimeListCalendarResponse(
                        oneDays.getDateData().getDayOfMonth(),
                        oneDays.getFocusTime()
                ))
                .toArray(FocusTimeListCalendarResponse[]::new);

        return new CommonResponseDto<>(responseData, "캘린더 데이터 조회에 성공했습니다.", 200);
    }

    public CommonResponseDto<?> findByLoginId(String loginId){
        Long userId = getUserByLoginId(loginId).getId();
        int targetTime = oneDaysRepository.findByUserIdAndDateData(userId, LocalDate.now())
                .map(OneDays::getTargetTime)
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        TimeGoalResponse timeGoalResponse = new TimeGoalResponse(targetTime);
        return new CommonResponseDto<>(timeGoalResponse, "목표 시간을 조회했습니다.",200);
    }

    public CommonResponseDto<?> saveTimeGoal(String loginId, TimeGoalRequest timeGoal){
        Users user = getUserByLoginId(loginId);
        validateTimeGoal(timeGoal.getTimeGoal());

        Optional<OneDays> oneDays = oneDaysRepository.findByUserIdAndDateData(user.getId(), LocalDate.now());
        if(oneDays.isPresent()){
            throw new CustomException(ALREADY_EXISTS);
        }

        OneDays oneDay = OneDays.builder()
                .user(user)
                .dateData(LocalDate.now())
                .focusTime(0)
                .targetTime(timeGoal.getTimeGoal()*60)
                .build();
        TimeGoalResponse timeGoalResponse = new TimeGoalResponse(oneDaysRepository.save(oneDay).getTargetTime() / 60);
        return new CommonResponseDto<>(timeGoalResponse, "정상적으로 목표가 설정되었습니다.", 201);
    }

    public CommonResponseDto<?> updateTimeGoal(String loginId, TimeGoalRequest timeGoal){
        Users user = getUserByLoginId(loginId);
        validateTimeGoal(timeGoal.getTimeGoal());

        OneDays oneDays = oneDaysRepository.findByUserIdAndDateData(user.getId(), LocalDate.now())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        OneDays oneDay = OneDays.builder()
                .id(oneDays.getId())
                .user(oneDays.getUser())
                .dateData(oneDays.getDateData())
                .focusTime(oneDays.getFocusTime())
                .targetTime(timeGoal.getTimeGoal()*60)
                .build();
        TimeGoalResponse timeGoalResponse = new TimeGoalResponse(oneDaysRepository.save(oneDay).getTargetTime() / 60);
        return new CommonResponseDto<>(timeGoalResponse, "정상적으로 목표 시간이 수정되었습니다.", 200);
    }

    private void validateTimeGoal(int timeGoalData) {
        if (!(0 <= timeGoalData && timeGoalData <= 1440)) {
            throw new CustomException(INVALID_TIME_GOAL);
        }
    }

    private Users getUserByLoginId(String loginId) {
        return usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

}

