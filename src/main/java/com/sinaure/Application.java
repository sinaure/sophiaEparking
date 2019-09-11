package com.sinaure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sinaure.config.GlobalProperties;
import com.sinaure.config.WordpressProperties;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private WordpressProperties wpProperties;

    @Autowired
    private GlobalProperties globalProperties;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(globalProperties);
        System.out.println(wpProperties);
    }
}
