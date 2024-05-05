package com.example.canvasbackend.service;

import java.util.List;

import com.example.canvasbackend.entity.Assignment;
import com.example.canvasbackend.entity.Quiz;

public interface QuizService {
    Quiz saveQuiz(Quiz quiz);

    List<Quiz> findQuizByCourse(Long courseId);

    Quiz publishQuiz(Long quizId);

    Quiz findQuizById(Long quizId);

    Quiz findQuizByQuizId(Long quizId, Long courseId);

    List<Quiz> findQuizzesByCourse(Long courseId);
}
