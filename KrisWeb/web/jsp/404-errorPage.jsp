<%--
  Created by IntelliJ IDEA.
  User: Mohru
  Date: 2016-01-24
  Time: 21:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Kris Web</title>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js'></script>
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="/styles.css">
</head>
<body>
<jsp:include page="navbar.jsp" />
<div class="container">
    <div class="jumbotron">
        <h1>Błąd 404</h1>
        <h4>Niestety strona o podanym adresie nie istnieje :( Spróbuj wrócic na poprzednią stronę.</h4>
    </div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
