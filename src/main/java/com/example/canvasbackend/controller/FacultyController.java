package com.example.canvasbackend.controller;

import com.example.canvasbackend.dao.*;
import com.example.canvasbackend.entity.*;
import com.example.canvasbackend.service.*;
import com.example.canvasbackend.entity.Announcement;
import com.example.canvasbackend.repository.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/faculty")
@CrossOrigin(origins = "http://localhost:3000")
public class FacultyController {

    private final CourseService courseService;
    private final CourseRepo courseRepository;
    private final AssignmentService assignmentService;
    private final QuizService quizService;
    private final UserService userService;
    @Autowired
    private SyllabusService syllabusService;
    private final AnnouncementService announcementService;
    private final GradeService gradeService;
    private final QuizgradeService quizgradeService;
    private final EnrollmentService enrollmentService;
    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;
    @Autowired
    private QuizSubmissionService quizSubmissionService;


    @PostMapping("/course/{courseId}/publish")
    public ResponseEntity<?> publishCourse(@PathVariable("courseId") Long id) {
        Course course = courseService.getCourse(id);
        course.setPublished(true);
        courseService.saveCourse(course);
        return ResponseEntity.accepted().body("Successfully published course");
    }

    @GetMapping("/course/{courseId}")
    public Course getCourseById( @PathVariable("courseId") Long id) {
        return courseRepository.findById(id).get();
    }

