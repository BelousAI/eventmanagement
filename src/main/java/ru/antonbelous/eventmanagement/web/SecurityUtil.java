package ru.antonbelous.eventmanagement.web;

import ru.antonbelous.eventmanagement.model.AbstractBaseEntity;

public class SecurityUtil {
    private SecurityUtil() {
    }

    private static int id = AbstractBaseEntity.START_SEQ;

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }
}
