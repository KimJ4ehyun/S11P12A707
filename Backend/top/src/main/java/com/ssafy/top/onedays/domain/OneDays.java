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
public class OneDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="one_day_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate dateData;

    @Column(nullable = false)
    private int focusTime;

    @Column(nullable = false)
    private int targetTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Users user;
}
