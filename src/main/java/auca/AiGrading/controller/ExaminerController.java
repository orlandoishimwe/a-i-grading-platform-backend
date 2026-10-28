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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.AiGrading.model.Examiner;
import auca.AiGrading.service.ExaminerService;

@RestController 
@RequestMapping ("/api/examiners")
public class ExaminerController {
    
    @Autowired 
    private ExaminerService examinerServ;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Examiner examiner) {
        return new ResponseEntity<>(examinerServ.save(examiner), HttpStatus.CREATED);
    }

    @GetMapping ("/all")
    public List<Examiner> getAll() {
        return examinerServ.getAll();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Examiner> found = examinerServ.getById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>("The examiner with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(examinerServ.getById(id), HttpStatus.OK);
        }
    }

    @GetMapping ("/by-discipline")
    public List<Examiner> getByExaminer(@RequestParam String name) {
        return examinerServ.getByDiscipline(name);
    }

    @PostMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Examiner examiner) {
        String message = examinerServ.update(id, examiner);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        Optional<Examiner> found = examinerServ.getById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(examinerServ.delete(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The examiner with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
