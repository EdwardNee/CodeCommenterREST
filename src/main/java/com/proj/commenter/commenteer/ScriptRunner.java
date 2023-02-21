package com.proj.commenter.commenteer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Simple class that helps to run python script.
 */
class ScriptRunner {
    /**
     * String constant to depict handled error while script execution.
     */
    private static final String errorMessage = "Error while running python script %s: %s";

    /**
     * Method runs python script and passes <code>code</code> as input value.
     * @param code Input value for running script.
     * @return Returns result of the execution.
     */
    String runPythonScript(String code) {
        String path = "C:\\Projects\\IntellijProjects\\CodeCommenterREST\\test.py";
        String baseLine = "py";

        File file = new File(path);
        if (!file.exists()) {
            return String.format(errorMessage, path, "File does not exists.");
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
                return outResult.toString();
            } else {
                return nonZeroCodeProcess(process, path);
            }

        } catch (IOException | InterruptedException e) {
            return String.format(errorMessage, path, e.getMessage());
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
