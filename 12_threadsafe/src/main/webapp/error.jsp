<%--
  Created by IntelliJ IDEA.
  User: melih
  Date: 7/27/2022
  Time: 11:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="error-servlet" method="get">
    <input type="submit" value="404 Error">
</form>

<form action="error-servlet" method="post">
    <input type="submit" value="500 Error">
</form>
</body>
</html>
