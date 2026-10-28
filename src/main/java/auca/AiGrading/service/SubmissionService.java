package auca.AiGrading.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.AiGrading.enums.SubmissionStatus;
import auca.AiGrading.model.Submission;
import auca.AiGrading.repository.SubmissionRepository;

@Service 
public class SubmissionService {

    @Autowired 
    private SubmissionRepository submissionRepo;

    public String save(Submission submission) {
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
        existing.setStatus(submission.getStatus());
        existing.setSubmittedDate(submission.getSubmittedDate());
        existing.setTitle(submission.getTitle());
        existing.setStudent(submission.getStudent());
        existing.setExaminer(submission.getExaminer());
        submissionRepo.save(existing);
        return "Submission updated successfully";
    }

    public String delete(UUID id) {
        if (!submissionRepo.existsById(id)) {
            return "Submission with that id does not exist";
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
