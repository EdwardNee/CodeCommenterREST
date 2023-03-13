package com.proj.commenter.service;

import com.proj.commenter.common_for_tests.CommonUtilsForTest;
import com.proj.commenter.model.Comment;
import com.proj.commenter.model.CommentResponse;
import com.proj.commenter.model.ErrorEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService service;

    @Test
    public void testGenerateCommentOK() {
        String codeForFile = """
                import sys
                def test(code_line: str):
                    return "That is test file: " + code_line
                if __name__ == "__main__":
                    code_line = sys.argv[1]
                    print(test(code_line))
                """;

        CommonUtilsForTest.createFile("script\\test.py", codeForFile);

        CommentResponse result = service.generateComment(new Comment("int a = 5;", null));

        assertEquals("That is test file: int a = 5;", result.generatedComment());
        assertNull(result.error());

        CommonUtilsForTest.deleteFile("test.py");
    }

    @Test
    public void testGenerateCommentError() throws IOException {
        String initialCode = """
                import sys

                def test(code_line: str):
                    return "That is test file: " + code_line


                if __name__ == "__main__":
                    code_line = sys.argv[1]
                    print(test(code_line))
                """;
        CommonUtilsForTest.deleteFile("script\\test.py");

        CommentResponse result = service.generateComment(new Comment("int a = 5;", null));

        CommonUtilsForTest.createFile("script\\test.py", initialCode);
        assertNull(result.generatedComment());
        assertNotNull(result.error());

        String expected = String.format("Error while running python script %s: File does not exist.",
                /* new File(".").getCanonicalPath() +*/ "script\\test.py");

        assertEquals(expected, result.error().errorMessage());
        assertEquals(ErrorEnum.ERROR, result.error().errorEnum());
    }
}


