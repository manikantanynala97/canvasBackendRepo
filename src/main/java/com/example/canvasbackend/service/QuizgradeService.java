package com.example.canvasbackend.service;

import com.example.canvasbackend.dao.StudentGradeViewDTO;
import com.example.canvasbackend.entity.Quizgrade;
import com.example.canvasbackend.dao.QuizgradeDTO;
import java.util.List;

public interface QuizgradeService {
    Quizgrade saveQuizGrade(Quizgrade quizgrade); // existing method

    // new method to get quiz grade by quiz and student
    QuizgradeDTO getQuizGradeByQuizAndStudent(Long quizId, Long studentId);

    // new method to get all quiz grades for a specific student
    List<QuizgradeDTO> getQuizGradesByStudent(Long studentId);

    List<StudentGradeViewDTO> getQuizGradesByStudentAndCourse(Long courseId, Long id);
}
