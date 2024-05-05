package com.example.canvasbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_submissions")
public class QuizSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private User student;

    @Lob
    @Column(name = "submission_content", nullable = false)
    private String submissionContent;

    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;



}
