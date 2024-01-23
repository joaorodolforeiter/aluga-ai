package com.alugai.alugaai;

import com.alugai.alugaai.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AlugaAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlugaAiApplication.class, args);
    }

}
