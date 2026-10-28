package auca.AiGrading.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.AiGrading.model.Student;
import auca.AiGrading.repository.StudentRepository;

@Service 
public class StudentService {

    @Autowired 
    private StudentRepository studentRepo;

    public String save(Student student) {
        studentRepo.save(student);
        return "Student saved successfully";
    }

    public List<Student> getAll() {
        return studentRepo.findAll();
    }

    public Optional<Student> getById(UUID id) {
        return studentRepo.findById(id);
    }

    public String update(UUID id, Student student) {
        Optional<Student> found = studentRepo.findById(id);
        if (found.isEmpty()) {
            return "Student with that id does not exist";
        }

        Student existing = found.get();
        existing.setFirstName(student.getFirstName());
        existing.setLastName(student.getLastName());
        existing.setEmail(student.getEmail());
        existing.setStudentNumber(student.getStudentNumber());
        studentRepo.save(existing);
        return "Student updated successfully";
    }

    public String delete(UUID id) {
        if (!studentRepo.existsById(id)) {
            return "Student with that id does not exist";
        }
        studentRepo.deleteById(id);
        return "Student deleted successfully";
    }

    public List<Student> getByLastName(String lastName) {
        return studentRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public List<Student> getByExaminer(UUID examinerId) {
        return studentRepo.findStudentsByExaminerId(examinerId);
    }
}
