<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
    <title>Список еды</title>
</head>
<body>
<h1>Meals</h1>
<br>
<a href="addMeal.jsp">AddMeal</a>
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
        <tr style="color: ${mealTo.excess ? 'red' : 'green'}">
            <td><fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/></td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?action=edit&mealId=<c:out value="${mealTo.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete</a></td>
            </td>
        </tr>
    </c:forEach>
</table>

<br><br>
<a href="index.html"> на главную</a>
</body>
</html>