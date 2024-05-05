package com.example.canvasbackend.service;

import com.example.canvasbackend.dao.StudentGradeViewDTO;
import com.example.canvasbackend.entity.QuizSubmission;
import com.example.canvasbackend.entity.Quizgrade;
import com.example.canvasbackend.repository.QuizSubmissionRepository;
import com.example.canvasbackend.repository.QuizgradeRepository;
import com.example.canvasbackend.dao.QuizgradeDTO;
import com.example.canvasbackend.entity.Quiz;
import com.example.canvasbackend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizgradeServiceImpl implements QuizgradeService {

    private final QuizgradeRepository quizgradeRepository;
    private final QuizService quizService;
    private final UserService userService;

    // Existing method to save a quiz grade
    @Override
    public Quizgrade saveQuizGrade(Quizgrade quizgrade) {
        return quizgradeRepository.save(quizgrade);
    }

    // New method to fetch quiz grade by quiz and student
    @Override
    public QuizgradeDTO getQuizGradeByQuizAndStudent(Long quizId, Long studentId) {
        Quiz quiz = quizService.findQuizById(quizId);
        User student = userService.getUserById(studentId);
        Quizgrade quizgrade = quizgradeRepository.findByQuizAndStudent(quiz, student);
        return new QuizgradeDTO(quizgrade.getStudent().getId(), quizgrade.getQuiz().getId(), quizgrade.getQuizgrade());
    }

    // New method to fetch all quiz grades for a specific student
    @Override
    public List<QuizgradeDTO> getQuizGradesByStudent(Long studentId) {
        User student = userService.getUserById(studentId);
        List<Quizgrade> quizgrades = quizgradeRepository.findAllByStudent(student);
        return quizgrades.stream()
                .map(qg -> new QuizgradeDTO(qg.getStudent().getId(), qg.getQuiz().getId(), qg.getQuizgrade()))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentGradeViewDTO> getQuizGradesByStudentAndCourse(Long courseId, Long studentId) {
        List<Quizgrade> quizGrades = quizgradeRepository.findAllByStudentAndCourse(courseId,studentId);
        List<Quiz> quizzes = quizService.findQuizzesByCourse(courseId);
        List<StudentGradeViewDTO> grades = new ArrayList<>();
        if(!quizGrades.isEmpty()) {
            grades = quizGrades.stream().map(quizGrade -> {
                StudentGradeViewDTO dto = new StudentGradeViewDTO();
                dto.setName(quizGrade.getQuiz().getTitle());
                dto.setDueDate(quizGrade.getQuiz().getDueDate());
                dto.setTotalPoints(quizGrade.getQuiz().getTotalPoints());
                dto.setAssignedPoints(quizGrade.getQuizgrade());
                quizzes.remove(quizGrade.getQuiz());
                return dto;
            }).collect(Collectors.toList());
        }
        for(Quiz quiz: quizzes) {
            StudentGradeViewDTO dto = new StudentGradeViewDTO();
            dto.setName(quiz.getTitle());
            dto.setDueDate(quiz.getDueDate());
            dto.setTotalPoints(quiz.getTotalPoints());
            grades.add(dto);
        }
        return grades;
    }
}
