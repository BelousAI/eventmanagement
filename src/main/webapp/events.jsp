<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://eventmanagement.antonbelous.ru/functions" %>
<html>
<head>
    <title>Events</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Events</h2>
    <form method="get" action="events">
        <input type="hidden" name="action" value="filter">
        <dl>
            <dt>From Date:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt>From Time:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt>To Time:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
    <hr/>
    <a href="events?action=create">Add Event</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Status</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${events}" var="event">
            <jsp:useBean id="event" type="ru.antonbelous.eventmanagement.to.EventTo"/>
            <tr data-eventStatus="${event.currentStatus.name()}">
                <td>
<%--                        ${event.startDateTime.toLocalDate()} ${event.startDateTime.toLocalTime()}--%>
                        ${fn:formatDateTime(event.startDateTime)}
                </td>
                <td>${event.description}</td>
                <td>${event.currentStatus.name()}</td>
                <td><a href="events?action=update&id=${event.id}">Update</a></td>
                <td><a href="events?action=delete&id=${event.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
