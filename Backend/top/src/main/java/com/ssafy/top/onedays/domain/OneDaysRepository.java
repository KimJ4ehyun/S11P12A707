package com.ssafy.top.onedays.domain;

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

    Optional<OneDays> findOneDaysByUserIdAndDateData(Long userId, LocalDate today);

    @Query("SELECT COALESCE(SUM(o.targetTime), 0) " +
           "FROM OneDays o " +
           "WHERE o.user.id = :userId AND o.dateData BETWEEN :startDate AND :endDate ")
    int findTotalTimeGoalByUserIdAndDateDataBetween(@Param("userId") Long userId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);


    // 해당 기간의 집중 시간 합 구하기
    @Query("SELECT COALESCE(SUM(o.focusTime), 0) " +
            "FROM OneDays o " +
            "WHERE o.user.id = :userId AND o.dateData BETWEEN :startDate AND :endDate ")
    int findTotalFocusTimeByUserIdAndDateDataBetween(@Param("userId") Long userId,
                                                     @Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);

    // 해당 기간의 집중 시간 합 구하기
    @Query("SELECT o.dateData, COALESCE(SUM(o.focusTime), 0) " +
            "FROM OneDays o " +
            "WHERE o.user.id = :userId " +
            "AND o.dateData BETWEEN :startDate AND :endDate " +
            "GROUP BY o.dateData " +
            "ORDER BY o.dateData ASC")
    List<Object[]> findFocusTimeListByUserIdAndDateDataBetween(@Param("userId") Long userId,
                                                               @Param("startDate") LocalDate startDate,
                                                               @Param("endDate") LocalDate endDate);

    // 전체 oneday에 있는 focustime 합 조회
    @Query("SELECT COALESCE(SUM(o.focusTime), 0) " +
            "FROM OneDays o " +
            "WHERE o.user.id = :userId")
    int findWholeTotalFocusTimeByUserId(@Param("userId") Long userId);

    // 해당 날짜의 focustime 조회
    @Query("SELECT COALESCE(o.focusTime, 0) " +
            "FROM OneDays o " +
            "WHERE o.user.id = :userId " +
            "AND o.dateData = :date")
    int findTotalFocusTimeByUserIdAndDateData(@Param("userId") Long userId, @Param("date") LocalDate date);
}
