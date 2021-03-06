package ru.voter.restaurantvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.voter.restaurantvote.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(int id, int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId")
    List<Dish> getAll(int restaurantId);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.id = ?1 and d.restaurant.id = ?2")
    Dish getWithRestaurant(int id, int restaurantId);
}
