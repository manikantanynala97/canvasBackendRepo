package com.example.canvasbackend.dao;

import com.example.canvasbackend.entity.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDao {

    private Long id;

    private String name;

    private String email;

    public UserDao(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
