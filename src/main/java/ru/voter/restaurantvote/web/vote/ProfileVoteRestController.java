package ru.voter.restaurantvote.web.vote;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voter.restaurantvote.model.Vote;
import ru.voter.restaurantvote.service.VoteService;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteRestController {
    static final String REST_URL = "/rest/profile/votes";

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
        voteService.delete(id);
    }

    @GetMapping
    public List<Vote> getAll() {
        return voteService.getAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote, @PathVariable int id) {
        voteService.update(vote, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createWithLocation(@RequestBody Vote vote) {
        Vote created = voteService.create(vote);
        if (Objects.nonNull(created)) {
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return new ResponseEntity<>("too late try tomorrow", HttpStatus.CONFLICT);
    }
}
