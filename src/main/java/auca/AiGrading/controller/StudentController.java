package auca.AiGrading.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.AiGrading.model.Student;
import auca.AiGrading.service.ExaminerService;
import auca.AiGrading.service.StudentService;

@RestController 
@RequestMapping ("/api/students")
public class StudentController {

    @Autowired 
    private StudentService studentServ;

    @Autowired 
    private ExaminerService examinerServ;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Student student) {
        return new ResponseEntity<>(studentServ.save(student), HttpStatus.CREATED);
    }

    @GetMapping ("/all")
    public List<Student> getAll() {
        return studentServ.getAll();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Student> found = studentServ.getById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(studentServ.getById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The student with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping ("/by-last-name")
    public List<Student> getByLastName(@RequestParam String lastName) {
        return studentServ.getByLastName(lastName);
    }

    @GetMapping ("/of-examiner/{examinerId}")
    public ResponseEntity<Object> getByExaminer(@PathVariable UUID examinerId) {
        if (examinerServ.getById(examinerId).isEmpty()) {
            return new ResponseEntity<>("Examiner with that Id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentServ.getByExaminer(examinerId), HttpStatus.OK);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Student student) {
        String message = studentServ.update(id, student);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        Optional<Student> found = studentServ.getById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>("The student with that id does not exist", HttpStatus.NOT_FOUND); 
        } else {
            return new ResponseEntity<>(studentServ.delete(id), HttpStatus.OK);
        }
    }
}
