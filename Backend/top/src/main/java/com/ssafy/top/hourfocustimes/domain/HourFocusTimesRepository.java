package com.ssafy.top.hourfocustimes.domain;

import com.ssafy.top.onedays.domain.OneDays;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HourFocusTimesRepository extends JpaRepository<HourFocusTimes, Long> {

    List<HourFocusTimes> findByOneDaysId(Long id);
}
