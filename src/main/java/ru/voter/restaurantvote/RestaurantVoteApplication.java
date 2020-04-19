package ru.voter.restaurantvote;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.voter.restaurantvote.model.Role;
import ru.voter.restaurantvote.model.User;
import ru.voter.restaurantvote.repository.UserRepository;

import java.util.Collections;

@SpringBootApplication
@AllArgsConstructor
public class RestaurantVoteApplication implements ApplicationRunner {
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVoteApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
         System.out.println(userRepository.findAll());
    }

}
