package com.ssafy.top.onedays.application;

import com.ssafy.top.appfocustimes.application.AppFocusTimesService;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final AppFocusTimesService appFocusTimesService;

    private final OneDaysRepository oneDaysRepository;

    private final UsersRepository usersRepository;

    @Scheduled(cron = "0 9 20 * * *", zone = "Asia/Seoul")
    public void updateAllUsersOneDayFocusTime() {
//        LocalDate yesterday = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(1);
        LocalDate yesterday = LocalDate.of(2024, 8, 13);
        usersRepository.findAll().forEach(user -> {
            int totalFocusTime = appFocusTimesService.findTodayTotalFocusTimeForScheduleByUserIdAndDateData(user.getId(), yesterday);
            if (totalFocusTime != 0) {
                oneDaysRepository.findByUserIdAndDateData(user.getId(), yesterday).ifPresent(oneDays -> oneDays.updateFocusTime(totalFocusTime));
            }
        });
    }
}