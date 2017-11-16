package br.com.davimonteiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private UserRepository repository;

    @RequestMapping("/")
    public String index() {
        repository.save(User.builder().id(1L).name("Davi").password("1234").build());
        return "Greetings from Spring Boot!";
    }

}
