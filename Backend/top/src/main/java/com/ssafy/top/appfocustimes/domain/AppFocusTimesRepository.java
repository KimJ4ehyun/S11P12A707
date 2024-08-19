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

    // 각 단위 시간 집중 시간 구하기
    @Query("SELECT a.timeUnit, COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app AND a.oneDays.user.id =  b.user.id " +
            "WHERE a.oneDays.id = :oneDayId AND b.name IS NULL " +
            "GROUP BY a.timeUnit")
    List<Object[]> findUnitFocusTimeByOneDayId(@Param("oneDayId") Long oneDayId);

    // 하루 전체 집중 시간 구하기
    @Query("SELECT COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "JOIN a.oneDays o " +
            "LEFT JOIN Bans b ON b.name = a.app AND o.user.id = b.user.id " +
            "WHERE o.id = :oneDayId AND b.name IS NULL")
    int findTodayTotalFocusTimeByOneDayId(@Param("oneDayId") Long oneDayId);

    // 백분율 구할 때 사용할 순위 구하기
    @Query(value = "SELECT r.ranking " +
            "FROM ( " +
            "    SELECT " +
            "        o.user_id AS userId, " +
            "        RANK() OVER (ORDER BY SUM(COALESCE(a.today_focus_time, 0) + COALESCE(o.past_focus_time, 0)) DESC) AS ranking " +
            "    FROM ( " +
            "        SELECT " +
            "            o.user_id, " +
            "            SUM(CASE WHEN o.date_data = :endDate THEN 0 ELSE o.focus_time END) AS past_focus_time " +
            "        FROM one_days o " +
            "        WHERE o.date_data BETWEEN :startDate AND :endDate " +  // :endDate 포함
            "        GROUP BY o.user_id " +
            "    ) o " +
            "    LEFT JOIN ( " +
            "        SELECT " +
            "            o.user_id, " +
            "            SUM(COALESCE(a.focus_time, 0)) AS today_focus_time " +
            "        FROM app_focus_times a " +
            "        JOIN one_days o ON a.one_day_id = o.one_day_id " +
            "        LEFT JOIN bans b ON b.name = a.app AND o.user_id = b.user_id " +
            "        WHERE o.date_data = :endDate " +
            "        AND b.name IS NULL " +
            "        GROUP BY o.user_id " +
            "    ) a ON a.user_id = o.user_id " +
            "    GROUP BY o.user_id " +
            ") r " +
            "WHERE r.userId = :userId",
            nativeQuery = true)
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
