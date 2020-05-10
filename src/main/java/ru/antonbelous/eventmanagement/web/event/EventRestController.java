package ru.antonbelous.eventmanagement.web.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.service.EventService;
import ru.antonbelous.eventmanagement.to.EventTo;
import ru.antonbelous.eventmanagement.util.EventUtil;
import ru.antonbelous.eventmanagement.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.antonbelous.eventmanagement.util.ValidationUtil.assureIdConsistent;
import static ru.antonbelous.eventmanagement.util.ValidationUtil.checkNew;

@Controller
public class EventRestController {
    private static final Logger log = LoggerFactory.getLogger(EventRestController.class);

    private final EventService service;

    public EventRestController(EventService service) {
        this.service = service;
    }

    public Event create(Event event) {
        int userId = SecurityUtil.authUserId();
        checkNew(event);
        log.info("create {} for user {}", event, userId);
        return service.create(event, userId);
    }

    public void update(Event event, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(event, id);
        log.info("update {} for user {}", event, userId);
        service.update(event, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete event by id= {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public Event get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get event by id= {} fo user {}", id, userId);
        return service.get(id, userId);
    }

    public List<EventTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("get all events for user {}", userId);
        return EventUtil.getTos(service.getAll(userId));
    }

    public List<EventTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                    @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Event> events = service.getBetweenInclusive(startDate, endDate, userId);
        return EventUtil.getFilteredTos(events, startTime, endTime);
    }
}
