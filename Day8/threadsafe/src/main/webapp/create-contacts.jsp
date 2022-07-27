<%--
  Created by IntelliJ IDEA.
  User: melih
  Date: 7/27/2022
  Time: 1:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="contacts-servlet" method="post">
    <label for="name">Name</label><br>
    <input type="text" id="name" name="name"><br>

    <label for="tel_number">Tel Number</label><br>
    <input type="text" id="tel_number" name="tel_number"><br>

    <br>
    <input type="submit" value="Create Contact">
</form>
</body>
</html>
