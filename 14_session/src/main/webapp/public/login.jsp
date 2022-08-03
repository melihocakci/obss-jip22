<%--
  Created by IntelliJ IDEA.
  User: melih
  Date: 7/28/2022
  Time: 11:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="<%=request.getContextPath()%>/login-servlet?redirect=<%=request.getParameter("redirect")%>"
      method="post">
    <label for="username">Username</label><br>
    <input type="text" id="username" name="username"><br>

    <label for="password">Password</label><br>
    <input type="password" id="password" name="password"><br><br>

    <input type="submit" value="Submit">
</form>
</body>
</html>
