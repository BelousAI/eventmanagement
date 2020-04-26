package ru.antonbelous.eventmanagement.to;

import ru.antonbelous.eventmanagement.model.Status;

import java.time.LocalDateTime;

public class EventTo {

    private final Integer id;
    private final LocalDateTime startDateTime;
    private final String description;
    private final Status currentStatus;

    public EventTo(Integer id, LocalDateTime startDateTime, String description, Status currentStatus) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.description = description;
        this.currentStatus = currentStatus;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public String getDescription() {
        return description;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    @Override
    public String toString() {
        return "EventTo{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", description='" + description + '\'' +
                ", currentStatus=" + currentStatus +
                '}';
    }
}
