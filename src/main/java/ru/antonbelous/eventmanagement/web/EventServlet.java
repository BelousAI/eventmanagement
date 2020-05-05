package ru.antonbelous.eventmanagement.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.model.Status;
import ru.antonbelous.eventmanagement.web.event.EventRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.antonbelous.eventmanagement.util.DateTimeUtil.parseLocalDate;
import static ru.antonbelous.eventmanagement.util.DateTimeUtil.parseLocalTime;

public class EventServlet extends HttpServlet {

    private ConfigurableApplicationContext springContext;
    private EventRestController eventController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        eventController = springContext.getBean(EventRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                eventController.delete(id);
                response.sendRedirect("events");
                break;
            case "create":
            case "update":
                final Event event = "create".equals(action) ?
                        new Event(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", Status.PLANNED) :
                        eventController.get(getId(request));
                request.setAttribute("event", event);
                request.getRequestDispatcher("/eventForm.jsp").forward(request, response);
                break;
            case "filter":
                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
                request.setAttribute("events", eventController.getBetweenHalfOpen(startDate, startTime, endDate, endTime));
                request.getRequestDispatcher("/events.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("events", eventController.getAll());
                request.getRequestDispatcher("/events.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Event event = new Event(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Status.valueOf(request.getParameter("status")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            eventController.create(event);
        } else {
            eventController.update(event, getId(request));
        }
        response.sendRedirect("events");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
