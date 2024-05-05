// SyllabusService.java

package com.example.canvasbackend.service;

import java.util.Optional;

import com.example.canvasbackend.entity.Syllabus;

public interface SyllabusService {
    Syllabus addSyllabus(Long courseId, String content);

    Optional<Syllabus> findSyllabusByCourseId(Long courseId);

    String findContentbyCourse(Long courseId, Long studentId);
}
