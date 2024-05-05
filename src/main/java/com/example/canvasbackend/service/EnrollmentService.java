package com.example.canvasbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.canvasbackend.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.canvasbackend.entity.Enrollment;
import com.example.canvasbackend.entity.User;
import com.example.canvasbackend.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<String> getStudentsByCourseIdAndSemester(Long courseId, String semester) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseIdAndSemester(courseId, semester);
        return enrollments.stream()
                .map(Enrollment::getStudent)
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public List<User> getStudentsByCourseId(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        return enrollments.stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
    }

}
