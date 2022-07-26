<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Image</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<a href="<%=request.getContextPath()%>/image?filename=possum.png">
    <img src="img/possum.png" alt="possum" width="300">
</a>
<a href="<%=request.getContextPath()%>/image?filename=monke.png">
    <img src="img/monke.png" alt="possum" width="300">
</a>
<a href="<%=request.getContextPath()%>/image?filename=becel.jpg">
    <img src="img/becel.jpg" alt="possum" width="300">
</a>
</body>
</html>
