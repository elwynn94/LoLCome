package com.elwynn94.lolcome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
//(exclude = { SecurityAutoConfiguration.class })
public class LoLComeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoLComeApplication.class, args);
    }

}
