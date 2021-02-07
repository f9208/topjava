<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Список еды</title>
</head>
<body>
<h1>Meals</h1>
<br>
<a>AddMeal</a>
<br>
<br>
<table border="1" cellpadding="5">
    <col width="150" align="center">
    <col width="250" align="left">
    <col width="80" align="left">
    <col width="50" align="left" span="2">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="mealTo" items="${list}">
        <tr style="color:
        ${mealTo.excess ? 'red' : 'green'}">
            <td><fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime }"/></td>
            <td> ${mealTo.description}</td>
            <td> ${mealTo.calories}</td>
            <td><a>Update</a></td>
            <td><a>Delete</a></td>
        </tr>
    </c:forEach>
</table>
<br><br>
<a href="index.html"> домой </a>
</body>
</html>
