package com.example.canvasbackend.service;

import com.example.canvasbackend.dao.StudentGradeViewDTO;
import com.example.canvasbackend.entity.Grade;
import com.example.canvasbackend.dao.GradeDTO;
import java.util.List;

public interface GradeService {
    Grade saveGrade(Grade grade); // existing method

    // new method to get grade by assignment and student
    GradeDTO getGradeByAssignmentAndStudent(Long assignmentId, Long studentId);

    // new method to get all grades for a specific student
    List<GradeDTO> getGradesByStudent(Long studentId);

    List<StudentGradeViewDTO> getAssignmentGradesByStudentAndCourse(Long courseId, Long id);

    List<Grade> getGradesByFacultyAndCourse(Long facultyId, Long courseId);

    List<Grade> getGradesByCourseId(Long courseId);
}
