package ru.voter.restaurantvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voter.restaurantvote.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}