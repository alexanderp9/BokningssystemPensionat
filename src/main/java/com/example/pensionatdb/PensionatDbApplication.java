package com.example.pensionatdb;
import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PensionatDbApplication {

    public static void main(String[] args) {


        if(args.length == 0) {
            SpringApplication.run(PensionatDbApplication.class, args);
        }else if(Objects.equals(args[0], "CustomerConsoleApplication")){
            SpringApplication application = new SpringApplication(CustomerConsoleApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);

        }
    }
}
