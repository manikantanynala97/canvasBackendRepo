package com.example.canvasbackend.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GradeDTO {
    private Long studentId;
    private Long assignmentId;
    private Double grade;

    public GradeDTO(Long studentId, Long assignmentId, Double grade) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.grade = grade;
    }


}