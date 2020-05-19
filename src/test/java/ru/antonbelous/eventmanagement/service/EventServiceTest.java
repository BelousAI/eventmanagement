package ru.antonbelous.eventmanagement.service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.repository.EventRepository;
import ru.antonbelous.eventmanagement.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.antonbelous.eventmanagement.EventTestData.*;
import static ru.antonbelous.eventmanagement.UserTestData.ADMIN_ID;
import static ru.antonbelous.eventmanagement.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class EventServiceTest {
    private static final Logger log = getLogger(EventServiceTest.class);

    private static StringBuilder results = new StringBuilder();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }

    @Autowired
    private EventService service;

    @Autowired
    private EventRepository repository;

    @Test
    public void delete() throws Exception {
        service.delete(EVENT1_ID, USER_ID);
        Assert.assertNull(repository.get(EVENT1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() throws Exception {
        Assert.assertThrows(NotFoundException.class,
                () -> service.delete(1, USER_ID));
    }

    @Test
    public void deleteNotOwn() throws Exception {
        Assert.assertThrows(NotFoundException.class,
                () -> service.delete(EVENT1_ID, ADMIN_ID));
    }

    @Test
    public void create() throws Exception {
        Event newEvent = getNew();
        Event created = service.create(newEvent, USER_ID);
        Integer newId = created.getId();
        newEvent.setId(newId);
        EVENT_MATCHER.assertMatch(created, newEvent);
        EVENT_MATCHER.assertMatch(service.get(newId, USER_ID), newEvent);
    }

    @Test
    public void get() throws Exception {
        Event actual = service.get(ADMIN_EVENT_ID, ADMIN_ID);
        EVENT_MATCHER.assertMatch(actual, ADMIN_EVENT1);
    }

    @Test
    public void getNotFound() throws Exception {
        Assert.assertThrows(NotFoundException.class,
                () -> service.get(1, USER_ID));
    }

    @Test
    public void getNotOwn() throws Exception {
        Assert.assertThrows(NotFoundException.class,
                () -> service.get(EVENT1_ID, ADMIN_ID));
    }

    @Test
    public void update() throws Exception {
        Event updated = getUpdated();
        service.update(updated, USER_ID);
        EVENT_MATCHER.assertMatch(service.get(EVENT1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        NotFoundException ex = Assert.assertThrows(NotFoundException.class,
                () -> service.update(EVENT1, ADMIN_ID));
        Assert.assertEquals("Not found entity with id=" + EVENT1_ID, ex.getMessage());
    }

    @Test
    public void getAll() throws Exception {
        EVENT_MATCHER.assertMatch(service.getAll(USER_ID), USER_EVENTS);
    }

    @Test
    public void getBetweenInclusive() throws Exception {
        EVENT_MATCHER.assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.APRIL, 17),
                LocalDate.of(2020, Month.APRIL, 23), USER_ID),
                EVENT3, EVENT2, EVENT1);
    }

    @Test
    public void getBetweenWithNullDates() throws Exception {
        EVENT_MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), USER_EVENTS);
    }
}
