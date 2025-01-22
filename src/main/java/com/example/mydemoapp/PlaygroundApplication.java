package com.example.mydemoapp;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple app to demo various features of Java, Spring Boot, and more...
 */
@SpringBootApplication
@EnableEncryptableProperties
public class PlaygroundApplication {

    static final private String JASYPT_PROPERTY = "jasypt.encryptor.password";
    static final private String JASYPT_ENCRYPTOR_PASSWORD = "DECRYPT_KEY";

    public static void main(String[] args) {
        System.setProperty(JASYPT_PROPERTY, System.getenv(JASYPT_ENCRYPTOR_PASSWORD));
        SpringApplication.run(PlaygroundApplication.class, args);
    }
}
