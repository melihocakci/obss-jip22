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
    <title>Home</title>
</head>
<%
    Boolean loggedIn = (Boolean) session.getAttribute("isUserLoggedIn");
    if (loggedIn == null || !loggedIn) {
        response.sendRedirect(request.getContextPath() + "/public/login.jsp");
    }
%>
<body>
<h1>Home</h1>
<a href="dashboard.jsp">Dashboard</a><br>
</body>
</html>
