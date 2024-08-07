package com.ssafy.top.hourfocustimes.domain;

import com.ssafy.top.hourfocustimes.dao.HourFocusTimeSumDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface HourFocusTimesRepository extends JpaRepository<HourFocusTimes, Long> {

    List<HourFocusTimes> findByOneDaysId(Long id);

    Optional<HourFocusTimes> findByOneDaysIdAndTimeUnit(Long id, int timeUnit);

    @Query("SELECT new com.ssafy.top.hourfocustimes.dao.HourFocusTimeSumDao(h.oneDays.user.id, SUM(h.focusTime)) " +
            "FROM HourFocusTimes h " +
            "WHERE h.oneDays.dateData = CURRENT_DATE " +
            "GROUP BY h.oneDays.user.id " +
            "ORDER BY SUM(h.focusTime) DESC")
    List<HourFocusTimeSumDao> findAllUsersFocusTimeSum();
}
