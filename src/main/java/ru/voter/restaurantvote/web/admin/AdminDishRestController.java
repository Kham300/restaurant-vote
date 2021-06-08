package ru.voter.restaurantvote.web.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voter.restaurantvote.model.Dish;
import ru.voter.restaurantvote.service.DishService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishRestController {
    static final String REST_URL = "/management/restaurants";

    private final DishService dishService;

    @GetMapping("/{restaurantId}/dishes/{id}")
    public Dish get(@PathVariable int id, @PathVariable int restaurantId) {
        return dishService.get(id, restaurantId);
    }

    @DeleteMapping("/{restaurantId}/dishes/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        dishService.delete(id, restaurantId);
    }

    @GetMapping("/{restaurantId}/dishes")
    public List<Dish> getAll(@PathVariable int restaurantId) {
        return dishService.getAll(restaurantId);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish,
                       @PathVariable int restaurantId,
                       @PathVariable int id) {
        dishService.update(dish, restaurantId, id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@PathVariable int restaurantId, @RequestBody Dish dish) {
        Dish created = dishService.create(dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/dishes/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
