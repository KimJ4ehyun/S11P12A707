package com.ssafy.top.apps.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Apps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="app_id")
    private Long id;
    
    @Column(name = "name")
    private String name;

}
