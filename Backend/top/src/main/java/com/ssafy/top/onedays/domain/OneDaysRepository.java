package com.ssafy.top.onedays.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface OneDaysRepository extends JpaRepository<OneDays, Long> {

    OneDays findByUserIdAndDateData(Long userId, LocalDate today);

    List<OneDays> findByUserIdAndDateDataBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
