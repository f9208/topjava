<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AddMeal</title>
</head>
<body>
<h1 style="font-size: 25px ">Add your meal here</h1>
<br>
<form method="POST" action="meals">
    <label for="date"> DateTime</label>
    <input type="datetime-local" id="date" name="date"/>
    <br><br>
    <label for="descriptionMeal">Description: </label>
    <input type="text" id="descriptionMeal" name="description"/>
    <br><br>
    <label for="calories" style="width: 100px">calories: </label>
    <input type="number" id="calories" name="calories"/>
    <br><br>
    <input type="submit" value="save" name="saveButton">
</form>
<form method="GET" action="meals">
    <button type="submit">Cancel</button>
</form>
</body>
</html>
