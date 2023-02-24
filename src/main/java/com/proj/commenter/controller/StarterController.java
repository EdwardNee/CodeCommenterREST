package com.proj.commenter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class StarterController {
    @GetMapping
    public String initialPage() {
        return "Go to /api/v1.";
    }
}
