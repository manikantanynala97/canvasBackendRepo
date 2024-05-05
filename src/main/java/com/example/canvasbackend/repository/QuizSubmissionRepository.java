package com.example.canvasbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.canvasbackend.entity.QuizSubmission;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {

    List<QuizSubmission> findByQuizId(Long quizId);
    Optional<QuizSubmission> findById(Long id);

    @Query("SELECT q  FROM QuizSubmission q WHERE q.quiz.id= :quizId AND q.student.id = :studentId")
    QuizSubmission findSubmissionByQuizIdAndStudentId(Long quizId, Long studentId);
}
