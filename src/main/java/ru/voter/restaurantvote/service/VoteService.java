package ru.voter.restaurantvote.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.voter.restaurantvote.model.Vote;
import ru.voter.restaurantvote.repository.RestaurantRepository;
import ru.voter.restaurantvote.repository.UserRepository;
import ru.voter.restaurantvote.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.voter.restaurantvote.util.ValidationUtil.checkNew;
import static ru.voter.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Slf4j
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Vote get(int id, int userId) {
        log.info("get vote {} for user {}", id, userId);
        return checkNotFoundWithId(voteRepository.getWithUser(id, userId), id);
    }

    @Transactional
    public void delete(int id, int userId) {
        log.info("delete vote {} for user {}", id, userId);
        checkNotFoundWithId(voteRepository.deleteByIdAndUserId(id, userId) != 0, id);
    }

    public List<Vote> getAll(int userId) {
        log.info("get all vote for user {}", userId);
        return voteRepository.findAllByUserId(userId);
    }

    @Transactional
    public Vote create(Vote newVote, int userId, int restaurantId) {
        //int userId = SecurityUtil.authUserId();
        Assert.notNull(newVote, "vote must not be null");
        checkNew(newVote);
        log.info("create {} for user {}", newVote, userId);
        //не поздно ли голосовать?
        if (LocalTime.now().isBefore(LocalTime.of(11, 0, 0))) {
            //если база вернула запись, значит нужно сделать апдейт с новым restaurantId
            Vote lastVote = voteRepository.findByUserIdAndVoteDate(userId, LocalDate.now());

            if (Objects.nonNull(lastVote)) {
                //апдейт
                lastVote.setRestaurant(restaurantRepository.getOne(restaurantId));
                return voteRepository.save(lastVote);
            }

            newVote.setUser(userRepository.getOne(userId));
            newVote.setRestaurant(restaurantRepository.getOne(restaurantId));

            return voteRepository.save(newVote);
        }
        return null;
    }
}
