package com.example.canvasbackend.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class QuizSubmissionDTO {
    private Long quizId;
    private String submissionContent;
    private LocalDateTime submissionDate;

    public QuizSubmissionDTO(Long quizId, String submissionContent, LocalDateTime submissionDate) {
        this.quizId = quizId;
        this.submissionContent = submissionContent;
        this.submissionDate = submissionDate;
    }
}
