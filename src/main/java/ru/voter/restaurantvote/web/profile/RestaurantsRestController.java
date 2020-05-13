package ru.voter.restaurantvote.web.profile;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.voter.restaurantvote.model.Restaurant;
import ru.voter.restaurantvote.service.RestaurantService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = RestaurantsRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantsRestController {
    static final String REST_URL = "/restaurants";

    private final RestaurantService service;

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return service.get(id);
    }
}
