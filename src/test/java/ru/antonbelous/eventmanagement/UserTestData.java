package ru.antonbelous.eventmanagement;

import ru.antonbelous.eventmanagement.model.Role;
import ru.antonbelous.eventmanagement.model.User;

import java.util.Collections;
import java.util.Date;

import static ru.antonbelous.eventmanagement.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@mail.ru", "user", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@mail.ru", "newPassword", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setEmail("updated.email@gmail.com");
        return updated;
    }

    public static TestMatcher<User> USER_MATCHER = TestMatcher.of("registered", "roles");
}
