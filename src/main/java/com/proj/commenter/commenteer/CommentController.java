package com.proj.commenter.commenteer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
public class CommentController {
    @PostMapping("/generate_comment")
    public String generateComment(@RequestBody Comment code) {
        return code.getCode();
    }

    @PostMapping("/complete_comment")
    public String completeComment(@RequestBody Comment code) {
        return code.getFirstPart() + " my second part";
    }

    @PutMapping("/save_to_dataset")
    public void saveToDataset(@RequestBody Comment code) {

    }
}
