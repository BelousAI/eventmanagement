package ru.antonbelous.eventmanagement.util;

import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.to.EventTo;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventUtil {
    private EventUtil() {
    }

    public static List<EventTo> getTos(Collection<Event> events) {
        return filteredByStreams(events, event -> true);
    }

    public static List<EventTo> getFilteredTos(Collection<Event> events, LocalTime startTime, LocalTime endTime) {
        return filteredByStreams(events, event -> Util.isBetweenHalfOpen(event.getTime(), startTime, endTime));
    }

    public static List<EventTo> filteredByStreams(Collection<Event> events, Predicate<Event> filter) {
        return events.stream()
                .filter(filter)
                .map(EventUtil::createTo)
                .collect(Collectors.toList());
    }

    private static EventTo createTo(Event event) {
        return new EventTo(event.getId(), event.getStartDateTime(), event.getDescription(), event.getCurrentStatus());
    }
}
