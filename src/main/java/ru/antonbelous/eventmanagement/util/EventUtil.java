package ru.antonbelous.eventmanagement.util;

import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.model.Status;
import ru.antonbelous.eventmanagement.to.EventTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventUtil {

    public static final List<Event> EVENTS = Arrays.asList(
            new Event(LocalDateTime.of(2020, Month.APRIL, 28, 9, 0), "Позвонить заведующей аптеки", Status.PLANNED),
            new Event(LocalDateTime.of(2020, Month.APRIL, 29, 11, 0), "Встреча с доктором Петровой В.В.", Status.PLANNED),
            new Event(LocalDateTime.of(2020, Month.APRIL, 29, 14, 0), "Подписание договора", Status.CANCELED),
            new Event(LocalDateTime.of(2020, Month.APRIL, 27, 15, 0), "Презентация нового продукта", Status.IN_PROGRESS),
            new Event(LocalDateTime.of(2020, Month.APRIL, 20, 13, 30), "Отгрузка товара", Status.FINISHED),
            new Event(LocalDateTime.of(2020, Month.APRIL, 23, 9, 30), "Позвонить заведующему клиники", Status.FINISHED),
            new Event(LocalDateTime.of(2020, Month.APRIL, 17, 18, 0), "Начало отпуска", Status.CANCELED)
    );

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
