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
<jsp:include page="../navbar.jsp" />
<script type="text/javascript"> $('#contractors-tab').addClass("active") </script>
<div class="container">
    <jsp:include page="../reusable-panel.jsp"  flush="true" />    <!-- w jakis posob przekazywac parametry z jednej strony do drugiej?? servletami?? -->

    <c:set var="contractor" scope="page" value="${contractor}"/>
    <c:if test="${contractor != null}">
        <jsp:include page="contractor-details-view.jsp" flush="true" />
    </c:if>

    <c:set var="addNewContractor" scope="page" value="${addNewContractor}"/>
    <c:if test="${addNewContractor != null}">
        <jsp:include page="contractor-details-view.jsp" flush="true" />
    </c:if>

</div>
<jsp:include page="../footer.jsp" />
</body>
</html>
