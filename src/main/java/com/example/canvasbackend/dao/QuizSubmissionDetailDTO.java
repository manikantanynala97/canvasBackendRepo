package com.example.canvasbackend.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmissionDetailDTO {
    private Long studentId;
    private String studentName;
    private String submissionContent;
    private Double quizGrade;
    private LocalDateTime submissionDate;
    private Long quizId;
}

