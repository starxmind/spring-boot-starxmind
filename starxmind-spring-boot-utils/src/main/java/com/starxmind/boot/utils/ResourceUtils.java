package com.starxmind.boot.utils;

import com.starxmind.bass.sugar.Asserts;
import com.starxmind.bass.sugar.ClassLoaderUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public abstract class ResourceUtils {
    public static String read(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = ClassLoaderUtils.getResourceStream(ResourceUtils.class, fileName)) {
            Asserts.notNull(inputStream, "No input stream for file " + fileName);
            try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            log.error("Error to read the resource file!", e);
            throw e;
        }
        return content.toString();
    }
}
