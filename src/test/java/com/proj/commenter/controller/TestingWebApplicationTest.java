package com.proj.commenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proj.commenter.model.CommentResponse;
import com.proj.commenter.model.Comment;
import com.proj.commenter.service.CommentService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(CommentController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestingWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService service;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static Comment comment;
    private static CommentResponse commentResponse;

    @BeforeAll
    public static void init() {
        comment = new Comment("code", "first_part");
        commentResponse = new CommentResponse(comment.getCode() + "response");
    }

    @Test
    public void testInitialEntry() throws Exception {
        when(service.initialEntry()).thenReturn("Hello from CodeCommenter!");
        ResultActions response = mockMvc.perform(get("/api/v1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Hello from CodeCommenter!")));
    }

    @Test
    public void testGenerateCommentEntry() throws Exception {
        String json = objectMapper.writeValueAsString(comment);

        given(service.generateComment(comment)).willReturn(commentResponse);
        ResultActions response = this.mockMvc.perform(
                        post("/api/v1/generate_comment")
                                .contentType(MediaType.APPLICATION_JSON).content(json));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.generatedComment", CoreMatchers.is(commentResponse.generatedComment())));
    }

    @Test
    public void testCompleteCommentEntry() throws Exception {
        String json = objectMapper.writeValueAsString(comment);

        given(service.completeComment(comment)).willReturn("That is result");
        ResultActions response = this.mockMvc.perform(
                        post("/api/v1/complete_comment")
                                .contentType(MediaType.APPLICATION_JSON).content(json));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(containsString("That is result")));
    }
}
