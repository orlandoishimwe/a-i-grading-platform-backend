package auca.AiGrading.controller;

import java.time.LocalDate;
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

import auca.AiGrading.enums.SubmissionStatus;
import auca.AiGrading.model.Submission;
import auca.AiGrading.service.SubmissionService;

@RestController 
@RequestMapping ("/api/submissions")
public class SubmissionController {

    @Autowired 
    private SubmissionService submissionServ;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@RequestBody Submission submission) {
        String message = submissionServ.save(submission);
        if (message.equals("Submission cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else if (message.equals("Examiner already has a submission to review on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }

    @GetMapping ("/all")
    public List<Submission> getAll() {
        return submissionServ.getAll();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Submission> found = submissionServ.getById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(submissionServ.getById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The submission with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping ("/by-status")
    public List<Submission> getByStatus(@RequestParam SubmissionStatus status) {
        return submissionServ.getByStatus(status);
    }

    @GetMapping ("/between")
    public List<Submission> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return submissionServ.getBetween(startDate, endDate);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Submission submission) {
        String message = submissionServ.update(id, submission);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PutMapping ("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam SubmissionStatus status) {
        String message = submissionServ.updateStatus(id, status);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.startsWith("Cannot change status")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = submissionServ.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.contains("delete them first")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
