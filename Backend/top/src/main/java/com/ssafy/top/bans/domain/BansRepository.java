package com.ssafy.top.bans.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BansRepository extends JpaRepository<Bans, Long> {
    Optional<Bans> findByUserIdAndName(Long userId, String name);

    @Query("select a.name " +
            "from Bans a " +
            "where a.user.id = :userId " +
            "order by a.name ")
    List<String> findByUser(@Param("userId") Long userId);
}
