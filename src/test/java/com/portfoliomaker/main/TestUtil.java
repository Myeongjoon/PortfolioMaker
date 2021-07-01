package com.portfoliomaker.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUtil {
    public static String getHtmlByString(String fileName) {

        Path path = Paths.get("src/test/resources/" + fileName);
        String htmlStr = "";

        try {
            htmlStr = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            fail();
        }
        return htmlStr;
    }
}
