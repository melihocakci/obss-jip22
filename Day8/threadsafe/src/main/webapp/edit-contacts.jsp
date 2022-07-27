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
<form action="edit-contact" method="post">
    <label for="id">id</label><br>
    <input type="text" id="id" name="id" value="<%=request.getParameter("id")%>"><br>

    <label for="name">Name</label><br>
    <input type="text" id="name" name="name" value="<%=request.getParameter("name")%>"><br>

    <label for="tel_number">Tel Number</label><br>
    <input type="text" id="tel_number" name="tel_number" value="<%=request.getParameter("tel_number")%>"><br>

    <br>
    <input type="submit" value="Edit Contact">
</form>
<%=request.getAttribute("results")%>
</body>
</html>
