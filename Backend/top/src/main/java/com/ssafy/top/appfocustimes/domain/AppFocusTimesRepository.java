package com.ssafy.top.appfocustimes.domain;

import com.ssafy.top.appfocustimes.dao.AppAndTimeDao;
import com.ssafy.top.appfocustimes.dao.AppFocusTimeSumDao;
import com.ssafy.top.onedays.domain.OneDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppFocusTimesRepository extends JpaRepository<AppFocusTimes, Long> {

    List<AppFocusTimes> findByOneDaysIdAndApp(Long oneDayId, String app);

    Optional<AppFocusTimes> findByOneDaysIdAndTimeUnitAndApp(Long id, int timeUnit, String app);

    Optional<AppFocusTimes> findByOneDaysAndTimeUnit(OneDays oneDays, int timeUnit);

    @Query("SELECT a.timeUnit, COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "WHERE a.oneDays.id = :oneDayId " +
            "GROUP BY a.timeUnit")
    List<Object[]> findUnitFocusTimeByOneDayId(@Param("oneDayId") Long oneDayId);

    @Query("SELECT COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "WHERE a.oneDays.id = :oneDayId")
    int findTodayTotalFocusTimeByOneDayId(@Param("oneDayId") Long oneDayId);

    // 일일 앱 시간 합
    @Query("SELECT new com.ssafy.top.appfocustimes.dao.AppAndTimeDao(a.app, SUM(a.focusTime)) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app " +
            "WHERE a.oneDays.id = :oneDayId AND (b.isBan = false OR b.name IS NULL) " +
            "GROUP BY a.app")
    List<AppAndTimeDao> findAppTimeByOneDaysId(@Param("oneDayId") Long oneDayId);

    @Query("SELECT MAX(a.startTime) FROM AppFocusTimes a WHERE a.oneDays.id = :oneDaysId AND a.app = :app")
    Optional<Integer> findLatestStartTimeByOneDaysIdAndApp(@Param("oneDaysId") Long oneDaysId, @Param("app") String app);

    @Query("SELECT MAX(a.startTime) FROM AppFocusTimes a WHERE a.oneDays.id = :oneDaysId")
    Optional<Integer> findLatestStartTimeByOneDaysId(@Param("oneDaysId") Long oneDaysId);

    @Query("SELECT new com.ssafy.top.appfocustimes.dao.AppFocusTimeSumDao(a.oneDays.user.id, SUM(a.focusTime)) " +
            "FROM AppFocusTimes a " +
            "WHERE a.oneDays.dateData = :today " +
            "GROUP BY a.oneDays.user.id " +
            "ORDER BY SUM(a.focusTime) DESC")
    List<AppFocusTimeSumDao> findAllUsersFocusTimeSum(@Param("today") LocalDate today);


}
