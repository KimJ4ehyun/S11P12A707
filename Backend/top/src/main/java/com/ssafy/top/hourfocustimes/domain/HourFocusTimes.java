package com.ssafy.top.hourfocustimes.domain;

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
public class HourFocusTimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hour_focus_time_id")
    private Long id;

    private int focusTime;

    private int timeUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="one_day_id")
    private OneDays oneDays;

}
