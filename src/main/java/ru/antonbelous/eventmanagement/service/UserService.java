package ru.antonbelous.eventmanagement.service;

import ru.antonbelous.eventmanagement.model.User;
import ru.antonbelous.eventmanagement.repository.UserRepository;
import ru.antonbelous.eventmanagement.repository.inmemory.InMemoryUserRepository;

import java.util.List;

import static ru.antonbelous.eventmanagement.util.ValidationUtil.checkNotFound;
import static ru.antonbelous.eventmanagement.util.ValidationUtil.checkNotFoundWithId;

public class UserService {

    private UserRepository repository;

    public void setRepository(InMemoryUserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        return repository.save(user);
    }

    public void update(User user) {
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }
}
