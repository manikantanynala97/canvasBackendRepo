package com.example.canvasbackend.dao;

import com.example.canvasbackend.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CourseDao {

    private Long id;

    private String title;

    private String semester;

    private boolean published;

    private String faculty;

    public CourseDao(Long id, String title, String semester, boolean published, String faculty) {
        this.id = id;
        this.title = title;
        this.semester = semester;
        this.published = published;
        this.faculty = faculty;
    }
}
