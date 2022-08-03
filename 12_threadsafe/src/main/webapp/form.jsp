<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Amount</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/hello-servlet">
    <label for="amount">Amount</label><br>
    <input type="number" id="amount" name="amount"><br><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>