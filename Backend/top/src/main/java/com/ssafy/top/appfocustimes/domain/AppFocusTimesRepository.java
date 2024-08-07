package com.ssafy.top.appfocustimes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppFocusTimesRepository extends JpaRepository<AppFocusTimes, Long> {

    List<AppFocusTimes> findByOneDaysId(Long oneDayId);

    Optional<AppFocusTimes> findByOneDaysIdAndApp(Long oneDayId, String app);

    @Query(value = "SELECT * FROM app_focus_times WHERE one_day_id = :oneDaysId ORDER BY start_time DESC LIMIT 1", nativeQuery = true)
    Optional<AppFocusTimes> findLatestStartTimeByOneDaysIdOrderByFocusTime(@Param("oneDaysId") Long oneDaysId);

}
