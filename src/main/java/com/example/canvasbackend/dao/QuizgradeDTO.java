package com.example.canvasbackend.dao;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class QuizgradeDTO {
    private Long studentId;
    private Long quizId;
    private Double quizgrade;

    public QuizgradeDTO(Long id, Long id1, Double quizgrade) {
    }
}
