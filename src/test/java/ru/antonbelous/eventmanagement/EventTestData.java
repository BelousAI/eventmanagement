package ru.antonbelous.eventmanagement;

import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.model.Status;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.antonbelous.eventmanagement.model.AbstractBaseEntity.START_SEQ;

public class EventTestData {

    public static final int EVENT1_ID = START_SEQ + 2;
    public static final int ADMIN_EVENT_ID = START_SEQ + 9;

    public static final Event EVENT1 =  new Event(EVENT1_ID, of(2020, Month.APRIL, 17, 18, 0), "Начало отпуска", Status.CANCELED);
    public static final Event EVENT2 =  new Event(EVENT1_ID + 1, of(2020, Month.APRIL, 20, 13, 30), "Отгрузка товара", Status.FINISHED);
    public static final Event EVENT3 =  new Event(EVENT1_ID + 2, of(2020, Month.APRIL, 23, 9, 30), "Позвонить заведующему клиники", Status.FINISHED);
    public static final Event EVENT4 =  new Event(EVENT1_ID + 3, of(2020, Month.APRIL, 27, 15, 0), "Презентация нового продукта", Status.IN_PROGRESS);
    public static final Event EVENT5 =  new Event(EVENT1_ID + 4, of(2020, Month.APRIL, 28, 9, 0), "Позвонить заведующей аптеки", Status.PLANNED);
    public static final Event EVENT6 =  new Event(EVENT1_ID + 5, of(2020, Month.APRIL, 29, 11, 0), "Встреча с доктором Петровой В.В.", Status.PLANNED);
    public static final Event EVENT7 =  new Event(EVENT1_ID + 6, of(2020, Month.APRIL, 29, 14, 0), "Подписание договора", Status.CANCELED);
    public static final Event ADMIN_EVENT1 =  new Event(ADMIN_EVENT_ID, of(2020, Month.APRIL, 18, 14, 0), "Админ: подготовить отчет", Status.PLANNED);
    public static final Event ADMIN_EVENT2 =  new Event(ADMIN_EVENT_ID + 1, of(2020, Month.APRIL, 27, 9, 0), "Админ: предоставить доступ", Status.IN_PROGRESS);

    public static final List<Event> USER_EVENTS = Arrays.asList(EVENT7, EVENT6, EVENT5, EVENT4, EVENT3, EVENT2, EVENT1);

    public static Event getCreated() {
        return new Event(null, of(2020, Month.MAY, 12, 9, 0), "Запланированное событие", Status.PLANNED);
    }

    public static Event getUpdated() {
        return new Event(EVENT1_ID, EVENT1.getStartDateTime(), "Обновленное событие", Status.CANCELED);
    }

    public static TestMatcher<Event> EVENT_MATCHER = TestMatcher.of();
}
