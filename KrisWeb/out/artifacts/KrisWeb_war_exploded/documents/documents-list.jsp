<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="list-group">
    <c:forEach items="${documents}" var="document" varStatus="loop">
        <a href="DocumentServlet?documentIndex=${loop.index}" class="list-group-item kris-list-group-item">
            <div>
                <h4>${document.number}</h4>
                <p>${document.documentDate}</p>
            </div>
        </a>
    </c:forEach>
</ul>
