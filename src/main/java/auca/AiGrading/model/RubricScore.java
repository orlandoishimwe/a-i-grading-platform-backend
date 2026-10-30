package auca.AiGrading.model;

import java.util.UUID;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "rubric_score")
public class RubricScore {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private String criterion;
    private String aiFeedback;
    private int score;
    private LocalDate gradedDate;

    @ManyToOne 
    @JoinColumn (name = "submission_id")
    private Submission submission;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public String getAiFeedback() {
        return aiFeedback;
    }

    public void setAiFeedback(String aiFeedback) {
        this.aiFeedback = aiFeedback;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getGradedDate() {
        return gradedDate;
    }

    public void setGradedDate(LocalDate gradedDate) {
        this.gradedDate = gradedDate;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    
}
