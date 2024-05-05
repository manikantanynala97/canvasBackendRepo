package com.example.canvasbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assignment_submissions")
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", referencedColumnName = "id", nullable = false)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private User student; // Assuming there is a User class that represents the student.

    @Lob
    @Column(name = "submission_content", nullable = false)
    private String submissionContent; // This is just a field, not a @ManyToOne relationship.

    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;

}
