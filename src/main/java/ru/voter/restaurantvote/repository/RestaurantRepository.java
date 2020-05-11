package ru.voter.restaurantvote.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.voter.restaurantvote.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    //    https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"voters", "menu"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Restaurant> findAll();

    @EntityGraph(attributePaths = {"voters", "menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1")
    Restaurant getWithMenuAndVoters(int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(int id);
}
