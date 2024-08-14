package com.ssafy.top.bans.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BansRepository extends JpaRepository<Bans, Long> {
    Optional<Bans> findByUserIdAndName(Long userId, String name);

    @Query("select a.name " +
            "from Bans a " +
            "where a.user.id = :userId " +
            "order by a.name ")
    List<String> findByUser(@Param("userId") Long userId);

    @Query("SELECT b.name, COALESCE(SUM(a.focusTime), 0) AS totalFocusTime " +
            "FROM Bans b " +
            "LEFT JOIN AppFocusTimes a ON a.oneDays.user.id = b.user.id AND b.name = a.app AND a.oneDays.dateData = :dateData " +
            "WHERE b.user.id = :userId " +
            "GROUP BY b.name " +
            "ORDER BY totalFocusTime DESC ")
    List<Object[]> findBanListByUserIdAndDateData(@Param("userId") Long userId, @Param("dateData") LocalDate dateData);
}
