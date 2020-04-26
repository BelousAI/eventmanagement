package ru.antonbelous.eventmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event {

    private Integer id;
    private LocalDateTime startDateTime;
    private String description;
    private Status currentStatus;

    public Event(LocalDateTime startDateTime, String description, Status currentStatus) {
        this(null, startDateTime, description, currentStatus);
    }

    public Event(Integer id, LocalDateTime startDateTime, String description, Status currentStatus) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.description = description;
        this.currentStatus = currentStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDate getDate() {
        return startDateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return startDateTime.toLocalTime();
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", description='" + description + '\'' +
                ", currentStatus=" + currentStatus +
                '}';
    }
}
