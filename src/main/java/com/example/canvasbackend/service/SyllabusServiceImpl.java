
package com.example.canvasbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.canvasbackend.entity.Course;
import com.example.canvasbackend.entity.Syllabus;
import com.example.canvasbackend.repository.CourseRepo;
import com.example.canvasbackend.repository.SyllabusRepository;

@Service
public class SyllabusServiceImpl implements SyllabusService {

    @Autowired
    private final SyllabusRepository syllabusRepository;
    private final CourseRepo courseRepository;

    public SyllabusServiceImpl(SyllabusRepository syllabusRepository, CourseRepo courseRepository) {
        this.syllabusRepository = syllabusRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Syllabus addSyllabus(Long courseId, String content) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course with id: " + courseId + " not found."));
        Optional<Syllabus> syllabus = syllabusRepository.findByCourseId(courseId);
        if (syllabus.isPresent()) {
            syllabus.get().setContent(content);
            return syllabusRepository.save(syllabus.get());
        }
        Syllabus newSyllabus = new Syllabus();
        newSyllabus.setCourse(course);
        newSyllabus.setContent(content);
        return syllabusRepository.save(newSyllabus);
    }

    public Optional<Syllabus> findSyllabusByCourseId(Long courseId) {
        return syllabusRepository.findByCourseId(courseId);
    }

    public String findContentbyCourse(Long courseId, Long studentId) {
        Optional<String> result = syllabusRepository.findContentByCourseIdAndStudentId(courseId, studentId);

        return result.isEmpty() ? "" : String.valueOf(result);

    }
}
