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

    List<OneDays> findByUserId(Long userId);

    @Query("SELECT new com.ssafy.top.onedays.dto.response.FocusTimeSumResponse(o.user.id, SUM(o.focusTime)) " +
            "FROM OneDays o " +
            "WHERE o.dateData BETWEEN :startDate AND :endDate " +
            "GROUP BY o.user.id")
    List<FocusTimeSumResponse> findAllUsersFocusTimeSumByDateDataBetween(@Param("startDate") LocalDate startDate,
                                                                         @Param("endDate") LocalDate endDate);
}
