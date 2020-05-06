package ru.antonbelous.eventmanagement.inmemory;

import org.springframework.stereotype.Repository;
import ru.antonbelous.eventmanagement.UserTestData;
import ru.antonbelous.eventmanagement.model.User;
import ru.antonbelous.eventmanagement.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.antonbelous.eventmanagement.UserTestData.ADMIN;
import static ru.antonbelous.eventmanagement.UserTestData.USER;

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    public void init() {
        map.clear();
        map.put(UserTestData.USER_ID, USER);
        map.put(UserTestData.ADMIN_ID, ADMIN);
    }

    @Override
    public User getByEmail(String email) {
        return getCollection().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }
}