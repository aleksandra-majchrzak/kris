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
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="/styles.css">
    <script src="<c:url value="/item-panel.js" />"></script>
</head>
<body>
<jsp:include page="../navbar.jsp" />
<script type="text/javascript"> $('#items-tab').addClass("active") </script>
<div class="container">
    <jsp:include page="../reusable-panel.jsp"   flush="true" />    <!-- w jakis posob przekazywac parametry z jednej strony do drugiej?? servletami?? -->
    <!-- if towar wybrany-->
    <c:set var="item" scope="page" value="${item}"/>
    <c:if test="${item != null}">
        <jsp:include page="item-details-view.jsp" flush="true" />
    </c:if>

    <c:set var="addNewItem" scope="page" value="${addNewItem}"/>
    <c:if test="${addNewItem != null}">
        <jsp:include page="add-item-view.jsp" flush="true" />
    </c:if>
</div>
<jsp:include page="../footer.jsp" />
</body>
</html>
