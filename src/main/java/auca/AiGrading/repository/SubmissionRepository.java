package auca.AiGrading.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.AiGrading.enums.SubmissionStatus;
import auca.AiGrading.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    List<Submission> findByStatusOrderBySubmittedDateAsc(SubmissionStatus status);
    List<Submission> findBySubmittedDateBetweenOrderBySubmittedDateAsc(LocalDate start, LocalDate end);

    boolean existsByExaminerIdAndSubmittedDateAndStatusNot(UUID examinerId, LocalDate submittedDate, SubmissionStatus status);
}
