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

<form action="contacts-servlet" method="get">
    <label for="name">Find contact by name</label><br>
    <input type="text" id="name" name="name"><br>

    <br>
    <input type="submit" value="Find Contact">
</form>

<%=request.getAttribute("table")%>
</body>
</html>
