package auca.AiGrading.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auca.AiGrading.model.Course;
import auca.AiGrading.model.Discipline;
import auca.AiGrading.model.Examiner;
import auca.AiGrading.repository.CourseRepository;
import auca.AiGrading.repository.DisciplineRepository;
import auca.AiGrading.repository.ExaminerRepository;

@Service 
public class ExaminerService {

    @Autowired 
    private ExaminerRepository examinerRepo;

    @Autowired 
    private DisciplineRepository disciplineRepo;

    @Autowired 
    private CourseRepository courseRepo;

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

    @Transactional 
    public String addDiscipline(UUID examinerId, UUID disciplineId) {
        Optional<Examiner> foundExaminer = examinerRepo.findById(disciplineId);
        if (foundExaminer.isEmpty()) {
            return "The examiner with that id does not exist";
        }
        Optional<Discipline> foundDiscipline = disciplineRepo.findById(disciplineId);
        if (foundDiscipline.isEmpty()) {
            return "The discipline with that id does not exist";
        }
        Examiner examiner = foundExaminer.get();
        Discipline discipline = foundDiscipline.get();

        boolean alreadyHas = examiner.getDisciplines().stream()
                .anyMatch(d -> d.getId().equals(discipline.getId()));
        if (alreadyHas) {
            return "The examiner already has that discipline";
        }
        examiner.getDisciplines().add(discipline);
        examinerRepo.save(examiner);
        return "Discipline successfully added to the examiner";
    }

    public  String changeCourse(UUID examinerId, int courseNumber) {
        Optional<Examiner> foundExaminer = examinerRepo.findById(examinerId);
        if (foundExaminer.isEmpty()) {
            return "The examiner with that id does not exist";
        }
        Optional<Course> foundCourse = courseRepo.findByCourseNumber(courseNumber);
        if (foundCourse.isEmpty()) {
            return "The course with that id does not exist";
        }
        Examiner examiner = foundExaminer.get();
        Course course = foundCourse.get();

        boolean isOwnCourse = examiner.getCourse() != null
                && examiner.getCourse().getId().equals(course.getId());
        if (isOwnCourse && examinerRepo.existsByCourse(course)) {
            return "That course is taken by another examiner";
        }
        examiner.setCourse(course);
        examinerRepo.save(examiner);
        return "Course changed successfully";
    }
}
