package ru.antonbelous.eventmanagement.repository.inmemory;

import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.model.Status;
import ru.antonbelous.eventmanagement.repository.EventRepository;
import ru.antonbelous.eventmanagement.util.EventUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.antonbelous.eventmanagement.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static ru.antonbelous.eventmanagement.repository.inmemory.InMemoryUserRepository.USER_ID;

public class InMemoryEventRepository implements EventRepository {

    // Map  userId -> (eventId-> event)
    private Map<Integer, Map<Integer, Event>> uidToEventMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        EventUtil.EVENTS.forEach(event -> save(event, USER_ID));
        save(new Event(LocalDateTime.of(2020, Month.MAY, 1, 9, 0), "Первомайская демонстрация", Status.PLANNED), ADMIN_ID);
        save(new Event(LocalDateTime.of(2020, Month.MAY, 2, 12, 0), "Подведение итогов", Status.PLANNED), ADMIN_ID);
    }

    @Override
    public Event save(Event event, int userId) {
        Map<Integer, Event> eventMap = uidToEventMap.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (event.isNew()) {
            event.setId(counter.incrementAndGet());
            eventMap.put(event.getId(), event);
            return event;
        }
        // handle case: update if present in storage
        return eventMap.computeIfPresent(event.getId(), (id, oldEvent) -> event);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Event> eventMap = uidToEventMap.get(userId);
        return eventMap != null && eventMap.remove(id) != null;
    }

    @Override
    public Event get(int id, int userId) {
        Map<Integer, Event> eventMap = uidToEventMap.get(userId);
        return eventMap == null ? null : eventMap.get(id);
    }

    @Override
    //Fix with Spring
    public List<Event> getAll(int userId) {
        Map<Integer, Event> eventMap = uidToEventMap.get(userId);
        return eventMap == null ? Collections.emptyList() :
                eventMap.values().stream()
                .sorted(Comparator.comparing(Event::getStartDateTime).reversed())
                .collect(Collectors.toList());
    }
}
