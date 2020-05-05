package ru.antonbelous.eventmanagement.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.model.Status;
import ru.antonbelous.eventmanagement.repository.EventRepository;
import ru.antonbelous.eventmanagement.util.EventUtil;
import ru.antonbelous.eventmanagement.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.antonbelous.eventmanagement.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static ru.antonbelous.eventmanagement.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository
public class InMemoryEventRepository implements EventRepository {

    // Map  userId -> eventRepository
    private Map<Integer, InMemoryBaseRepository<Event>> uidToEventMap = new ConcurrentHashMap<>();

    {
        EventUtil.EVENTS.forEach(event -> save(event, USER_ID));
        save(new Event(LocalDateTime.of(2020, Month.MAY, 1, 9, 0), "Первомайская демонстрация", Status.PLANNED), ADMIN_ID);
        save(new Event(LocalDateTime.of(2020, Month.MAY, 2, 12, 0), "Подведение итогов", Status.PLANNED), ADMIN_ID);
    }

    @Override
    public Event save(Event event, int userId) {
        InMemoryBaseRepository<Event> events = uidToEventMap.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        return events.save(event);
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryBaseRepository<Event> events = uidToEventMap.get(userId);
        return events != null && events.delete(id);
    }

    @Override
    public Event get(int id, int userId) {
        InMemoryBaseRepository<Event> events = uidToEventMap.get(userId);
        return events == null ? null : events.get(id);
    }

    @Override
    public List<Event> getAll(int userId) {
        return getAllFiltered(userId, event -> true);
    }

    @Override
    public List<Event> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllFiltered(userId, event -> Util.isBetweenHalfOpen(event.getStartDateTime(), startDateTime, endDateTime));
    }

    private List<Event> getAllFiltered(int userId, Predicate<Event> filter) {
        InMemoryBaseRepository<Event> events = uidToEventMap.get(userId);
        return events == null ? Collections.emptyList() :
                events.getCollection().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Event::getStartDateTime).reversed())
                .collect(Collectors.toList());
    }
}
