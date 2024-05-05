package com.example.canvasbackend.service;

import com.example.canvasbackend.dao.AssignmentSubmissionDTO;
import com.example.canvasbackend.dao.SubmissionDetailDTO;
import com.example.canvasbackend.entity.*;

import com.example.canvasbackend.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {


    private final AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public AssignmentSubmission submitAssignment(AssignmentSubmission assignmentSubmission) {
        return assignmentSubmissionRepository.save(assignmentSubmission);
    }

    @Override
    public Optional<AssignmentSubmission> findSubmissionById(Long id) {
        return assignmentSubmissionRepository.findById(id);
    }

    @Override
    public List<SubmissionDetailDTO> getSubmissionDetailsForAssignment(Long assignmentId) {
        Course course = assignmentRepository.findAssignmentById(assignmentId).getCourse();
        List<User> enrolledStudents = enrollmentRepository.findByCourse(course)
                .stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByAssignmentId(assignmentId);
        List<SubmissionDetailDTO> submissionDetails = new ArrayList<>();
        if (!submissions.isEmpty()) {
            submissionDetails = submissions.stream().map(submission -> {
                SubmissionDetailDTO dto = new SubmissionDetailDTO();
                dto.setStudentId(submission.getStudent().getId());
                dto.setStudentName(submission.getStudent().getName());
                dto.setSubmissionContent(submission.getSubmissionContent());
                dto.setSubmissionDate(submission.getSubmissionDate());
                Grade grade = gradeRepository.findByAssignmentAndStudent(submission.getAssignment(), submission.getStudent());
                if (grade != null) {
                    dto.setGrade(grade.getGrade());
                }
                enrolledStudents.remove(submission.getStudent());
                return dto;
            }).collect(Collectors.toList());
        }
        for (User student : enrolledStudents) {
            SubmissionDetailDTO dto = new SubmissionDetailDTO();
            dto.setStudentId(student.getId());
            dto.setStudentName(student.getName());
            submissionDetails.add(dto);
        }

        return submissionDetails;
    }

    @Override
    public AssignmentSubmission findSubmissionByAssignmentAndStudent(Long assignmentId, Long id) {
        return assignmentSubmissionRepository.findSubmissionByAssignmentIdAndStudentId(assignmentId,id);
    }

}

