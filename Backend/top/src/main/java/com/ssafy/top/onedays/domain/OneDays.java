package com.ssafy.top.onedays.domain;

import com.ssafy.top.users.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "one_days", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_data"})})
public class OneDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="one_day_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate dateData;

    @Column(nullable = false)
    private int targetTime;

    @Column(nullable = false)
    private int focusTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Users user;

    public void updateFocusTime(int focusTime) { this.focusTime = focusTime; }
    public void updateTargetTime(int targetTime) {
        this.targetTime = targetTime;
    }
}
