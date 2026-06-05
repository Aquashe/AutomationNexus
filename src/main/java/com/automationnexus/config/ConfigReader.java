package com.automationnexus.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;


    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            // Finds your project root automatically
            String projectRoot = System.getProperty("user.dir");
            String configPath = projectRoot
                    + "/src/main/resources/config/config.properties";

            FileInputStream fis = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(fis);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties file not found!");
        }
    }

    // ✅ Gets any value from properties file using KEY
    public static String get(String key) {
        return properties.getProperty(key);
    }

    // ✅ Gets the full app path automatically
    public static String getAppPath() {
        String projectRoot = System.getProperty("user.dir");
        String appRelativePath = properties.getProperty("app.path");
        return projectRoot + File.separator + appRelativePath;
    }

    // ✅ Gets the KEY when you give the VALUE (case-insensitive)
    public static String getKeyByValue(String value) {
        for (String key : properties.stringPropertyNames()) {
            if (properties.getProperty(key).equalsIgnoreCase(value)) {
                return key;
            }
        }
        return null;
    }

}
