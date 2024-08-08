package com.ssafy.top.widgets.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WidgetsRepository extends JpaRepository<Widgets, Long> {
    void deleteAllByUserId(Long userId);
}
