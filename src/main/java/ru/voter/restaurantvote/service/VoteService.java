package ru.voter.restaurantvote.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.voter.restaurantvote.model.Vote;
import ru.voter.restaurantvote.repository.VoteRepository;
import ru.voter.restaurantvote.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.voter.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Slf4j
@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        return checkNotFoundWithId(voteRepository.getWithUser(id, userId), id);
    }

    public void delete(int id) {

    }

    public List<Vote> getAll() {
        return null;
    }

    public void update(Vote vote, int id) {

    }

    public Vote create(Vote newVote) {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0, 0))) {
            //TODO проверить что юзер выгрузился
            Vote lastVote = voteRepository.findTopByUserIdOrderByVoteDateDesc(1);
            Optional.ofNullable(lastVote)
                    .filter(vote -> LocalDate.now().isEqual(vote.getVoteDate()))
                    .ifPresent(voted -> {
                        newVote.setId(voted.getId());
                        newVote.setUser(voted.getUser());
                    });

            return voteRepository.save(newVote);
        }
        return null;
    }
}
