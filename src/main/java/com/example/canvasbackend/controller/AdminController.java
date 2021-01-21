package com.example.canvasbackend.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import com.example.canvasbackend.entity.*;
import com.example.canvasbackend.service.EnrollmentService;
import com.example.canvasbackend.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.canvasbackend.dao.CourseDao;
import com.example.canvasbackend.service.CourseService;
import com.example.canvasbackend.service.UserService;
import com.example.canvasbackend.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @GetMapping("/faculties")
    public ResponseEntity<List<User>> getAllFaculties() {
        List<User> faculties = userService.findAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/courses/{semester}")
    public List<CourseDao> findCoursesBySem(@PathVariable("semester") String semester) {
        List<Course> courses = courseService.getCoursesBySem(semester);
        return courses.stream().map((course) -> {
            return new CourseDao(course.getId(),course.getTitle(),
                    course.getSemester(),course.isPublished(),course.getFaculty()==null?null:course.getFaculty().getEmail());
        }).collect(Collectors.toList());
    }

    @GetMapping("/student/{semester}/{courseId}")
    public List<String> findStudentsBySemAndCourse(@PathVariable("semester") String semester,
                                            @PathVariable("courseId") Long courseId ) throws UnsupportedEncodingException {
        String sem = URLDecoder.decode(semester, StandardCharsets.UTF_8);
        return enrollmentService.getStudentsByCourseIdAndSemester(courseId,sem);
    }



    @DeleteMapping(path = "/faculties/batch", consumes = "application/json")
    public ResponseEntity<?> deleteFaculties(@RequestBody List<Long> ids) {
        userService.deleteFacultiesByIds(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/course/add")
    public ResponseEntity<?> addCourseForFaulty(@RequestBody CourseDao courseDao) {
        Course course = courseService.getCourse(courseDao.getId());
        User faculty = userService.getUserByEmail(courseDao.getFaculty());
        if (faculty.getRole().equals(Role.FACULTY)) {
            course.setFaculty(faculty);
            courseService.saveCourse(course);
        }
        return ResponseEntity.accepted().body("Successfully assigned course to faculty");
    }

    // Changes made by Venkata Surya Manikanta Nynala

    @GetMapping("/courses/faculty/{facultyId}/semester/{semester}")
    public List<CourseDao> getCoursesByFacultyAndSemester(@PathVariable("facultyId") Long facultyId,
                                                          @PathVariable("semester") String semester) {
        // Decode the semester parameter if needed
        String decodedSemester = URLDecoder.decode(semester, StandardCharsets.UTF_8);

        // Retrieve courses by faculty and semester
        List<Course> courses = courseService.getCoursesByFacultyAndSemester(facultyId, decodedSemester);

        // Map Course entities to CourseDao objects
        return courses.stream()
                .map(course -> new CourseDao(course.getId(), course.getTitle(), course.getSemester(),
                        course.isPublished(), course.getFaculty() == null ? null : course.getFaculty().getEmail()))
                .collect(Collectors.toList());
    }

    @PostMapping("/course/assign")
    public ResponseEntity<?> assignCourseToFaculty(@RequestParam("facultyId") Long facultyId,
                                                   @RequestParam("courseId") Long courseId,
                                                   @RequestParam("semester") String semester) {
        User faculty = userService.getUserById(facultyId);
        Course course = courseService.getCourse(courseId);
        if (faculty != null && course != null) {
            // Assign the course to the faculty for the new semester
            course.setFaculty(faculty);
            course.setSemester(semester); // Update the semester
            courseService.saveCourse(course);
            return ResponseEntity.accepted().body("Course assigned to faculty for the new semester");
        } else {
            // Handle the case where the faculty or course is not found
            return ResponseEntity.badRequest().body("Faculty or course not found");
        }
    }

    @GetMapping("/course/{courseId}/students")
    public ResponseEntity<List<User>> getStudentsByCourse(@PathVariable("courseId") Long courseId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            List<User> students = enrollmentService.getStudentsByCourseId(courseId);
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
