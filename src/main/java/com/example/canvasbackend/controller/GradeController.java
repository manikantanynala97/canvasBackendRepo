package com.example.canvasbackend.controller;

import com.example.canvasbackend.dao.GradeDTO;
import com.example.canvasbackend.dao.QuizgradeDTO;
import com.example.canvasbackend.service.GradeService;
import com.example.canvasbackend.service.QuizgradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class GradeController {

    private final GradeService gradeService;
    private final QuizgradeService quizgradeService;

    @GetMapping("/assignments/{assignmentId}")
    public ResponseEntity<GradeDTO> getGradeByAssignment(@PathVariable Long assignmentId, @RequestParam Long studentId) {
        GradeDTO grade = gradeService.getGradeByAssignmentAndStudent(assignmentId, studentId);
        return ResponseEntity.ok(grade);
    }

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<QuizgradeDTO> getQuizGradeByQuiz(@PathVariable Long quizId, @RequestParam Long studentId) {
        QuizgradeDTO quizgrade = quizgradeService.getQuizGradeByQuizAndStudent(quizId, studentId);
        return ResponseEntity.ok(quizgrade);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeDTO>> getGradesByStudent(@PathVariable Long studentId) {
        List<GradeDTO> grades = gradeService.getGradesByStudent(studentId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/quizgrades/student/{studentId}")
    public ResponseEntity<List<QuizgradeDTO>> getQuizGradesByStudent(@PathVariable Long studentId) {
        List<QuizgradeDTO> quizgrades = quizgradeService.getQuizGradesByStudent(studentId);
        return ResponseEntity.ok(quizgrades);
    }
}
