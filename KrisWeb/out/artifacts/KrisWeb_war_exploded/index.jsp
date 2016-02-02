<%--
  Created by IntelliJ IDEA.
  User: Mohru
  Date: 2016-01-24
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <h1>Witaj w aplikacji KrisWeb!</h1>
            <c:if test="${user == null}">
                <h4>Zarejestruj sie, by poznac wszystkie sekrety naszej aplikacji.</h4>
            </c:if>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>
