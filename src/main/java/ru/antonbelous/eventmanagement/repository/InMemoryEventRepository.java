package ru.antonbelous.eventmanagement.repository;

import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.util.EventUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryEventRepository implements EventRepository {

    private Map<Integer, Event> idToEvent = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        EventUtil.EVENTS.forEach(this::save);
    }

    @Override
    public Event save(Event event) {
        if (event.isNew()) {
            event.setId(counter.incrementAndGet());
            idToEvent.put(event.getId(), event);
            return event;
        }
        // handle case: update if present in storage
        return idToEvent.computeIfPresent(event.getId(), (id, oldEvent) -> event);
    }

    @Override
    public boolean delete(int id) {
        return idToEvent.remove(id) != null;
    }

    @Override
    public Event get(int id) {
        return idToEvent.get(id);
    }

    @Override
    public Collection<Event> getAll() {
        return idToEvent.values();
    }
}
