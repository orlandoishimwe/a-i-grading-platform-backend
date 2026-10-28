package auca.AiGrading.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.AiGrading.repository.DisciplineRepository;
import auca.AiGrading.model.Discipline;

@Service 
public class DisciplineService {

    @Autowired 
    private DisciplineRepository disciplineRepo;

    public String save(Discipline discipline) {
        disciplineRepo.save(discipline);
        return "Discipline saved successfully";
    }

    public List<Discipline> getAll() {
        return disciplineRepo.findAll();
    }

    public Optional<Discipline> getById(UUID id) {
        return disciplineRepo.findById(id);
    }

    public String update(UUID id, Discipline discipline) {
        Optional<Discipline> found = disciplineRepo.findById(id);
        if (found.isEmpty()) {
            return "Discipline with that id does not exist";
        }
        Discipline existing = found.get();
        existing.setName(discipline.getName());
        disciplineRepo.save(existing);
        return "Discipline updated successfully";
    }

    public String delete(UUID id) {
        if (!disciplineRepo.existsById(id)) {
            return "Discipline with that id does not exist";
        }
        disciplineRepo.deleteById(id);
        return "Discipline deleted successfully";
    }

    public List<Discipline> getUnusedDisciplines() {
        return disciplineRepo.findUnusedDisciplines();
    }
}
