package ru.antonbelous.eventmanagement.web.user;

import org.springframework.stereotype.Controller;
import ru.antonbelous.eventmanagement.model.User;

import static ru.antonbelous.eventmanagement.web.SecurityUtil.authUserId;

@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }
}
