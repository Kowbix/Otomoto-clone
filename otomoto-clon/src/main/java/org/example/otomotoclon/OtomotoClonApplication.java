package org.example.otomotoclon;

import org.example.otomotoclon.translator.BrandMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class OtomotoClonApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtomotoClonApplication.class, args);
    }
}
