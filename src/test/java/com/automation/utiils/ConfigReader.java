package com.automation.utiils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    // This is a placeholder for the ConfigReader class. You can implement methods to read configuration properties from a file or environment variables as needed.

    static Properties properties;
    public void initialize() {
        properties = new Properties();;
         try (InputStream input = new FileInputStream("config.properties")) {
             properties.load(input);
         } catch (IOException ex) {
             ex.printStackTrace();
         }
    }

    public static String getProperty(String key) {
        // Return the value of the specified property key
        // You can implement this method to return the value from the loaded properties
        return properties.getProperty(key); // Placeholder return statement
    }
}
