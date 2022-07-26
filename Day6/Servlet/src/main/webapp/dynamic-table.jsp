<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dynamic Table</title>
    <link rel="stylesheet" href="style.css">
</head>
<%
    ArrayList list = (ArrayList) request.getAttribute("list");
    StringBuilder table = new StringBuilder();
    for (int i = 0; i < 3; i++) {
        table.append("<tr>\n");
        for (int j = 0; j < 3; j++) {
            table.append("<td>").append(list.get(j + i * 3)).append("</td>\n");
        }
        table.append("</tr>");
    }
%>
<body>
<table border="2">
<%=table.toString()%>
</table>
</body>
</html>
