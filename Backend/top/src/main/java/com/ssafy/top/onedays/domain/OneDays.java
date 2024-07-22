package com.ssafy.top.onedays.domain;

import com.ssafy.top.users.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OneDayId;

    private LocalDate dateData;

    private int focusTime;

    private int targetTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Users user;
}
