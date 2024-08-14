package com.ssafy.top.appfocustimes.domain;

import com.ssafy.top.appfocustimes.dao.AppAndTimeDao;
import com.ssafy.top.appfocustimes.dao.AppFocusTimeSumDao;
import com.ssafy.top.onedays.domain.OneDays;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppFocusTimesRepository extends JpaRepository<AppFocusTimes, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AppFocusTimes> findByOneDaysIdAndTimeUnitAndApp(Long id, int timeUnit, String app);

    // 해당 기간의 집중 시간 합 구하기
    @Query("SELECT COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app AND a.oneDays.user.id =  b.user.id " +
            "WHERE a.oneDays.user.id = :userId AND b.name IS NULL AND a.oneDays.dateData BETWEEN :startDate AND :endDate ")
    int findTotalFocusTimeByUserIdAndDateDataBetween(@Param("userId") Long userId,
                                                     @Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);

    // 각 단위 시간 집중 시간 구하기
    @Query("SELECT a.timeUnit, COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app AND a.oneDays.user.id =  b.user.id " +
            "WHERE a.oneDays.id = :oneDayId AND b.name IS NULL " +
            "GROUP BY a.timeUnit")
    List<Object[]> findUnitFocusTimeByOneDayId(@Param("oneDayId") Long oneDayId);

    // 해당 기간의 집중 시간 합 구하기
    @Query("SELECT a.oneDays.dateData, COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app AND a.oneDays.user.id = b.user.id " +
            "WHERE a.oneDays.user.id = :userId " +
            "AND b.name IS NULL " +
            "AND a.oneDays.dateData BETWEEN :startDate AND :endDate " +
            "GROUP BY a.oneDays.dateData " +
            "ORDER BY a.oneDays.dateData ASC")
    List<Object[]> findFocusTimeListByUserIdAndDateDataBetween(@Param("userId") Long userId,
                                                               @Param("startDate") LocalDate startDate,
                                                               @Param("endDate") LocalDate endDate);


    // 하루 전체 집중 시간 구하기
    @Query("SELECT COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app AND a.oneDays.user.id =  b.user.id " +
            "WHERE a.oneDays.id = :oneDayId AND b.name IS NULL")
    int findTodayTotalFocusTimeByOneDayId(@Param("oneDayId") Long oneDayId);

    @Query("SELECT COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app AND a.oneDays.user.id =  b.user.id " +
            "WHERE a.oneDays.user.id = :userId AND b.name IS NULL")
    int findWholeTotalFocusTimeByUserIdExcludingToday(@Param("userId") Long userId);

    // 백분율 구할 때 사용할 순위 구하기
    @Query(value = "SELECT r.ranking " +
            "FROM ( " +
            "    SELECT o.user_id AS userId, " +
            "           RANK() OVER (ORDER BY SUM(COALESCE(a.focus_time, 0)) DESC) AS ranking " +
            "    FROM one_days o " +
            "    LEFT JOIN app_focus_times a ON a.one_day_id = o.one_day_id " +
            "    LEFT JOIN bans b ON b.name = a.app AND o.user_id = b.user_id " +
            "    WHERE o.date_data BETWEEN :startDate AND :endDate " +
            "      AND b.name IS NULL " +
            "    GROUP BY o.user_id " +
            ") r " +
            "WHERE r.userId = :userId", nativeQuery = true)
    int findRankByDateDataBetween(@Param("userId") Long userId,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app AND a.oneDays.user.id =  b.user.id " +
            "WHERE a.oneDays.user.id = :userId AND b.name IS NULL " +
            "AND a.oneDays.dateData = :date")
    int findTotalFocusTimeByUserIdAndDateData(@Param("userId") Long userId, @Param("date") LocalDate date);

    // 일일 앱 사용량 구하기
    @Query("SELECT new com.ssafy.top.appfocustimes.dao.AppAndTimeDao(a.app, SUM(a.focusTime)) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app AND a.oneDays.user.id =  b.user.id " +
            "WHERE a.oneDays.id = :oneDayId AND b.name IS NULL " +
            "GROUP BY a.app")
    List<AppAndTimeDao>  findAppTimeByOneDaysId(@Param("oneDayId") Long oneDayId);

}
