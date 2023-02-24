package com.proj.commenter.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScriptRunnerTest {

    private final String basicCode = "basic code";
    private final String path = "test.py";
    private final ScriptRunner scriptRunner = new ScriptRunner();

    private void createFile(String path, String codeForFile) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(codeForFile);
        } catch (IOException e) { }
    }

    private void deleteFile() {
        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }
    }

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
        createFile(path, codeForFile);

        String result = scriptRunner.runPythonScript(basicCode);

        assertEquals("That is test file: basic code", result);

        deleteFile();
    }


    @Test
    public void testRunWithNonExistedFile() {
        String result = scriptRunner.runPythonScript(basicCode, path);

        assertEquals("Error while running python script test.py: File does not exists.", result);
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

        createFile(path, codeForFile);

        String absolutePath = new File(path).getAbsolutePath();
        String result = scriptRunner.runPythonScript(basicCode, absolutePath);

        String expected = """
                Error while running python script %s: Traceback (most recent call last):
                  File "%s", line 4, in <module>
                    code_line = sys.argv[1]
                NameError: name 'sys' is not defined
                """;
        assertEquals(result, String.format(expected, absolutePath, absolutePath));

        deleteFile();
    }
}
