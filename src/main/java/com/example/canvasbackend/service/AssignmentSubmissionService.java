package com.example.canvasbackend.service;

import com.example.canvasbackend.dao.AssignmentSubmissionDTO;
import com.example.canvasbackend.dao.SubmissionDetailDTO;
import com.example.canvasbackend.entity.AssignmentSubmission;

import java.util.List;
import java.util.Optional;

public interface AssignmentSubmissionService {
    AssignmentSubmission submitAssignment(AssignmentSubmission assignmentSubmission);
    Optional<AssignmentSubmission> findSubmissionById(Long id);
    public List<SubmissionDetailDTO> getSubmissionDetailsForAssignment(Long assignmentId);

    AssignmentSubmission findSubmissionByAssignmentAndStudent(Long assignmentId, Long id);
}