package com.example.canvasbackend.service;

import com.example.canvasbackend.dao.QuizSubmissionDetailDTO;
import com.example.canvasbackend.entity.QuizSubmission;

import java.util.List;
import java.util.Optional;

public interface QuizSubmissionService {
    QuizSubmission submitQuiz(QuizSubmission quizSubmission);
    List<QuizSubmissionDetailDTO> getSubmissionDetailsForQuiz(Long quizId);

    QuizSubmission findSubmissionByQuizAndStudent(Long quizId, Long id);
}
