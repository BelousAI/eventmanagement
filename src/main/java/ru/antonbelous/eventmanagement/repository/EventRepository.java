package ru.antonbelous.eventmanagement.repository;

import ru.antonbelous.eventmanagement.model.Event;

import java.util.List;

public interface EventRepository {

    // null if updated event do not belong to userId
    Event save(Event event, int userId);

    // false if event do not belong to userId
    boolean delete(int id, int userId);

    // null if event do not belong to userId
    Event get(int id, int userId);

    // ORDERED dateTime desc
    List<Event> getAll(int userId);
}
