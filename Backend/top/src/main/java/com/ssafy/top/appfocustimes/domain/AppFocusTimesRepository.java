package com.ssafy.top.appfocustimes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppFocusTimesRepository extends JpaRepository<AppFocusTimes, Long> {

    List<AppFocusTimes> findByOneDaysId(Long oneDayId);

    Optional<AppFocusTimes> findByOneDaysIdAndApp(Long oneDayId, String app);

    @Query("SELECT MAX(a.startTime) FROM AppFocusTimes a WHERE a.oneDays.id = :oneDaysId")
    Optional<Integer> findLatestStartTimeByOneDaysId(@Param("oneDaysId") Long oneDaysId);


}
