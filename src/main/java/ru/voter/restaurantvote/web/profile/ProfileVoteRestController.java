package ru.voter.restaurantvote.web.profile;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voter.restaurantvote.model.Vote;
import ru.voter.restaurantvote.service.VoteService;
import ru.voter.restaurantvote.web.AuthUser;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteRestController {
    static final String REST_URL = "/profile";

    private final VoteService voteService;

    @GetMapping("/votes/{id}")
    public Vote get(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        return voteService.get(id, authUser.id());
    }

    @DeleteMapping("/votes/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        voteService.delete(id, authUser.id());
    }

    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return voteService.getAll(authUser.id());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/restaurants/{restaurantId}/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVoteWithLocation(@RequestBody Vote newVote,
                                                    @PathVariable int restaurantId,
                                                    @AuthenticationPrincipal AuthUser authUser) {
        Vote created = voteService.create(newVote, authUser.id(), restaurantId);
        if (Objects.nonNull(created)) {
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/votes/{id}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return new ResponseEntity<>("too late try tomorrow", HttpStatus.CONFLICT);
    }
}
