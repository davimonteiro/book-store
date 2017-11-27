package br.com.davimonteiro;

import br.com.davimonteiro.config.DatabaseInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    @Bean
    CommandLineRunner commandLineRunner(DatabaseInitializer databaseInitializer) {
        return args -> {
            // Initialize the database
            databaseInitializer.init();
        };
    }

}
