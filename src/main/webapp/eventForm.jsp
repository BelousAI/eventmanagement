<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.antonbelous.eventmanagement.model.Status" %>

<html>
<head>
    <title>Event</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create event' : 'Edit event'}</h2>
    <jsp:useBean id="event" type="ru.antonbelous.eventmanagement.model.Event" scope="request"/>
    <form method="post" action="events">
        <input type="hidden" name="id" value="${event.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${event.startDateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${event.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt>Status:</dt>
            <dd>
                <select id="status" name="status">
                    <c:forEach items="${Status.values()}" var="statusVar">
                        <option value="${statusVar}" ${event.currentStatus == statusVar ? 'selected' : ''}>${statusVar}</option>
                    </c:forEach>
                </select>
            </dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
