package com.example.canvasbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.canvasbackend.entity.Quiz;
import com.example.canvasbackend.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> findQuizByCourse(Long courseId) {
        return quizRepository.findByCourseId(courseId);
    }

    @Override
    public Quiz findQuizById(Long quizId) {
        return quizRepository.findQuizById(quizId);
    }

    @Override
    public Quiz publishQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setPublished(true);
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz findQuizByQuizId(Long quizId, Long courseId) {
        return quizRepository.findQuizByQuizId(quizId, courseId);
    }

    @Override
    public List<Quiz> findQuizzesByCourse(Long courseId) {
        return quizRepository.findByCourseIdAndPublished(courseId);
    }

}
