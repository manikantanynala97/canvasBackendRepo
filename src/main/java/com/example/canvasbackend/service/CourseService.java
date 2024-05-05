package com.example.canvasbackend.service;

import java.util.Collections;
import java.util.List;

import com.example.canvasbackend.entity.User;
import jakarta.persistence.*;
import com.example.canvasbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.canvasbackend.entity.Course;
import com.example.canvasbackend.repository.CourseRepo;
import com.example.canvasbackend.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    @Autowired
    private CourseRepo courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private UserRepository userRepository;

    private CourseServiceImpl courseServiceImpl;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getPublishedCourses() {
        return courseRepository.findByPublished(true);
    }

    public Course getCourse(Long courseId) {
        return courseRepository.getReferenceById(courseId);
    }

    public List<Course> getCoursesBySem(String semester) {
        return courseRepository.findCourseBySemester(semester);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> getPublishedCoursesByStudentAndSemester(Long studentId, String semester) {
        return enrollmentRepository.findPublishedCoursesByStudentAndSemester(studentId, semester);
    }

    public List<Course> getCoursesByFaculty(Long facultyId) {
        User faculty = userRepository.findById(facultyId).orElse(null);
        if (faculty != null) {
            return courseRepository.findByFaculty(faculty);
        }
        return Collections.emptyList();
    }

    public List<User> getStudentsByCourseAndFaculty(Long facultyId, Long courseId)
    {
         return enrollmentRepository.findStudentsByCourseAndFaculty(facultyId,courseId);
    }

    public List<Course> getCoursesByFacultyAndSemester(Long facultyId, String semester)
    {
        return courseServiceImpl.getCoursesByFacultyAndSemester(facultyId, semester);
    }

}
