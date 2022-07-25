<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;
											charset=UTF-8">
    <title>Hello</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<% String val = request.getParameter("uname"); %>
<h1>Hello <%=val%>
</h1>
</body>
</html>
