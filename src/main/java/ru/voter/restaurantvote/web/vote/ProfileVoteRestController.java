package ru.voter.restaurantvote.web.vote;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voter.restaurantvote.model.Vote;
import ru.voter.restaurantvote.service.VoteService;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteRestController {
    static final String REST_URL = "/rest/profile/vote";

    private final VoteService voteService;

    public ProfileVoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        return voteService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        voteService.delete(id, 1);
    }

    @GetMapping
    public List<Vote> getAll() {
        return voteService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createWithLocation(@RequestBody Vote vote, @RequestParam Integer restaurantId) {
        Vote created = voteService.create(vote, 1, restaurantId);
        if (Objects.nonNull(created)) {
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return new ResponseEntity<>("too late try tomorrow", HttpStatus.CONFLICT);
    }
}
