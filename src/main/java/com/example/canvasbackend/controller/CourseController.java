package com.example.canvasbackend.controller;

import com.example.canvasbackend.dao.UserDao;
import com.example.canvasbackend.entity.Course;
import com.example.canvasbackend.entity.User;
import com.example.canvasbackend.service.CourseService;
import com.example.canvasbackend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @Autowired
    public CourseController(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courses);
    }


    @GetMapping("/published")
    public ResponseEntity<List<Course>> getPublishedCourses() {
        List<Course> courses = courseService.getPublishedCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<UserDao>> getStudentsByCourseId(@PathVariable Long courseId) {
        List<User> students = enrollmentService.getStudentsByCourseId(courseId);
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UserDao> stds = students.stream().map(student -> {
            return new UserDao(student.getId(),student.getName(),student.getEmail());
        }).toList();
        return ResponseEntity.ok(stds);
    }
}
