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
<a href="addMeal">AddMeal</a>
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
        <form method="POST" action="meals" accept-charset="UTF-8">
            <tr style="color: ${mealTo.getExcess() ? 'red' : 'green'}">
                <input type="hidden" value="${mealTo.getDateTime()}" name="date">
                <td><fmt:parseDate value="${mealTo.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/></td>
                <td><input type="hidden" value="${mealTo.getDescription()}" name="description">
                        ${mealTo.getDescription()}</td>
                <td><input type="hidden" value="${mealTo.getCalories()}" name="calories">
                        ${mealTo.getCalories()}</td>
                <td><input type="hidden" name="update_meal_id" value="${mealTo.getId()}"/>
                    <input type="submit" value="обновить" name="update_meal">
                </td>
                <td><input type="hidden" name="delete_meal_id" value="${mealTo.getId()}"/>
                    <input type="submit" value="delete" name="delete">
                </td>
            </tr>
        </form>

    </c:forEach>
</table>

<br><br>
<a href="index.html"> на главную</a>
</body>
</html>