    @GetMapping("/courses/{courseId}/assignments")
    public ResponseEntity<List<Assignment>> getAssignmentsForCourse(@PathVariable Long courseId) {
        List<Assignment> assignments = assignmentService.findAssignmentsByCourse(courseId);
        if (assignments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/courses/{courseId}/quiz")
    public ResponseEntity<List<Quiz>> getQuizForCourse(@PathVariable Long courseId) {
        List<Quiz> quiz = quizService.findQuizByCourse(courseId);
        if (quiz.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/courses/{courseId}/syllabus/content")
    public ResponseEntity<String> getSyllabusContentByCourseId(@PathVariable Long courseId) {
        Optional<Syllabus> syllabusOptional = syllabusService.findSyllabusByCourseId(courseId);
        if (syllabusOptional.isPresent()) {
            Syllabus syllabus = syllabusOptional.get();
            return ResponseEntity.ok(syllabus.getContent());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/courses/{courseId}/announcements")
    public ResponseEntity<List<Announcement>> getAnnouncementsForCourse(@PathVariable Long courseId) {
        List<Announcement> announcements = announcementService.getAnnouncementsByCourse(courseId);
        if (announcements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(announcements);
    }



    @PostMapping("/assignment/add")
    public ResponseEntity<?> addAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        try {
            Course course = courseService.getCourse(assignmentDTO.getCourseId());
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with ID " + assignmentDTO.getCourseId() + " not found.");
            }
            Assignment assignment = new Assignment();
            assignment.setCourse(course);
            assignment.setTitle(assignmentDTO.getTitle());
            assignment.setContent(assignmentDTO.getContent());
            assignment.setDueDate(assignmentDTO.getDueDate());
            assignment.setTotalPoints(assignmentDTO.getTotalPoints());
            assignment.setPublished(assignmentDTO.isPublished());
            assignmentService.saveAssignment(assignment);
            return ResponseEntity.accepted().body("Successfully created Assignment");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating assignment: " + e.getMessage());
        }
    }
    @PostMapping("/quiz/add")
    public ResponseEntity<?> addAssignment(@RequestBody QuizDTO quizDTO) {

        try {
            Course course = courseService.getCourse(quizDTO.getCourseId());
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with ID " + quizDTO.getCourseId() + " not found.");
            }
            Quiz quiz = new Quiz();
            quiz.setCourse(course);
            quiz.setTitle(quizDTO.getTitle());
            quiz.setContent(quizDTO.getContent());
            quiz.setDueDate(quizDTO.getDueDate());
            quiz.setTotalPoints(quizDTO.getTotalPoints());
            quiz.setPublished(quizDTO.isPublished());
            quizService.saveQuiz(quiz);
            return ResponseEntity.accepted().body("Successfully created Quiz");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating quiz: " + e.getMessage());
        }
    }
    @PostMapping("/assignments/{assignmentId}/publish")
    public ResponseEntity<?> publishAssignment(@PathVariable Long assignmentId) {
        try {
            Assignment publishedAssignment = assignmentService.publishAssignment(assignmentId);
            return ResponseEntity.ok(publishedAssignment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error publishing assignment: " + e.getMessage());
        }
    }
    @PostMapping("/quiz/{quizId}/publish")
    public ResponseEntity<?> publishQuiz(@PathVariable Long quizId) {
        try {
            Quiz publishedQuiz = quizService.publishQuiz(quizId);
            return ResponseEntity.ok(publishedQuiz);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error publishing Quiz: " + e.getMessage());
        }
    }

    @PostMapping("/courses/{courseId}/syllabus")
    public ResponseEntity<?> addSyllabus(@PathVariable Long courseId, @RequestBody SyllabusDTO syllabusDTO) {
        try {
            Syllabus syllabus = syllabusService.addSyllabus(courseId, syllabusDTO.getContent());
            return new ResponseEntity<>(syllabus, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/grades/assign")
    public ResponseEntity<?> addGrade(@RequestBody GradeDTO gradeDTO) {
        try {
            Assignment assignment = assignmentService.findAssignmentById(gradeDTO.getAssignmentId());
            User student = userService.getUserById(gradeDTO.getStudentId());
            Grade grade = new Grade();
            grade.setStudent(student);
            grade.setAssignment(assignment);
            grade.setGrade(gradeDTO.getGrade());
            gradeService.saveGrade(grade);
            return ResponseEntity.accepted().body("Successfully assigned grade");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning grade: " + e.getMessage());
        }
    }


    @PostMapping("/quizgrades/assign")
    public ResponseEntity<?> addGrade(@RequestBody QuizgradeDTO quizgradeDTO) {
        try {
            Quiz quiz = quizService.findQuizById(quizgradeDTO.getQuizId());
            User student = userService.getUserById(quizgradeDTO.getStudentId());
            Quizgrade quizgrade = new Quizgrade();
            quizgrade.setStudent(student);
            quizgrade.setQuiz(quiz);
            quizgrade.setQuizgrade(quizgradeDTO.getQuizgrade());
            quizgradeService.saveQuizGrade(quizgrade);
            return ResponseEntity.accepted().body("Successfully assigned grade for quiz");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning grade for quiz: " + e.getMessage());
        }
    }

    @PostMapping("/announcement/add")
    public ResponseEntity<?> postAnnouncement( @RequestBody AnnouncementDTO announcementDTO) {
        try {
            Course course = courseService.getCourse(announcementDTO.getCourseId());
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with ID " + announcementDTO.getCourseId() + " not found.");
            }
            Announcement announcement = new Announcement();
            announcement.setTitle(announcementDTO.getTitle());
            announcement.setContent(announcementDTO.getContent());
            announcement.setCourse(course);
            announcement.setPublishedDate(new Date());
            announcementService.saveAnnouncement(announcement);
            return ResponseEntity.accepted().body("Successfully created Announcement");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating announcement: " + e.getMessage());
        }
    }

    @GetMapping("/assignments/{assignmentId}/submissions")
    public ResponseEntity<List<SubmissionDetailDTO>> getSubmissionDetails(@PathVariable Long assignmentId) {
        List<SubmissionDetailDTO> submissionDetails = assignmentSubmissionService.getSubmissionDetailsForAssignment(assignmentId);
        if (submissionDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(submissionDetails);
    }

    @GetMapping("/quiz/{quizId}/submissions")
    public ResponseEntity<List<QuizSubmissionDetailDTO>> getQuizSubmissionDetails(@PathVariable Long quizId) {
        List<QuizSubmissionDetailDTO> quizsubmissionDetails = quizSubmissionService.getSubmissionDetailsForQuiz(quizId);
        if (quizsubmissionDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizsubmissionDetails);
    }

    @GetMapping("course/{courseId}/assignment/{assignmentId}")
    public Assignment getAssignment(
            @PathVariable("assignmentId") Long assignmentId,@PathVariable("courseId") Long courseId) {

        return assignmentService.findAssignmentById(assignmentId);
    }

    @GetMapping("course/{courseId}/quiz/{quizId}")
    public Quiz getQuiz(
            @PathVariable("quizId") Long quizId,@PathVariable("courseId") Long courseId) {

        return quizService.findQuizById(quizId);
    }

    // My Code
    // Manikanta's Restful API'S Implementation


    // POST Add content to Syllabus section
    @PostMapping("/course/{courseId}/syllabus")
    public ResponseEntity<?> addSyllabusContent(@PathVariable Long courseId, @RequestBody SyllabusDTO syllabusDTO) {
        try {
            // Retrieve the course by courseId
            Course course = courseService.getCourse(courseId);
            if (course == null) {
                // If the course is not found, return a 404 Not Found response
                return ResponseEntity.notFound().build();
            }

            // Add syllabus content to the course
            Syllabus syllabus = syllabusService.addSyllabus(courseId, syllabusDTO.getContent());

            // Return a 201 Created response with a success message
            return ResponseEntity.status(HttpStatus.CREATED).body("Syllabus content added successfully for course with ID: " + courseId);
        } catch (Exception e) {
            // If an error occurs, return a 500 Internal Server Error response with an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding syllabus content: " + e.getMessage());
        }
    }


    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCoursesTaughtByFaculty(@RequestParam Long facultyId) {
        List<Course> courses = courseService.getCoursesByFaculty(facultyId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/students")
    public ResponseEntity<List<User>> getStudentsByCourse(@RequestParam Long facultyId, @RequestParam Long courseId) {
        List<User> students = courseService.getStudentsByCourseAndFaculty(facultyId, courseId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/grades")
    public ResponseEntity<List<Grade>> getGradesByCourse(@RequestParam Long facultyId, @RequestParam Long courseId) {
        List<Grade> grades = gradeService.getGradesByFacultyAndCourse(facultyId, courseId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/all-courses")
    public List<CourseDao> getAllCoursesTaughtByFaculty(@RequestParam Long facultyId) {
        // Get all courses taught by the faculty member
        List<Course> allCourses = new ArrayList<>();

        // Iterate over all semesters to retrieve courses taught by the faculty member
        List<String> allSemesters = getAllSemesters();
        for (String semester : allSemesters) {
            allCourses.addAll(courseService.getCoursesByFacultyAndSemester(facultyId, semester));
        }

        // Convert courses to CourseDao and return
        return allCourses.stream().map(course -> new CourseDao(course.getId(), course.getTitle(),
                        course.getSemester(), course.isPublished(), course.getFaculty() == null ? null : course.getFaculty().getEmail()))
                .collect(Collectors.toList());
    }

    private List<String> getAllSemesters() {
        // Define the list of semesters
        List<String> semesters = new ArrayList<>();

        // Add all semesters
        semesters.add("Spring 2024");
        semesters.add("Fall 2023");
        // Add more semesters as needed

        return semesters;
    }

    @GetMapping("/{facultyId}/courses")
    public List<Course> getCurrentCoursesByFaculty(
            @PathVariable Long facultyId,
            @RequestParam String currentSemester
    ) {
        // Get all courses taught by the faculty for the current semester
        List<Course> currentCourses = courseService.getCoursesByFacultyAndSemester(facultyId, currentSemester);

        // Optionally, filter the courses to include both published and unpublished courses
        // This step depends on your application's logic and requirements

        return currentCourses;
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<User>> getStudentsByCourseId(@PathVariable Long courseId) {
        List<User> students = enrollmentService.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/courses/{courseId}/grades")
    public ResponseEntity<List<Grade>> getGradesByCourseId(@PathVariable Long courseId) {
        List<Grade> grades = gradeService.getGradesByCourseId(courseId);
        return ResponseEntity.ok(grades);
    }

}





