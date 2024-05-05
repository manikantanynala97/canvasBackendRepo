package com.example.canvasbackend.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.canvasbackend.dao.*;
import com.example.canvasbackend.entity.*;
import com.example.canvasbackend.repository.CourseRepo;
import com.example.canvasbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final UserService userService;
    private final CourseService courseService;
    private final SyllabusService syllabusService;
    private final AssignmentService assignmentService;
    private final CourseRepo courseRepository;
    private final AssignmentSubmissionService assignmentSubmissionService;
    private final AnnouncementService announcementService;
    private final QuizService quizService;
    private final QuizSubmissionService quizSubmissionService;
    private final GradeService assignmentGradeService;
    private final QuizgradeService quizgradeService;
    private final EnrollmentService enrollmentService;

    @GetMapping("/courses")
    public List<Course> getPublishedCoursesBySemester(
            @RequestParam(name = "studentId", required = false, defaultValue = "5") Long studentId,
            @RequestParam(name = "semester", required = false, defaultValue = "Fall 2024") String semester) {
        return courseService.getPublishedCoursesByStudentAndSemester(studentId, semester);
    }

    @GetMapping("/course/{courseId}")
    public Course getCourseById( @PathVariable("courseId") Long id) {
        return courseRepository.findById(id).get();
    }

    @GetMapping("/content")
    public String getContent(@RequestParam(name = "courseId", required = false) Long courseId,
            @RequestParam(name = "studentId", required = false) Long studentId) {
        return syllabusService.findContentbyCourse(courseId, studentId);

    }
    @GetMapping("/course/{courseId}/syllabus/content")
    public ResponseEntity<String> getSyllabusContentByCourseId(@PathVariable Long courseId) {
        Optional<Syllabus> syllabusOptional = syllabusService.findSyllabusByCourseId(courseId);
        if (syllabusOptional.isPresent()) {
            Syllabus syllabus = syllabusOptional.get();
            return ResponseEntity.ok(syllabus.getContent());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/assignments/all")
    public List<Assignment> getAssignmentsByCourse(
            @RequestParam(name = "courseId", required = false) Long courseId) {
        return assignmentService.findAssignmentsByCourseAndStudent(courseId);
    }

    @GetMapping("/quiz/all")
    public List<Quiz> getQuizzesByCourse(
            @RequestParam(name = "courseId", required = false) Long courseId) {
        return quizService.findQuizzesByCourse(courseId);
    }

    @GetMapping("/assignment/{assignmentId}")
    public Assignment getAssignment(
            @PathVariable("assignmentId") Long assignmentId,
            @RequestParam(name = "courseId", required = false) Long courseId) {
        return assignmentService.findAssignmentByAssignmentId(assignmentId, courseId);
    }

    @GetMapping("/quiz/{quizId}")
    public Quiz getQuiz(
            @PathVariable("quizId") Long quizId,
            @RequestParam(name = "courseId", required = false) Long courseId) {

        return quizService.findQuizByQuizId(quizId, courseId);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDao> getLoggedInStudent() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        UserDao userDao = new UserDao(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok(userDao);
    }

    @PostMapping("/submit-assignment")
    public ResponseEntity<?> submitAssignment(@RequestBody AssignmentSubmissionDTO assignmentSubmissionDTO) {
        try {

            Assignment assignment = assignmentService.findAssignmentById(assignmentSubmissionDTO.getAssignmentId());

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User student = userService.getUserByEmail(userDetails.getUsername());

            AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
            assignmentSubmission.setAssignment(assignment);
            assignmentSubmission.setStudent(student);
            assignmentSubmission.setSubmissionContent(assignmentSubmissionDTO.getSubmissionContent());
            assignmentSubmission.setSubmissionDate(LocalDateTime.now());

            assignmentSubmissionService.submitAssignment(assignmentSubmission);

            return ResponseEntity.accepted().body("Successfully submitted Assignment");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting assignment: " + e.getMessage());
        }
    }

    @PostMapping("/submit-quiz")
    public ResponseEntity<?> submitQuiz(@RequestBody QuizSubmissionDTO quizSubmissionDTO) {
        try {
            Quiz quiz = quizService.findQuizById(quizSubmissionDTO.getQuizId());

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User student = userService.getUserByEmail(userDetails.getUsername());

            QuizSubmission quizSubmission = new QuizSubmission();
            quizSubmission.setQuiz(quiz);
            quizSubmission.setStudent(student);
            quizSubmission.setSubmissionContent(quizSubmissionDTO.getSubmissionContent());
            quizSubmission.setSubmissionDate(LocalDateTime.now());
            quizSubmissionService.submitQuiz(quizSubmission);
            return ResponseEntity.accepted().body("Successfully submitted Quiz");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting quiz: " + e.getMessage());
        }
    }

    @GetMapping("/course/{courseId}/announcements")
    public ResponseEntity<List<Announcement>> getAnnouncementsForCourse(@PathVariable Long courseId) {
        List<Announcement> announcements = announcementService.getAnnouncementsByCourse(courseId);
        if (announcements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/assignment/{assignmentId}/submission")
    public ResponseEntity<AssignmentSubmissionDTO> getSubmissionForStudent(@PathVariable Long assignmentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        AssignmentSubmission assignmentSubmission = assignmentSubmissionService.findSubmissionByAssignmentAndStudent(assignmentId,user.getId());
        if(assignmentSubmission == null) {
            return ResponseEntity.ok(null);
        }
        AssignmentSubmissionDTO dto = new AssignmentSubmissionDTO(assignmentSubmission.getAssignment().getId(),
                assignmentSubmission.getSubmissionContent(),assignmentSubmission.getSubmissionDate());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/quiz/{quizId}/submission")
    public ResponseEntity<QuizSubmissionDTO> getQuizSubmissionForStudent(@PathVariable Long quizId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        QuizSubmission quizSubmission = quizSubmissionService.findSubmissionByQuizAndStudent(quizId,user.getId());
        if(quizSubmission == null) {
            return ResponseEntity.ok(null);
        }
        QuizSubmissionDTO dto = new QuizSubmissionDTO(quizSubmission.getQuiz().getId(),
                quizSubmission.getSubmissionContent(),quizSubmission.getSubmissionDate());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/course/{courseId}/grades")
    public ResponseEntity<List<StudentGradeViewDTO>> getGradesByStudent(@PathVariable Long courseId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<StudentGradeViewDTO> quizGrades = quizgradeService.getQuizGradesByStudentAndCourse(courseId,user.getId());
        List<StudentGradeViewDTO> assignmentGrades = assignmentGradeService.getAssignmentGradesByStudentAndCourse(courseId,user.getId());
        List<StudentGradeViewDTO> combinedList = new ArrayList<>(quizGrades);
        combinedList.addAll(assignmentGrades);
        return ResponseEntity.ok(combinedList);
    }



}
