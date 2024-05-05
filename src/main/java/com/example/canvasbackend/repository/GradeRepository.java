package com.example.canvasbackend.repository;

import com.example.canvasbackend.entity.Assignment;
import com.example.canvasbackend.entity.Grade;
import com.example.canvasbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    // existing method
    Grade findByAssignmentAndStudent(Assignment assignment, User student);

    // new method to fetch all grades for a specific student
    List<Grade> findAllByStudent(User student);


    @Query("SELECT e.student FROM Enrollment e " +
            "JOIN e.course c " +
            "JOIN c.faculty f " +
            "WHERE c.id = :courseId AND f.id = :facultyId")
    List<User> findStudentsByCourseAndFaculty(@Param("courseId") Long courseId, @Param("facultyId") Long facultyId);

    List<Grade> findAllByAssignment_CourseId(Long courseId);

    @Query("SELECT g  FROM Grade g WHERE g.student.id = :studentId AND g.assignment.course.id = :courseId")
    List<Grade> findAllByStudentAndCourse(@Param("courseId")Long courseId, @Param("studentId")Long studentId);

    // New method to fetch all grades by faculty and course
    @Query("SELECT g FROM Grade g WHERE g.assignment.course.faculty.id = :facultyId AND g.assignment.course.id = :courseId")
    List<Grade> findByFacultyIdAndCourseId(@Param("facultyId") Long facultyId, @Param("courseId") Long courseId);
}
