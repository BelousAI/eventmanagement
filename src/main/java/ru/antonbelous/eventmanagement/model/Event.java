package ru.antonbelous.eventmanagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Event.ALL_SORTED, query = "SELECT e FROM Event e WHERE e.user.id=:userId ORDER BY e.startDateTime DESC"),
        @NamedQuery(name = Event.DELETE, query = "DELETE FROM Event e WHERE e.id=:id AND e.user.id=:userId"),
        @NamedQuery(name = Event.GET_BETWEEN, query = "SELECT e FROM Event e " +
                "WHERE e.user.id=:userId AND e.startDateTime >= :startDateTime AND e.startDateTime < :endDateTime ORDER BY e.startDateTime DESC"),
//        @NamedQuery(name = Meal.UPDATE, query = "UPDATE Meal m SET m.dateTime = :datetime, m.calories= :calories," +
//                "m.description=:desc where m.id=:id and m.user.id=:userId")
})
@Entity
@Table(name = "events", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "start_date_time"}, name = "events_unique_user_datetime_idx")})
public class Event extends AbstractBaseEntity {

    public static final String ALL_SORTED = "Event.getAll";
    public static final String DELETE = "Event.delete";
    public static final String GET_BETWEEN = "Event.getBetween";

    @Column(name = "start_date_time", nullable = false)
    @NotNull
    private LocalDateTime startDateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status")
    private Status currentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public Event() {
    }

    public Event(LocalDateTime startDateTime, String description, Status currentStatus) {
        this(null, startDateTime, description, currentStatus);
    }

    public Event(Integer id, LocalDateTime startDateTime, String description, Status currentStatus) {
        super(id);
        this.startDateTime = startDateTime;
        this.description = description;
        this.currentStatus = currentStatus;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
