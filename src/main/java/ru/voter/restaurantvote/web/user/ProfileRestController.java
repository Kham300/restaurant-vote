package ru.voter.restaurantvote.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.voter.restaurantvote.web.AuthUser;
import ru.voter.restaurantvote.model.User;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        super.delete(authUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @AuthenticationPrincipal AuthUser authUser) {
        super.update(user, authUser.id());
    }

}