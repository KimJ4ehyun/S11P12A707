package com.ssafy.top.onedays.application;

import com.ssafy.top.appfocustimes.application.AppFocusTimesService;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final AppFocusTimesService appFocusTimesService;

    private final OneDaysRepository oneDaysRepository;

    private final UsersRepository usersRepository;

    @Scheduled(cron = "0 8 19 * * ?", zone = "Asia/Seoul")
    public void updateAllUsersOneDayFocusTime() {
        logger.info("Scheduled task started for updating user focus time.");
//        LocalDate yesterday = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(1);
        LocalDate yesterday = LocalDate.of(2024, 8, 13);
        usersRepository.findAll().forEach(user -> {
            int totalFocusTime = appFocusTimesService.findTodayTotalFocusTimeForScheduleByUserIdAndDateData(user.getId(), yesterday);
            if (totalFocusTime != 0) {
                oneDaysRepository.findByUserIdAndDateData(user.getId(), yesterday).ifPresent(oneDays -> {
                    oneDays.updateFocusTime(totalFocusTime);
                    logger.info("Updated focus time for user: {}", user.getId());
                    logger.info("{}", totalFocusTime);
                });
            }
        });
    }
}