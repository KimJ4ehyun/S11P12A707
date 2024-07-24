package com.ssafy.top.appfocustimes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppFocusTimesRepository extends JpaRepository<AppFocusTimes, Long> {

    List<AppFocusTimes> findByOneDaysId(Long oneDayId);
}
