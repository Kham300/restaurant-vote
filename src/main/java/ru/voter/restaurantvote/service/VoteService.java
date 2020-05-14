package ru.voter.restaurantvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.voter.restaurantvote.model.Vote;
import ru.voter.restaurantvote.repository.RestaurantRepository;
import ru.voter.restaurantvote.repository.UserRepository;
import ru.voter.restaurantvote.repository.VoteRepository;
import ru.voter.restaurantvote.util.exception.TooLateToVoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.voter.restaurantvote.util.DateTimeUtil.parseLocalTime;
import static ru.voter.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {
    @Value("${api.vote.deadline:'11:00:00'}")
    private String deadlineToVote;

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public Vote get(int id, int userId) {
        log.info("get vote {} for user {}", id, userId);
        return checkNotFoundWithId(voteRepository.getWithUser(id, userId), id);
    }

    @Transactional
    @CacheEvict(value = "votes", allEntries = true)
    public void delete(int id, int userId) {
        log.info("delete vote {} for user {}", id, userId);
        checkNotFoundWithId(voteRepository.deleteByIdAndUserId(id, userId) != 0, id);
    }

    @Cacheable("votes")
    public List<Vote> getAll(int userId) {
        log.info("get all vote for user {}", userId);
        return voteRepository.findAllByUserId(userId);
    }

    @Transactional
    @CacheEvict(value = "votes", allEntries = true)
    public Vote create(int userId, int restaurantId) {
        Vote newVote = new Vote();
        log.info("create {} for user {}", newVote, userId);
        //не поздно ли голосовать?
        if (LocalTime.now().isBefore(parseLocalTime(deadlineToVote))) {
            //если база вернула запись, значит нужно сделать апдейт с новым restaurantId
            Vote lastVote = voteRepository.findByUserIdAndVoteDate(userId, LocalDate.now());

            if (Objects.nonNull(lastVote)) {
                //апдейт
                lastVote.setRestaurant(restaurantRepository.getOne(restaurantId));
                return voteRepository.save(lastVote);
            }

            newVote.setUser(userRepository.getOne(userId));
            newVote.setVoteDate(LocalDate.now());
            newVote.setRestaurant(restaurantRepository.getOne(restaurantId));

            return voteRepository.save(newVote);
        }
        throw new TooLateToVoteException();
    }
}
