package com.example.canvasbackend.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentGradeViewDTO {

    String name;
    Date dueDate;
    Double assignedPoints;
    int totalPoints;
}
