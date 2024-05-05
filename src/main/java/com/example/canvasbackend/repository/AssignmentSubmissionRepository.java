package com.example.canvasbackend.repository;

import com.example.canvasbackend.dao.AssignmentSubmissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.canvasbackend.entity.AssignmentSubmission;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
    Optional<AssignmentSubmission> findById(Long id);
    List<AssignmentSubmission> findByAssignmentId(Long assignmentId);

    @Query("SELECT a  FROM AssignmentSubmission a WHERE a.assignment.id= :assignmentId AND a.student.id = :id")
    AssignmentSubmission findSubmissionByAssignmentIdAndStudentId(Long assignmentId, Long id);
}
