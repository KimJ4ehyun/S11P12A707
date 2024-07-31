package com.ssafy.top.hourfocustimes.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HourFocusTimesRepository extends JpaRepository<HourFocusTimes, Long> {

    List<HourFocusTimes> findByOneDaysId(Long id);

    Optional<HourFocusTimes> findByOneDaysIdAndTimeUnit(Long id, int timeUnit);
}
