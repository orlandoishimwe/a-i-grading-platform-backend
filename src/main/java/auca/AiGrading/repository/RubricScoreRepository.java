package auca.AiGrading.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.AiGrading.model.RubricScore;
import auca.AiGrading.model.Submission;

public interface RubricScoreRepository extends JpaRepository<RubricScore, UUID> {
    @Query("SELECT d FROM RubricScore d WHERE d.submission.student.studentNumber = " +
        ":studentNumber ORDER BY d.gradedDate DESC")
    List<RubricScore> findByStudentNumber(@Param ("studentNumber") String studentNumber);

    boolean existsBySubmission(Submission submission);
    
}
