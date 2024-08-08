package com.ssafy.top.widgets.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WidgetsRepository extends JpaRepository<Widgets, Long> {
    List<Widgets> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
