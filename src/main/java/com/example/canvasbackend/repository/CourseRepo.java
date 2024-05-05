package com.example.canvasbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.canvasbackend.entity.Course;
import com.example.canvasbackend.entity.User;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    List<Course> findByPublished(boolean published);

    List<Course> findByFaculty(User faculty);

    List<Course> findCourseBySemester(@Param("semester") String semester);
}
