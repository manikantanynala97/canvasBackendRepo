package com.example.canvasbackend.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDetailDTO {
    private Long studentId;
    private String studentName;
    private String submissionContent;
    private Double grade;
    private LocalDateTime submissionDate;
}

