package com.example.canvasbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quizgrade")
public class Quizgrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizgradeId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;

    private Double quizgrade;

    public Double getGrade() {
        return quizgrade;
    }

    public void setGrade(Double grade) {
        this.quizgrade = grade;
    }


}

