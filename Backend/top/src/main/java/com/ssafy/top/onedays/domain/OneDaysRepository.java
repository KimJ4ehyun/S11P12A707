package com.ssafy.top.onedays.domain;

import com.ssafy.top.onedays.dto.response.FocusTimeSumResponse;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OneDaysRepository extends JpaRepository<OneDays, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<OneDays> findByUserIdAndDateData(Long userId, LocalDate today);

    List<OneDays> findByUserIdAndDateDataBetween(Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app " +
            "WHERE a.oneDays.user.id = :userId AND b.name IS NULL AND a.oneDays.dateData BETWEEN :startDate AND :endDate ")
    int findTotalFocusTimeByUserIdAndDateDataBetween(@Param("userId") Long userId,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(a.focusTime), 0) " +
            "FROM AppFocusTimes a " +
            "LEFT JOIN Bans b ON b.name = a.app " +
            "WHERE a.oneDays.user.id = :userId AND b.name IS NULL")
    int findWholeTotalFocusTimeByUserIdExcludingToday(@Param("userId") Long userId);

    @Query("SELECT new com.ssafy.top.onedays.dto.response.FocusTimeSumResponse(o.user.id, SUM(o.focusTime)) " +
            "FROM OneDays o " +
            "WHERE o.dateData BETWEEN :startDate AND :endDate " +
            "GROUP BY o.user.id")
    List<FocusTimeSumResponse> findAllUsersFocusTimeSumByDateDataBetween(@Param("startDate") LocalDate startDate,
                                                                         @Param("endDate") LocalDate endDate);
}
