package sma.util;

import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import sma.interfaces.IConfig;

public class ConfigReader {

    private final ObjectMapper mapper;

    public ConfigReader() {
        this.mapper = new ObjectMapper();
    }

    public IConfig readConfig(String fileName) throws Exception {
        try {

            return mapper.readValue(new String(Files.readAllBytes(Path.of(fileName))), IConfig.class);

        } catch (Exception e) {
            throw e;
        }
    }
}
