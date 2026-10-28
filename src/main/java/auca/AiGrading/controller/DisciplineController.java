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
import org.springframework.web.bind.annotation.RestController;

import auca.AiGrading.model.Discipline;
import auca.AiGrading.service.DisciplineService;

@RestController
@RequestMapping ("/api/disciplines")
public class DisciplineController {

    @Autowired 
    private DisciplineService disciplineServ;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Discipline discipline) {
        return new ResponseEntity<>(disciplineServ.save(discipline), HttpStatus.CREATED);
    }

    @GetMapping ("/all")
    public List<Discipline> getAll() {
        return disciplineServ.getAll();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Discipline> found = disciplineServ.getById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(disciplineServ.getById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The discipline with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping ("/unused")
    public List<Discipline> getUnusedDisciplines() {
        return disciplineServ.getUnusedDisciplines();
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Discipline discipline) {
        String message = disciplineServ.update(id, discipline);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        Optional<Discipline> found = disciplineServ.getById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>("Discipline with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(disciplineServ.delete(id), HttpStatus.OK);
        }
    }
}
