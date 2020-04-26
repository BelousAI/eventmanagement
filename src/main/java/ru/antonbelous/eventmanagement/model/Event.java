package ru.antonbelous.eventmanagement.model;

import java.time.LocalDateTime;

public class Event {

    private Integer id;
    private LocalDateTime startDateTime;
    private String description;
    private Status currentStatus;
    private User userId;

    public Event(LocalDateTime startDateTime, String description, Status currentStatus, User userId) {
        this.startDateTime = startDateTime;
        this.description = description;
        this.currentStatus = currentStatus;
        this.userId = userId;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
