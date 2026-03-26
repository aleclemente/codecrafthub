package com.codecrafthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CodeCraftHub Application
 *
 * Main entry point for the CodeCraftHub REST API.
 * A learning platform for tracking courses development progress.
 */
@SpringBootApplication
public class CodeCraftHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeCraftHubApplication.class, args);
    }

}
