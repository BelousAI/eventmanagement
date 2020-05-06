package ru.antonbelous.eventmanagement;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.antonbelous.eventmanagement.model.Role;
import ru.antonbelous.eventmanagement.model.User;
import ru.antonbelous.eventmanagement.to.EventTo;
import ru.antonbelous.eventmanagement.web.event.EventRestController;
import ru.antonbelous.eventmanagement.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            System.out.println();

            EventRestController eventController = appCtx.getBean(EventRestController.class);
            List<EventTo> filteredEvents = eventController.getBetweenHalfOpen(
                    LocalDate.of(2020, Month.APRIL, 23), LocalTime.of(7, 0),
                    LocalDate.of(2020, Month.APRIL, 29), LocalTime.of(14, 0));
            filteredEvents.forEach(System.out::println);
            System.out.println();
            System.out.println(eventController.getBetweenHalfOpen(null, null, null, null));
        }
    }
}
