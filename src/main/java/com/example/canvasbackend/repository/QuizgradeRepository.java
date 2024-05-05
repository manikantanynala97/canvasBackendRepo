package com.example.canvasbackend.repository;

import com.example.canvasbackend.entity.Quizgrade;
import com.example.canvasbackend.entity.Quiz;
import com.example.canvasbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizgradeRepository extends JpaRepository<Quizgrade, Long> {

    // Existing method to save a quiz grade (inherited from JpaRepository)

    // New method to fetch quiz grade by quiz and student
    Quizgrade findByQuizAndStudent(Quiz quiz, User student);

    // New method to fetch all quiz grades for a specific student
    List<Quizgrade> findAllByStudent(User student);

    @Query("SELECT q  FROM Quizgrade q WHERE q.student.id = :studentId AND q.quiz.course.id = :courseId")
    List<Quizgrade> findAllByStudentAndCourse(@Param("courseId")Long courseId, @Param("studentId")Long studentId);
}
