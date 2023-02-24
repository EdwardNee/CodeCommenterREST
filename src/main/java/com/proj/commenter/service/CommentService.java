package com.proj.commenter.service;

import com.proj.commenter.model.CommentResponse;
import com.proj.commenter.model.Comment;
import com.proj.commenter.utils.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private ScriptRunner runner;

    public CommentResponse generateComment(Comment code) {
        return new CommentResponse(code.getCode() + runner.runPythonScript(code.getCode()));
    }

    public String completeComment(Comment code) {
        return code.getFirstPart() + "[C O M P L E T I O N]";
    }

    public void saveToDataset(Comment code) {

    }

    public String initialEntry() {
        return "Hello from CodeCommenter!";
    }
}
