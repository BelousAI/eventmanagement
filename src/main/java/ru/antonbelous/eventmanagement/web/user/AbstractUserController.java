package ru.antonbelous.eventmanagement.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antonbelous.eventmanagement.model.User;
import ru.antonbelous.eventmanagement.service.UserService;

import java.util.List;

import static ru.antonbelous.eventmanagement.util.ValidationUtil.checkNew;
import static ru.antonbelous.eventmanagement.util.ValidationUtil.assureIdConsistent;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    private UserService service;

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void delete(int id) {
        log.info("delete with id={}", id);
        service.delete(id);
    }

    public User get(int id) {
        log.info("get with id={}", id);
        return service.get(id);
    }

    public User getByEmail(String email) {
        log.info("get by email={}", email);
        return service.getByEmail(email);
    }

    public List<User> getAll() {
        log.info("get all");
        return service.getAll();
    }

}
