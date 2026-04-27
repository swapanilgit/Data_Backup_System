package com.backup.utils;

import java.io.*;
import java.util.Properties;

public class ConfigManager {

    public static Properties loadConfig(String path) throws IOException {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            prop.load(fis);
        }
        return prop;
    }
}