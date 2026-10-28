package auca.AiGrading.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.AiGrading.model.Examiner;
import auca.AiGrading.repository.ExaminerRepository;

@Service 
public class ExaminerService {

    @Autowired 
    private ExaminerRepository examinerRepo;

    public String save(Examiner examiner) {
        examinerRepo.save(examiner);
        return "Examiner saved successfully";
    }

    public List<Examiner> getAll() {
        return examinerRepo.findAll();
    }

    public Optional<Examiner> getById(UUID id) {
        return examinerRepo.findById(id);
    }

    public String update(UUID id, Examiner examiner) {
        Optional<Examiner> found = examinerRepo.findById(id);
        if (found.isEmpty()) {
            return "Examiner with that id does not exist";
        }
        Examiner existing = found.get();
        existing.setFirstName(examiner.getFirstName());
        existing.setLastName(examiner.getLastName());
        existing.setEmail(examiner.getEmail());
        existing.setCourse(examiner.getCourse());
        examinerRepo.save(existing);
        return "Examiner updated successfully";
    }

    public String delete(UUID id) {
        if (!examinerRepo.existsById(id)) {
            return "Examiner with that id does not exist";
        }
        examinerRepo.deleteById(id);
        return "Examiner deleted successfully";
    }

    public List<Examiner> getByDiscipline(String name) {
        return examinerRepo.findByDisciplineName(name);
    }
}
