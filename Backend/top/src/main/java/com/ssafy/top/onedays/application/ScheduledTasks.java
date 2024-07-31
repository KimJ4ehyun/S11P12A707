package com.ssafy.top.onedays.application;

import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final OneDaysService oneDaysService;

    private final OneDaysRepository oneDaysRepository;

    private final UsersRepository usersRepository;

    @Scheduled(cron = "0 5 0 * * ?")
    public void updateAllUsersOneDayFocusTime() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        usersRepository.findAll().forEach(user -> {
            int totalFocusTime = oneDaysService.findTodayTotalFocusTimeByUserIdAndDateData(user.getId(), yesterday);
            if (totalFocusTime != 0) {
                oneDaysRepository.findByUserIdAndDateData(user.getId(), yesterday).ifPresent(oneDays -> {
                    oneDays.updateFocusTime(totalFocusTime);
                    oneDaysRepository.save(oneDays);
                });
            }
        });
    }
}
