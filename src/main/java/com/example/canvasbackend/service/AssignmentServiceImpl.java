package com.example.canvasbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.canvasbackend.entity.Assignment;
import com.example.canvasbackend.repository.AssignmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Override
    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> findAssignmentsByCourse(Long courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }

    @Override
    public List<Assignment> findAssignmentsByCourseAndStudent(Long courseId) {
        return assignmentRepository.findByCourseIdAndStudentId(courseId);
    }

    @Override
    public Assignment publishAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        assignment.setPublished(true);
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment findAssignmentById(Long assignmentId) {
        return assignmentRepository.findAssignmentById(assignmentId);
    }

    @Override
    public Assignment findAssignmentByAssignmentId(Long assignmentId, Long courseId) {
        return assignmentRepository.findAssignmentByAssignmentId(assignmentId, courseId);
    }


}
