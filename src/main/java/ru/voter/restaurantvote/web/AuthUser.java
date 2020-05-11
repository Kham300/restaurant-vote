package ru.voter.restaurantvote.web;

import lombok.Getter;
import lombok.NonNull;
import ru.voter.restaurantvote.model.User;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {
    @NonNull
    private User user;

    public AuthUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}
