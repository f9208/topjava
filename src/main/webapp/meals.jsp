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
<a href="addmeal">AddMeal</a>
<br>
<br>
<form method="GET" action="meals">
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
            ${mealTo.isExcess() ? '<tr style="color: red">' : '<tr style="color: green">'}
            <td><fmt:parseDate value="${mealTo.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${parsedDateTime }"/></td>
            <td> ${mealTo.getDescription()}</td>
            <td> ${mealTo.getCalories()}</td>
            <td>
                <button> update</button>

            </td>
            <td>
                <button>del</button>
            </td>
            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>
