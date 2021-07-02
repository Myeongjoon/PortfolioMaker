package com.portfoliomaker.main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUtil {
    public static String getHtmlByString(String fileName) {

        Path path = Paths.get("src/test/resources/" + fileName);
        StringBuilder htmlStr = new StringBuilder();

        try {
            List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String s : list) {
                htmlStr.append(s).append("\n");
            }
        } catch (IOException e) {
            fail();
        }
        return htmlStr.toString();
    }
}
