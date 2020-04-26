package ru.antonbelous.eventmanagement.repository;

import ru.antonbelous.eventmanagement.model.Event;

import java.util.Collection;

public interface EventRepository {

    //null if not found, when updated
    Event save(Event event);

    //null if not found
    boolean delete(int id);

    //null if not found
    Event get(int id);

    Collection<Event> getAll();
}
