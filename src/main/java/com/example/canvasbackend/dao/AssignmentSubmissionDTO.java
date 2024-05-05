package com.example.canvasbackend.dao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class AssignmentSubmissionDTO {
    private Long assignmentId;
    private String submissionContent;
    private LocalDateTime submissionDate;

    public AssignmentSubmissionDTO(Long assignmentId, String submissionContent, LocalDateTime submissionDate) {
        this.assignmentId = assignmentId;
        this.submissionContent = submissionContent;
        this.submissionDate = submissionDate;
    }
}
