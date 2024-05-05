package com.example.canvasbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.canvasbackend.entity.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourseId(Long courseId);

    Assignment findAssignmentById(Long assignmentId);

    @Query("SELECT a  FROM Assignment a WHERE a.isPublished AND a.course.id = :courseId")
    List<Assignment> findByCourseIdAndStudentId(@Param("courseId") Long courseId);

    @Query("SELECT a  FROM Assignment a WHERE a.id= :assignmentId AND a.isPublished AND a.course.id = :courseId")
    Assignment findAssignmentByAssignmentId(@Param("assignmentId") Long assignmentId, @Param("courseId") Long courseId);
}
