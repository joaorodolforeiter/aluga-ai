package com.alugai.alugaai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class Config {

    @Autowired
    private Environment environment;

    @Bean
    public  JavaMailSenderImpl mailSenderConfig(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getProperty("spring.mail.host"));

        String portProperty = environment.getProperty("spring.mail.port");
        System.out.println("Valor da propriedade spring.mail.port: " + portProperty);

        javaMailSender.setPort(environment.getProperty("spring.mail.port", Integer.class));
        javaMailSender.setUsername(environment.getProperty("spring.mail.username"));
        javaMailSender.setPassword(environment.getProperty("spring.mail.password"));

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", environment.getProperty("spring.mail.properties.mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        properties.put("mail.debug", "true");

        return javaMailSender;
    }

}
