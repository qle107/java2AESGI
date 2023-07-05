package org.esgi.cookmaster;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {
    public static void main(String[] args) {
        // Load the configuration file
        Properties config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/config.cfg")) {
            config.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading the configuration file: " + e.getMessage());
            return;
        }

        // Retrieve and use the configuration values
        String dbHost = config.getProperty("db.host");
        String dbPort = config.getProperty("db.port");
        String dbName = config.getProperty("db.name");
        String dbUsername = config.getProperty("db.username");
        String dbPassword = config.getProperty("db.password");

        // Use the configuration values in your program
        System.out.println("Database Configuration:");
        System.out.println("Host: " + dbHost);
        System.out.println("Port: " + dbPort);
        System.out.println("Name: " + dbName);
        System.out.println("Username: " + dbUsername);
        System.out.println("Password: " + dbPassword);
    }
}

