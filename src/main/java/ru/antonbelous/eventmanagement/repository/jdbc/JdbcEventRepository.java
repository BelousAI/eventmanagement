package ru.antonbelous.eventmanagement.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

public class JdbcEventRepository implements EventRepository {
    private static final RowMapper<Event> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Event.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertEvent;

    @Autowired
    public JdbcEventRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertEvent = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("events")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Event save(Event event, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", event.getId())
                .addValue("description", event.getDescription())
                .addValue("start_date_time", event.getStartDateTime())
                .addValue("user_id", userId)
                .addValue("current_status", event.getCurrentStatus());

        if (event.isNew()) {
            Number newId = insertEvent.executeAndReturnKey(map);
            event.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                            "UPDATE events " +
                            "   SET description=:description, start_date_time=:start_date_time, current_status=:current_status " +
                            " WHERE id=:id AND user_id=:user_id"
                    , map) == 0) {
                return null;
            }
        }
        return event;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM events WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Event get(int id, int userId) {
        List<Event> events = jdbcTemplate.query(
                "SELECT * FROM events WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(events);
    }

    @Override
    public List<Event> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM events WHERE user_id=? ORDER BY start_date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Event> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM events WHERE user_id=?  AND start_date_time >=  ? AND start_date_time < ? ORDER BY start_date_time DESC",
                ROW_MAPPER, userId, startDate, endDate);
    }
}
