package com.ssafy.top.onedays.domain;

import com.ssafy.top.onedays.dto.response.FocusTimeSumResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OneDaysRepository extends JpaRepository<OneDays, Long> {

    Optional<OneDays> findByUserIdAndDateData(Long userId, LocalDate today);

    List<OneDays> findByUserIdAndDateDataBetween(Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(o.focusTime), 0) " +
            "FROM OneDays o " +
            "WHERE o.user.id = :userId " +
            "AND o.dateData <> :today")
    int findWholeTotalFocusTimeByUserIdExcludingToday(@Param("userId") Long userId, @Param("today") LocalDate today);

    @Query("SELECT new com.ssafy.top.onedays.dto.response.FocusTimeSumResponse(o.user.id, SUM(o.focusTime)) " +
            "FROM OneDays o " +
            "WHERE o.dateData BETWEEN :startDate AND :endDate " +
            "GROUP BY o.user.id")
    List<FocusTimeSumResponse> findAllUsersFocusTimeSumByDateDataBetween(@Param("startDate") LocalDate startDate,
                                                                         @Param("endDate") LocalDate endDate);
}
