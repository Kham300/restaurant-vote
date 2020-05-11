package ru.voter.restaurantvote.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.voter.restaurantvote.model.Dish;
import ru.voter.restaurantvote.model.Restaurant;
import ru.voter.restaurantvote.repository.DishRepository;
import ru.voter.restaurantvote.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static ru.voter.restaurantvote.util.ValidationUtil.*;

@Slf4j
@Service
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public void update(Dish dish, int restaurantId, int id) {
        Assert.notNull(dish, "dish must not be null");
        assureIdConsistent(dish, id);
        log.info("update {} for restaurant {}", dish, restaurantId);
        checkNotFoundWithId(saveDish(dish, restaurantId), dish.id());
    }

    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNew(dish);
        log.info("create {} for restaurant {}", dish, restaurantId);
        return saveDish(dish, restaurantId);
    }

    public void delete(int id, int restaurantId) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        checkNotFoundWithId(dishRepository.delete(id, restaurantId) != 0, id);
    }

    public Dish get(int id, int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return checkNotFoundWithId(dishRepository.findById(id)
                .filter(dish -> Optional.of(dish)
                        .map(Dish::getRestaurant)
                        .map(Restaurant::getId)
                        .filter(restId -> restId == restaurantId)
                        .isPresent())
                .orElse(null), id);
    }

    public List<Dish> getAll(int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return dishRepository.getAll(restaurantId);
    }

    private Dish saveDish(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.id(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

    public Dish getWithRestaurant(int id, int restaurantId) {
        return dishRepository.getWithRestaurant(id, restaurantId);
    }
}
