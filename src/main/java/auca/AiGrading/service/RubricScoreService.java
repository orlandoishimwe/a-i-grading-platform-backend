package auca.AiGrading.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.AiGrading.enums.SubmissionStatus;
import auca.AiGrading.model.RubricScore;
import auca.AiGrading.model.Submission;
import auca.AiGrading.repository.RubricScoreRepository;
import auca.AiGrading.repository.SubmissionRepository;

@Service 
public class RubricScoreService {
    @Autowired 
    private RubricScoreRepository rubricScoreRepo;

    @Autowired 
    private SubmissionRepository submissionRepo;

    public String save(UUID submissionId, RubricScore rubricScore) {
        Optional<Submission> found = submissionRepo.findById(submissionId);
        if (found.isEmpty()) {
            return "The submission with that id does not exist";
        }
        Submission submission = found.get();
        if (submission.getStatus() != SubmissionStatus.REVIEWED) {
            return "Rubric scores are only for REVIEWED submissions";
        }
        rubricScore.setSubmission(submission);
        rubricScore.setGradedDate(LocalDate.now());
        rubricScoreRepo.save(rubricScore);
        return "Rubric score saved successfully";
    }

    public List<RubricScore> getByStudentNumber(String studentNumber) {
        return rubricScoreRepo.findByStudentNumber(studentNumber);
    }

    public String delete(UUID id) {
        if (!rubricScoreRepo.existsById(id)) {
            return "The rubric score with that id does not exist";
        }
        rubricScoreRepo.deleteById(id);
        return "The rubric score deleted successfully";
    }
}
