package ru.antonbelous.eventmanagement.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.antonbelous.eventmanagement.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.antonbelous.eventmanagement.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.antonbelous.eventmanagement.util.ValidationUtil.checkNotFoundWithId;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Event create(Event event, int userId) {
        Assert.notNull(event, "event must not be null");
        return repository.save(event, userId);
    }

    public void update(Event event, int userId) {
        Assert.notNull(event, "event must not be null");
        checkNotFoundWithId(repository.save(event, userId), event.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Event get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Event> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Event> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
    }
}
