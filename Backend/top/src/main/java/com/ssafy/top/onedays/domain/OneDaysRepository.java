package com.ssafy.top.onedays.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OneDaysRepository extends JpaRepository<OneDays, Long> {

    Optional<OneDays> findByUserIdAndDateData(Long userId, LocalDate today);

    List<OneDays> findByUserIdAndDateDataBetween(Long userId, LocalDate startDate, LocalDate endDate);

    List<OneDays> findByUserId(Long userId);
}
