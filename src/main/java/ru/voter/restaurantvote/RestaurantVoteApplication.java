package ru.voter.restaurantvote;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class RestaurantVoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantVoteApplication.class, args);
    }
}
