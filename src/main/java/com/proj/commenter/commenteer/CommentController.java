package com.proj.commenter.commenteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
public class CommentController {
    private final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/generate_comment")
    public ResponseEntity<String> generateComment(@RequestBody Comment code) {
        return new ResponseEntity<>(service.generateComment(code), HttpStatus.OK);
    }

    @PostMapping("/complete_comment")
    public String completeComment(@RequestBody Comment code) {
        return service.completeComment(code);
    }

    @PutMapping("/save_to_dataset")
    public void saveToDataset(@RequestBody Comment code) {
        service.saveToDataset(code);
    }
}
