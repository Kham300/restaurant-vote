package ru.voter.restaurantvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voter.restaurantvote.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
}
