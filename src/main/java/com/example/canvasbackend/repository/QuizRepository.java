package com.example.canvasbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.canvasbackend.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCourseId(Long courseId);

    Quiz findQuizById(Long quizId);

    @Query("SELECT q  FROM Quiz q WHERE q.id= :quizId AND q.isPublished AND q.course.id = :courseId")
    Quiz findQuizByQuizId(@Param("quizId") Long quizId, @Param("courseId") Long courseId);

    @Query("SELECT q  FROM Quiz q WHERE q.isPublished AND q.course.id = :courseId")
    List<Quiz> findByCourseIdAndPublished(@Param("courseId") Long courseId);
}
