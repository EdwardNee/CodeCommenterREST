package com.proj.commenter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getDataObject(String api) {
        return this.restTemplate.getForObject("http://localhost:" + port + api, String.class);
    }

    @Test
    public void greetingShouldReturnDefaultMessage() {
        String data = getDataObject("/");
        assertTrue(data.contains("Go to /api/v1."));
    }

    @Test
    public void greetingShouldReturnDefaultMessageOnApiV1() {
        String data = getDataObject("/api/v1");
        assertTrue(data.contains("Hello from CodeCommenter!"));
    }
}
