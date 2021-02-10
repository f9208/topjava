<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>AddMeal</title>
</head>
<body>
<h1>${action} your meal here</h1>
<br>
<form method="POST" action="meals">
    <label for="date"> DateTime</label>
    <input type="datetime-local" id="date" name="date" value="<c:out value="${meal.dateTime}"/>"/>
    <br><br>
    <label for="descriptionMeal">Description: </label>
    <input type="text" id="descriptionMeal" name="description" value="
<c:out value="${meal.description}"/>"/>
    <br><br>
    <label for="calories" style="width: 100px">calories: </label>
    <input type="number" id="calories" name="calories" value="<c:out value="${meal.calories}"/>"/>
    <br><br>
    <input type="submit" value="save" name="saveButton">
    <input type="hidden" value="<c:out value="${meal.id}"/>" name="mealId">
</form>
<form method="GET" action="meals">
    <input type="submit" name="cancelButton" value="Cancel">
</form>
</body>
</html>
