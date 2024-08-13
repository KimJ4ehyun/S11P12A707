package com.ssafy.top.appfocustimes.domain;

import com.ssafy.top.onedays.domain.OneDays;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppFocusTimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="app_focus_time_id")
    private Long id;

    private String app;

    private int startTime;

    private int focusTime;

    private int timeUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="one_day_id")
    private OneDays oneDays;

    public void updateStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void updateFocusTime(int focusTime) {
        this.focusTime = focusTime;
    }
}
