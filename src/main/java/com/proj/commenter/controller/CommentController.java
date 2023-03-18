package com.proj.commenter.controller;

import com.proj.commenter.model.CommentResponse;
import com.proj.commenter.model.Comment;
import com.proj.commenter.model.ErrorEnum;
import com.proj.commenter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
public class CommentController {
    private final CommentService service;

    /**
     * Getter for service.
     * @return <code>{@link CommentService}</code>
     */
    public CommentService getService() {
        return service;
    }

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    /**
     * Hello page.
     * @return String with hello.
     */
    @GetMapping
    public String initialEntry() {
        return service.initialEntry();
    }

    @PostMapping("/generate_comment")
    public ResponseEntity<CommentResponse> generateComment(@RequestBody Comment code) {
        CommentResponse result = service.generateComment(code);

        HttpStatus status =
                result.error() == null || result.error().errorEnum() == ErrorEnum.OK
                ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(result, status);
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
