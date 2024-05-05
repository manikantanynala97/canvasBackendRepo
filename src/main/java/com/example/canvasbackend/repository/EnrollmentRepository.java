package com.example.canvasbackend.repository;

import java.util.Collection;
import java.util.List;

import com.example.canvasbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.canvasbackend.entity.Course;
import com.example.canvasbackend.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByCourseIdAndSemester(Long courseId, String semester);

    List<Enrollment> findByCourse(Course course);

    List<Enrollment> findByCourseId(Long courseId);

    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId AND e.course.faculty.id = :facultyId")
    List<User> findStudentsByCourseAndFaculty(@Param("courseId") Long courseId, @Param("facultyId") Long facultyId);


    @Query("SELECT e.course FROM Enrollment e WHERE e.student.id = :studentId AND e.semester = :semester AND e.course.published = true")
    List<Course> findPublishedCoursesByStudentAndSemester(@Param("studentId") Long studentId,
            @Param("semester") String semester);

}
