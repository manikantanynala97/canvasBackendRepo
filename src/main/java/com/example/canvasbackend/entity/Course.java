package com.example.canvasbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "courses")
@EntityListeners(AuditingEntityListener.class) // Optional: for automatic setting of timestamps, etc.
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "course_title", nullable = false)
    private String title;

    @Column(name = "semester", nullable = false)
    private String semester;

    @Column(name = "is_published", nullable = false)
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonIgnore
    private User faculty;



    public Course() {
    }

    public Course(String title, String semester, boolean published, User faculty) {
        this.title = title;
        this.semester = semester;
        this.published = published;
        this.faculty = faculty;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public User getFaculty() {
        return faculty;
    }

    public void setFaculty(User faculty) {
        this.faculty = faculty;
    }


}
