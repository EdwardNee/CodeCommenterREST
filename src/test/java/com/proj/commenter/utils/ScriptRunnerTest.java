package com.proj.commenter.utils;

import com.proj.commenter.common_for_tests.CommonUtilsForTest;
import com.proj.commenter.model.CommentError;
import com.proj.commenter.model.CommentResponse;
import com.proj.commenter.model.ErrorEnum;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScriptRunnerTest {

    private final String basicCode = "basic code";
    private final String path = "test.py";
    private final ScriptRunner scriptRunner = new ScriptRunner();

    @Test
    public void testBasicScriptRunner() {
        String codeForFile = """
                import sys
                def test(code_line: str):
                    return "That is test file: " + code_line
                if __name__ == "__main__":
                    code_line = sys.argv[1]
                    print(test(code_line))
                """;
        CommonUtilsForTest.createFile(path, codeForFile);

        CommentError result = scriptRunner.runPythonScript(basicCode);

        assertEquals("That is test file: basic code", result.errorMessage());
        assertEquals(ErrorEnum.OK, result.errorEnum());

        CommonUtilsForTest.deleteFile(path);
    }


    @Test
    public void testRunWithNonExistedFile() {
        CommentError result = scriptRunner.runPythonScript(basicCode, path);

        assertEquals("Error while running python script test.py: File does not exist.", result.errorMessage());
        assertEquals(ErrorEnum.ERROR, result.errorEnum());

        CommonUtilsForTest.deleteFile(path);
    }

    @Test
    public void testExistedWithNonZeroCode() {
        String codeForFile = """
                def test(code_line: str):
                    return "That is test file: " + code_line
                if __name__ == "__main__":
                    code_line = sys.argv[1]
                    print(test(code_line))
                """;

        CommonUtilsForTest.createFile(path, codeForFile);

        String absolutePath = new File(path).getAbsolutePath();
        CommentError result = scriptRunner.runPythonScript(basicCode, absolutePath);

        String expected = """
                Error while running python script %s: Traceback (most recent call last):
                  File "%s", line 4, in <module>
                    code_line = sys.argv[1]
                NameError: name 'sys' is not defined
                """;
        assertEquals(String.format(expected, absolutePath, absolutePath), result.errorMessage());
        assertEquals(ErrorEnum.ERROR, result.errorEnum());

        CommonUtilsForTest.deleteFile(path);
    }
}
