<%--
  Created by IntelliJ IDEA.
  User: Mohru
  Date: 2016-02-02
  Time: 14:17
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
    <div class="panel panel-default col-lg-6 col-md-offset-1 admin-panel" >
        <!-- Default panel contents -->
        <div class="panel-heading kris-panel-heading">
            <h1>
                Panel administratora
            </h1>
        </div>
        <div class="panel-body kris-panel-body">
            <div class=" col-lg-12">
                <ul class="list-group list-group">
                    <c:forEach items="${users}" var="diffUser" varStatus="loop">
                        <li class="list-group-item">
                            <h4 style="display: inline-block;">${diffUser.login}</h4>
                            <div class="admin-button-container" ">
                                <c:if test="${!diffUser.isActive}">
                                    <form role="form" action="UserServlet" method="post">
                                        <div>
                                            <input type="hidden" name="userToActivateIndex" value="${loop.index}" />
                                            <button type="submit" class="btn btn-mini btn-primary">Aktywuj</button>
                                        </div>
                                    </form>
                                </c:if>
                                <c:if test="${!diffUser.login.equals(user.login)}">
                                    <form role="form" action="UserServlet" method="post">
                                        <div>
                                            <input type="hidden" name="userToDeleteIndex" value="${loop.index}" />
                                            <button type="submit" class="btn btn-mini btn-danger">Usuń</button>
                                        </div>
                                    </form>
                                </c:if>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

    </div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>