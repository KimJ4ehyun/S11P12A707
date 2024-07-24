package com.ssafy.top.appfocustimes.application;

import com.ssafy.top.appfocustimes.domain.AppFocusTimes;
import com.ssafy.top.appfocustimes.domain.AppFocusTimesRepository;
import com.ssafy.top.appfocustimes.dto.response.AppListResponse;
import com.ssafy.top.onedays.domain.OneDays;
import com.ssafy.top.onedays.domain.OneDaysRepository;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AppFocusTimesService {

    private final AppFocusTimesRepository appFocusTimesRepository;

    private final OneDaysRepository oneDaysRepository;

    private final UsersRepository usersRepository;

    public List<AppFocusTimes> findAppFocusTimesByLoginId(String loginId) {
        Long userId = usersRepository.findByLoginId(loginId)
                .map(Users::getId)
                .orElseThrow(() -> new NoSuchElementException("해당 로그인 아이디가 존재하지 않습니다: " + loginId));

        LocalDate today = LocalDate.now();
        Long oneDayId = oneDaysRepository.findByUserIdAndDateData(userId, today)
                .map(OneDays::getId)
                .orElseThrow(() -> new NoSuchElementException("해당 날짜에 해당하는 데이터가 없습니다: " + today));

        return appFocusTimesRepository.findByOneDaysId(oneDayId);
    }

    public AppListResponse[] findTopThreeByAppFocusTimeList(List<AppFocusTimes> appFocusTimesList) {
        int totalFocusTime = appFocusTimesList.stream()
                .mapToInt(AppFocusTimes::getFocusTime)
                .sum();

        return appFocusTimesList.stream()
                .sorted(Comparator.comparingInt(AppFocusTimes::getFocusTime).reversed())
                .limit(3)
                .map(appFocusTimes -> new AppListResponse(
                        appFocusTimes.getApp(),
                        appFocusTimes.getFocusTime(),
                        appFocusTimes.getFocusTime() * 100 / totalFocusTime
                ))
                .toArray(AppListResponse[]::new);
    }

}
