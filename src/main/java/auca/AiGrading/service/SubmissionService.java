package auca.AiGrading.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.AiGrading.enums.SubmissionStatus;
import auca.AiGrading.model.Submission;
import auca.AiGrading.repository.RubricScoreRepository;
import auca.AiGrading.repository.SubmissionRepository;

@Service 
public class SubmissionService {

    @Autowired 
    private SubmissionRepository submissionRepo;

    private RubricScoreRepository rubricScoreRepo;

    public String save(Submission submission) {
        if (submission.getSubmittedDate().isBefore(LocalDate.now())) {
            return "Submission date cannot be in the past";
        }
        if (submission.getExaminer() != null
                && submissionRepo.existsByExaminerIdAndSubmittedDateAndStatusNot(submission.getExaminer().getId(),
                submission.getSubmittedDate(), SubmissionStatus.REJECTED)) {
            return "Examiner already has a submission to review on that date";
        }
        submission.setStatus(SubmissionStatus.UPLOADED);
        submissionRepo.save(submission);
        return "Submission saved successfully";
    }

    public List<Submission> getAll() {
        return submissionRepo.findAll();
    }

    public Optional<Submission> getById(UUID id) {
        return submissionRepo.findById(id);
    }

    public String update(UUID id, Submission submission) {
        Optional<Submission> found = submissionRepo.findById(id);
        if (found.isEmpty()) {
            return "Submission with that id does not exist";
        }

        Submission existing = found.get();
        existing.setFileName(submission.getFileName());
        existing.setSubmittedDate(submission.getSubmittedDate());
        existing.setTitle(submission.getTitle());
        existing.setStudent(submission.getStudent());
        existing.setExaminer(submission.getExaminer());
        submissionRepo.save(existing);
        return "Submission updated successfully";
    }

    public String updateStatus(UUID id, SubmissionStatus newStatus) {
        Optional<Submission> found = submissionRepo.findById(id);
        if (found.isEmpty()) {
            return "The submission with that id does not exist";
        }
        Submission submission = found.get();
        SubmissionStatus current = submission.getStatus();

        boolean allowed = false;
        if (current == SubmissionStatus.UPLOADED
                && (newStatus == SubmissionStatus.AI_GRADED || newStatus == SubmissionStatus.REJECTED))
        {
            allowed = true;
        } else if (current == SubmissionStatus.AI_GRADED &&
            (newStatus == SubmissionStatus.REVIEWED || newStatus == SubmissionStatus.REJECTED)
        ) {
            allowed = true;
        }

        if (!allowed) {
            return "Cannot change the status from " + current + " to " + newStatus;
        }
        submission.setStatus(newStatus);
        submissionRepo.save(submission);
        return "Submission status updated successfully";
    }

    public String delete(UUID id) {
        Optional<Submission> found = submissionRepo.findById(id);
        if (found.isEmpty()) {
            return "The submission with that id does not exist";
        } if (rubricScoreRepo.existsBySubmission(found.get())) {
            return "That submission has rubric scores, delete them first";
        }
        submissionRepo.deleteById(id);
        return "Submission deleted successfully";
    }

    public List<Submission> getByStatus(SubmissionStatus status) {
        return submissionRepo.findByStatusOrderBySubmittedDateAsc(status);
    }

    public List<Submission> getBetween(LocalDate start, LocalDate end) {
        return submissionRepo.findBySubmittedDateBetweenOrderBySubmittedDateAsc(start, end);
    }
}
