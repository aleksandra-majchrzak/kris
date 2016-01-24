<%--
  Created by IntelliJ IDEA.
  User: Mohru
  Date: 2016-01-24
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kris Web</title>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js'></script>
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<jsp:include page="navbar.jsp" />
<script type="text/javascript"> $('#register-tab').addClass("active") </script>
<div class="container">
    <h1>Rejestracja</h1>
    <form role="form" action="RegisterServlet" method="post" style="max-width: 300px;">
        <div class="form-group">
            <input type="text" name="username" placeholder="Wpisz login" class="form-control"/>
        </div>
        <div class="form-group">
            <input type="password" name="password" placeholder="Hasło" class="form-control"/>
        </div>
        <div class="form-group">
            <input type="password" name="password_confirmation" placeholder="Powtórz hasło" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-default">Zatwierdź</button>&nbsp;<a href="${pageContext.request.contextPath}/">
        <button type="button" class="btn btn-primary">Anuluj</button></a>
    </form>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
