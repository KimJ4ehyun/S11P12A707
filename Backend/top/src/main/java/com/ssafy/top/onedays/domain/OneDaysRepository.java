package com.ssafy.top.onedays.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;

public interface OneDaysRepository extends JpaRepository<OneDays, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<OneDays> findByUserIdAndDateData(Long userId, LocalDate today);

    @Query("SELECT COALESCE(SUM(o.targetTime), 0) " +
           "FROM OneDays o " +
           "WHERE o.user.id = :userId AND o.dateData BETWEEN :startDate AND :endDate ")
    int findTotalTimeGoalByUserIdAndDateDataBetween(@Param("userId") Long userId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}
