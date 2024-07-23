package com.ssafy.top.onedays.application;

import com.ssafy.top.hourfocustimes.domain.HourFocusTimes;
import com.ssafy.top.hourfocustimes.domain.HourFocusTimesRepository;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
        List<HourFocusTimes> hourFocusTimesList =
                hourFocusTimesRepository.findByOneDaysId(oneDaysRepository.findByUserIdAndDateData(userId, today).getId());

        for (HourFocusTimes hourFocusTime : hourFocusTimesList) {
            totalFocusTime += hourFocusTime.getFocusTime();
        }

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

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
