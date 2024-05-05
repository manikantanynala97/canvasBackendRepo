package com.example.canvasbackend.service;

import java.util.List;

import com.example.canvasbackend.entity.Assignment;

public interface AssignmentService {
    Assignment saveAssignment(Assignment assignment);

    List<Assignment> findAssignmentsByCourse(Long courseId);

    Assignment publishAssignment(Long assignmentId);

    Assignment findAssignmentById(Long assignmentId);

    List<Assignment> findAssignmentsByCourseAndStudent(Long courseId);

    Assignment findAssignmentByAssignmentId(Long assignmentId, Long courseId);

}
