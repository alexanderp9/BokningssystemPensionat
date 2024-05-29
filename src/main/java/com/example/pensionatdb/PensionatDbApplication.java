package com.example.pensionatdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.WebApplicationType;

import java.util.Objects;

@SpringBootApplication
public class PensionatDbApplication {

    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication.run(PensionatDbApplication.class, args);
        } else if (Objects.equals(args[0], "CustomerConsoleApplication")) {
            SpringApplication application = new SpringApplication(CustomerConsoleApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "ShipperConsoleApplication")) {
            SpringApplication application = new SpringApplication(ShipperConsoleApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else {
            ConfigurableApplicationContext context = SpringApplication.run(PensionatDbApplication.class, args);
            EventConsumer eventConsumer = context.getBean(EventConsumer.class);
            try {
                eventConsumer.consumeEvents();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



