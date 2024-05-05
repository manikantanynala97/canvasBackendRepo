package com.example.canvasbackend.service;

// CourseServiceImpl.java

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.example.canvasbackend.entity.Course;
import com.example.canvasbackend.entity.User;
import com.example.canvasbackend.repository.CourseRepo;
import com.example.canvasbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends CourseService {

    private final CourseRepo courseRepository;
    private final UserService userService;

    @Autowired
    public CourseServiceImpl(CourseRepo courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    @Override
    public List<Course> getCoursesByFacultyAndSemester(Long facultyId, String semester) {
        User faculty = userService.getUserById(facultyId);
        if (faculty != null) {
            List<Course> allCourses = courseRepository.findAll();
            return allCourses.stream()
                    .filter(course -> course.getFaculty() != null && course.getFaculty().getId().equals(facultyId))
                    .filter(course -> semester.equals(course.getSemester()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


}
