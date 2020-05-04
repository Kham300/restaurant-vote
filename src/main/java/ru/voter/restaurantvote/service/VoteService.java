package ru.voter.restaurantvote.service;

import org.springframework.stereotype.Service;
import ru.voter.restaurantvote.model.Vote;
import ru.voter.restaurantvote.repository.VoteRepository;
import ru.voter.restaurantvote.web.SecurityUtil;

import java.util.List;

import static ru.voter.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id) {

    }

    public List<Vote> getAll() {
        return null;
    }

    public void update(Vote vote, int id) {

    }

    public Vote create(Vote vote) {
        return null;
    }
}
