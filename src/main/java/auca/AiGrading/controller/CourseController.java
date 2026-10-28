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

import auca.AiGrading.model.Course;
import auca.AiGrading.service.CourseService;

@RestController 
@RequestMapping ("/api/courses")
public class CourseController {

    @Autowired 
    private CourseService courseServ;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Course course) {
        return new ResponseEntity<>(courseServ.save(course), HttpStatus.CREATED);
    }

    @GetMapping ("/all")
    public List<Course> getAll() {
        return courseServ.getAll();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Course> found = courseServ.getById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>("The course with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return  new ResponseEntity<>(courseServ.getById(id), HttpStatus.OK);
        }
    }

    @GetMapping ("/free")
    public List<Course> getFreeCourses() {
        return courseServ.getFreeCourses();
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Course course) {
        Optional<Course> found = courseServ.getById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(courseServ.update(id, course), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The course with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = courseServ.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
