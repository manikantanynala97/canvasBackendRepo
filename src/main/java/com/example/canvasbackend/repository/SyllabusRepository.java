package com.example.canvasbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.canvasbackend.entity.Syllabus;

public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
    Optional<Syllabus> findByCourseId(Long courseId);

    // select content from syllabus where course_id = (select course_id from
    // enrollmentsyllabusServiceImpl where student_id = 7 and course_id =1)

    @Query("SELECT s.content FROM Syllabus s where s.course.published and s.course.id IN (SELECT e.course.id FROM Enrollment e WHERE e.student.id=:studentId AND e.course.id=:courseId)")
    Optional<String> findContentByCourseIdAndStudentId(@Param("courseId") Long courseId,
            @Param("studentId") Long studentId);

    // @Query("SELECT s.content FROM Syllabus s where s.course.id=:courseId")
    // Optional<String> findContentByCourseIdAndStudentId(@Param("courseId") Long
    // courseId);
    // // @Param("courseId") Long courseId);

}
