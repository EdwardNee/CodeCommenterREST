package com.proj.commenter.utils;

import com.proj.commenter.model.CommentError;
import com.proj.commenter.model.ErrorEnum;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Simple class that helps to run python script.
 */
@Component("scriptRunner")
public class ScriptRunner {
    /**
     * String constant to depict handled error while script execution.
     */
    private static final String errorMessage = "Error while running python script %s: %s";

    /**
     * Method runs python script and passes <code>code</code> as input value.
     * @param code Input value for running script.
     * @return Returns result of the execution.
     */
    public CommentError runPythonScript(String code) {
        String path = "C:\\Projects\\IntellijProjects\\CodeCommenterREST\\test.py";
        return runPythonScript(code, path);
    }

    /**
     * Method runs python by a given <code>path</code> script and passes <code>code</code> as input value.
     * @param code Input value for running script.
     * @param path Path to executable python script.
     * @return Returns result of the execution.
     */
    public CommentError runPythonScript(String code, String path) {
        String baseLine = "py";

        File file = new File(path);
        if (!file.exists()) {
            return new CommentError(ErrorEnum.ERROR, String.format(errorMessage, path, "File does not exist."));
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(baseLine, path, code);
            Process process = pb.start();

            StringBuilder outResult = new StringBuilder();
            Scanner scanner = new Scanner(process.getInputStream());

            while (scanner.hasNextLine()) {
                outResult.append(scanner.nextLine());
            }

            int exitCode = process.waitFor();

            scanner.close();
            if (exitCode == 0) {
                return new CommentError(ErrorEnum.OK, outResult.toString());
            } else {
                return new CommentError(ErrorEnum.ERROR, nonZeroCodeProcess(process, path));
            }

        } catch (IOException | InterruptedException e) {

            return new CommentError(ErrorEnum.ERROR, String.format(errorMessage, path, e.getMessage()));
            //String.format(errorMessage, path, e.getMessage());
        }
    }

    /**
     * Method that processes case when <code>Process</code> returned non-zero existing code.
     * @param process Run process.
     * @param path Run process file.
     * @return returns <code>String</code>
     */
    private String nonZeroCodeProcess(Process process, String path) throws IOException {
        try(BufferedReader errorReader = process.errorReader()) {
            String errorLines;
            StringBuilder errorOutput = new StringBuilder();

            while ((errorLines = errorReader.readLine()) != null) {
                errorOutput.append(errorLines).append("\n");
            }
            return String.format(errorMessage, path, errorOutput);
        }
    }
}
