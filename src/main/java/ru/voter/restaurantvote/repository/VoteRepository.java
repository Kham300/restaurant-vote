package ru.voter.restaurantvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.voter.restaurantvote.model.Vote;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.id = ?1 AND v.user.id = ?2")
    Vote getWithUser(int id, int userId);

    Vote findTopByUserIdOrderByVoteDateDesc(int userId);
}
