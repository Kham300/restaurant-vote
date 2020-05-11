package ru.voter.restaurantvote.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.voter.restaurantvote.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.id = ?1 AND v.user.id = ?2")
    Vote getWithUser(int id, int userId);

    //TODO проверить что юзер  и ресторан выгрузились
    @EntityGraph(attributePaths = {"user", "restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    Vote findByUserIdAndVoteDate(int userId, LocalDate voteDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id = ?1 AND v.user.id = ?2")
    int deleteByIdAndUserId(int id, int userId);

    List<Vote> findAllByUserId(int userId);
}
