package ru.antonbelous.eventmanagement.service;

import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.repository.EventRepository;


import java.util.Collection;

import static ru.antonbelous.eventmanagement.util.ValidationUtil.checkNotFoundWithId;
import static ru.antonbelous.eventmanagement.util.ValidationUtil.checkNotFound;

public class EventService {

    private EventRepository repository;

    public Event create(Event event, int userId) {
        return repository.save(event, userId);
    }

    public void update(Event event, int userId) {
        checkNotFoundWithId(repository.save(event, userId), event.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Event get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Event> getAll(int userId) {
        return repository.getAll(userId);
    }
}
