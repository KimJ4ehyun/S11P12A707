package com.ssafy.top.widgets.domain;

import com.ssafy.top.users.domain.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class Widgets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "widget_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private WidgetType widgetType;

    private int xPosition;

    private int yPosition;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
