package auca.AiGrading.controller;

import java.util.List;
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

import auca.AiGrading.model.RubricScore;
import auca.AiGrading.service.RubricScoreService;

@RestController 
@RequestMapping("/api/rubric-scores")
public class RubricScoreController {

    @Autowired 
    private RubricScoreService rubricScoreServ;

    @PostMapping ("/save")
    public ResponseEntity<String> save(@PathVariable UUID submissionId, @RequestBody RubricScore rubricScore) {
        String message = rubricScoreServ.save(submissionId, rubricScore);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.contains("Rubric scores are only for REVIEWED submissions")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }

    @GetMapping ("/student/{studentNumber}")
    public List<RubricScore> getByStudentNumber(@RequestParam String studentNumber) {
        return rubricScoreServ.getByStudentNumber(studentNumber);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = rubricScoreServ.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
