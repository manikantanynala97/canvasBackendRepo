package com.example.canvasbackend.service;

import com.example.canvasbackend.dao.StudentGradeViewDTO;
import com.example.canvasbackend.entity.*;
import com.example.canvasbackend.repository.GradeRepository;
import com.example.canvasbackend.dao.GradeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final UserService userService;
    private final AssignmentService assignmentService;

    // existing method
    @Override
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    // new method to fetch grade by assignment and student
    @Override
    public GradeDTO getGradeByAssignmentAndStudent(Long assignmentId, Long studentId) {
        Assignment assignment = assignmentService.findAssignmentById(assignmentId);
        User student = userService.getUserById(studentId);
        Grade grade = gradeRepository.findByAssignmentAndStudent(assignment, student);
        return new GradeDTO(grade.getStudent().getId(), grade.getAssignment().getId(), grade.getGrade());
    }

    // new method to fetch all grades for a specific student
    @Override
    public List<GradeDTO> getGradesByStudent(Long studentId) {
        User student = userService.getUserById(studentId);
        List<Grade> grades = gradeRepository.findAllByStudent(student);
        return grades.stream()
                .map(g -> new GradeDTO(g.getStudent().getId(), g.getAssignment().getId(), g.getGrade()))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentGradeViewDTO> getAssignmentGradesByStudentAndCourse(Long courseId, Long studentId) {
        List<Grade> assignmentGrades = gradeRepository.findAllByStudentAndCourse(courseId,studentId);
        List<Assignment> assignments = assignmentService.findAssignmentsByCourse(courseId);
        List<StudentGradeViewDTO> grades = new ArrayList<>();
        if(!assignmentGrades.isEmpty()) {
            grades = assignmentGrades.stream().map(assignmentGrade -> {
                StudentGradeViewDTO dto = new StudentGradeViewDTO();
                dto.setName(assignmentGrade.getAssignment().getTitle());
                dto.setDueDate(assignmentGrade.getAssignment().getDueDate());
                dto.setTotalPoints(assignmentGrade.getAssignment().getTotalPoints());
                dto.setAssignedPoints(assignmentGrade.getGrade());
                assignments.remove(assignmentGrade.getAssignment());
                return dto;
            }).collect(Collectors.toList());
        }
        for(Assignment assignment: assignments) {
            StudentGradeViewDTO dto = new StudentGradeViewDTO();
            dto.setName(assignment.getTitle());
            dto.setDueDate(assignment.getDueDate());
            dto.setTotalPoints(assignment.getTotalPoints());
            grades.add(dto);
        }
        return grades;
    }

    public List<Grade> getGradesByFacultyAndCourse(Long facultyId, Long courseId) {
        // Implement logic to retrieve grades by faculty and course
        return gradeRepository.findByFacultyIdAndCourseId(facultyId, courseId);
    }

    public List<Grade> getGradesByCourseId(Long courseId)
    {
        return gradeRepository.findAllByAssignment_CourseId(courseId);
    }
}
