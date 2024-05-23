package com.example.pensionatdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PensionatDbApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PensionatDbApplication.class, args);
        EventConsumer eventConsumer = context.getBean(EventConsumer.class);
        try {
            eventConsumer.consumeEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



//if(args.length == 0) {
//        SpringApplication.run(PensionatDbApplication.class, args);
//        }else if(Objects.equals(args[0], "CustomerConsoleApplication")){
//SpringApplication application = new SpringApplication(CustomerConsoleApplication.class);
//            application.setWebApplicationType(WebApplicationType.NONE);
//            application.run(args);
//
//        }
