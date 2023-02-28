package com.proj.commenter.controller;

import com.proj.commenter.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentControllerTest {
    @Autowired
    private CommentController controller;

    /**
     * Tests controller <code>{@link CommentController}</code> is injected.
     */
    @Test
    public void controllerLoads() {
        assertNotNull(controller);
    }

    /**
     * Tests service <code>{@link CommentService}</code> in the <code>{@link CommentController}</code> is injected.
     */
    @Test
    public void serviceLoads() {
        assertNotNull(controller.getService());
    }
}