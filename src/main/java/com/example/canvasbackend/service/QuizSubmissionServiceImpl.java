package com.example.canvasbackend.service;

import com.example.canvasbackend.dao.QuizSubmissionDetailDTO;
import com.example.canvasbackend.entity.Course;
import com.example.canvasbackend.entity.Quizgrade;
import com.example.canvasbackend.entity.QuizSubmission;
import com.example.canvasbackend.entity.User;
import com.example.canvasbackend.entity.Enrollment;
import com.example.canvasbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizSubmissionServiceImpl implements QuizSubmissionService {

    private final QuizSubmissionRepository quizSubmissionRepository;
    @Autowired
    private QuizgradeRepository quizGradeRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public QuizSubmission submitQuiz(QuizSubmission quizSubmission) {
        return quizSubmissionRepository.save(quizSubmission);
    }

    @Override
    public List<QuizSubmissionDetailDTO> getSubmissionDetailsForQuiz(Long quizId) {
        Course course = quizRepository.findQuizById(quizId).getCourse();
        List<User> enrolledStudents = enrollmentRepository.findByCourse(course)
                .stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
        List<QuizSubmission> quizSubmissions = quizSubmissionRepository.findByQuizId(quizId);
        List<QuizSubmissionDetailDTO> quizSubmissionDetails = new ArrayList<>();
        if (!quizSubmissions.isEmpty()) {
            quizSubmissionDetails = quizSubmissions.stream().map(submission -> {
                QuizSubmissionDetailDTO dto = new QuizSubmissionDetailDTO();
                dto.setStudentId(submission.getStudent().getId());
                dto.setStudentName(submission.getStudent().getName());
                dto.setSubmissionContent(submission.getSubmissionContent());
                dto.setSubmissionDate(submission.getSubmissionDate());
                dto.setQuizId(submission.getQuiz().getId());
                Quizgrade quizGrade = quizGradeRepository.findByQuizAndStudent(submission.getQuiz(), submission.getStudent());
                if (quizGrade != null) {
                    dto.setQuizGrade(quizGrade.getGrade());
                }
                enrolledStudents.remove(submission.getStudent());
                return dto;
            }).collect(Collectors.toList());
        }
        for (User student : enrolledStudents) {
            QuizSubmissionDetailDTO dto = new QuizSubmissionDetailDTO();
            dto.setStudentId(student.getId());
            dto.setStudentName(student.getName());
            quizSubmissionDetails.add(dto);
        }

        return quizSubmissionDetails;
    }

    @Override
    public QuizSubmission findSubmissionByQuizAndStudent(Long quizId, Long studentId) {
        return quizSubmissionRepository.findSubmissionByQuizIdAndStudentId(quizId,studentId);
    }


}
