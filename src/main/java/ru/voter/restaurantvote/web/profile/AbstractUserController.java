package ru.voter.restaurantvote.web.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voter.restaurantvote.model.User;
import ru.voter.restaurantvote.service.UserService;
import ru.voter.restaurantvote.web.AuthUser;

import java.util.List;

import static ru.voter.restaurantvote.util.ValidationUtil.assureIdConsistent;
import static ru.voter.restaurantvote.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    private static final Logger log = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    private UserService service;

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, AuthUser authUser) {
        log.info("update {} with id={}", user, authUser.id());
        assureIdConsistent(user, authUser.id());
        user.setRoles(authUser.getUser().getRoles());
        if (user.getPassword() == null) {
            user.setPassword(authUser.getUser().getPassword());
        }
        service.update(user);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }
}