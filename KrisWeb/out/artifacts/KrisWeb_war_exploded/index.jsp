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
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
        <h1>Simple Java Web App Demo</h1>
        <p>To invoke the java servlet click <a href="MainServlet">here</a></p>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>
