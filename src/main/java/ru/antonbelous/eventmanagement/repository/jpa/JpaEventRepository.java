package ru.antonbelous.eventmanagement.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.model.User;
import ru.antonbelous.eventmanagement.repository.EventRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class JpaEventRepository implements EventRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Event save(Event event, int userId) {
        event.setUser(em.getReference(User.class, userId));
        if (event.isNew()) {
            em.persist(event);
            return event;
        } else if (get(event.getId(), userId) == null) {
            return null;
        }
        return em.merge(event);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Event.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Event get(int id, int userId) {
        Event event = em.find(Event.class, id);
        return event != null && event.getUser().getId() == userId ? event : null;
    }

    @Override
    public List<Event> getAll(int userId) {
        return em.createNamedQuery(Event.ALL_SORTED, Event.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Event> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Event.GET_BETWEEN, Event.class)
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}
