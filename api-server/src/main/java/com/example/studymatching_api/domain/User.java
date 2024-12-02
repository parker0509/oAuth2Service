package com.example.studymatching_api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;


    @NotNull
    private String name;

    @NotNull
    private String email;

    @Column
    private String picture;

    private String gender;

    private String age;


    @Builder
    public User(String name, String email, String picture,String gender,String age) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.gender= gender;
        this.age = age;
    }

    // 사용자 정보 업데이트 메서드 추가
    public void update(String name, String email) {
        this.name = name;
        this.email = email;
        this.picture = picture != null ? picture : "N/A";
        this.gender = gender != null ? gender : "N/A";
        this.age = age != null ? age : "N/A";
    }
}