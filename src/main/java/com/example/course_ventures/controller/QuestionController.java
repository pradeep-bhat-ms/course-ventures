package com.example.course_ventures.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.course_ventures.entity.Question;
import com.example.course_ventures.response.ResponseStructure;
import com.example.course_ventures.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/save")
    public ResponseEntity<ResponseStructure<Question>> saveQuestion(
            @RequestBody Question question,
            @RequestParam int mockTestId) {

        Question savedQuestion = questionService.saveQuestion(question, mockTestId);

        ResponseStructure<Question> rs = new ResponseStructure<>();
        rs.setStatus(HttpStatus.CREATED.value());
        rs.setMessage("Question Added Successfully");
        rs.setData(savedQuestion);

        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Question>> getQuestionById(
            @PathVariable int id) {

        Question question = questionService.findQuestionById(id);

        ResponseStructure<Question> rs = new ResponseStructure<>();
        rs.setStatus(HttpStatus.OK.value());
        rs.setMessage("Question Found");
        rs.setData(question);

        return ResponseEntity.ok(rs);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteQuestion(
            @PathVariable int id) {

        questionService.deleteQuestion(id);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatus(HttpStatus.OK.value());
        rs.setMessage("Question Deleted Successfully");
        rs.setData("Deleted");

        return ResponseEntity.ok(rs);
    }
}