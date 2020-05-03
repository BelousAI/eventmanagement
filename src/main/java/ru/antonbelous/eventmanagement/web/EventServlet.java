package ru.antonbelous.eventmanagement.web;

import ru.antonbelous.eventmanagement.model.Event;
import ru.antonbelous.eventmanagement.model.Status;
import ru.antonbelous.eventmanagement.repository.EventRepository;
import ru.antonbelous.eventmanagement.repository.inmemory.InMemoryEventRepository;
import ru.antonbelous.eventmanagement.util.EventUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.antonbelous.eventmanagement.repository.inmemory.InMemoryUserRepository.USER_ID;

public class EventServlet extends HttpServlet {

    private EventRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryEventRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                repository.delete(id, USER_ID);
                response.sendRedirect("events");
                break;
            case "create":
            case "update":
                final Event event = action.equals("create") ?
                        new Event(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", Status.PLANNED) :
                        repository.get(getId(request), USER_ID);
                request.setAttribute("event", event);
                request.getRequestDispatcher("/eventForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("events", EventUtil.getTos(repository.getAll(USER_ID)));
                request.getRequestDispatcher("/events.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Event event = new Event(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Status.valueOf(request.getParameter("status")));

        repository.save(event, USER_ID);
        response.sendRedirect("events");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
