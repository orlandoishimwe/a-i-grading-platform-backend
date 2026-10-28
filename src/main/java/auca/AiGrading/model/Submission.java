package auca.AiGrading.model;

import java.time.LocalDate;
import java.util.UUID;

import auca.AiGrading.enums.SubmissionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "submission")
public class Submission {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate submittedDate;

    @Enumerated (EnumType.STRING)
    private SubmissionStatus status;

    private String title;
    private String fileName;

    @ManyToOne 
    @JoinColumn (name = "student_id")
    private Student student;

    @ManyToOne 
    @JoinColumn (name = "examiner_id")
    private Examiner examiner;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDate submittedDate) {
        this.submittedDate = submittedDate;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Examiner getExaminer() {
        return examiner;
    }

    public void setExaminer(Examiner examiner) {
        this.examiner = examiner;
    }

    
}
