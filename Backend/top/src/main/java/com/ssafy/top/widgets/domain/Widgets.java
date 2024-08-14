package com.ssafy.top.widgets.domain;

import com.ssafy.top.users.domain.Users;
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
public class Widgets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "widget_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private WidgetType name;

    private int x;

    private int y;

    private int height;

    private int width;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
