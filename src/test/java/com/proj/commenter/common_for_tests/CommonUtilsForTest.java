package com.proj.commenter.common_for_tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommonUtilsForTest {
    public static void createFile(String path, String codeForFile) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(codeForFile);
        } catch (IOException e) { }
    }

    public static void deleteFile(String path) {
        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }
    }
}
