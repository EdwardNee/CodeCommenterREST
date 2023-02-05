package com.proj.commenter.commenteer;

import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public String generateComment(Comment code) {
        return code.getCode();
    }

    public String completeComment(Comment code) {
        return code.getFirstPart() + "[C O M P L E T I O N]";
    }

    public void saveToDataset(Comment code) {

    }
}
