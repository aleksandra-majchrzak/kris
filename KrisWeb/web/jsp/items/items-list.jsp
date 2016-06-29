<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="list-group">
    <c:forEach items="${items}" var="item" varStatus="loop">
        <a href="ItemServlet?itemIndex=${loop.index}" class="list-group-item kris-list-group-item">
            <div>
                <h4>${item.code}</h4>
                <h6>${item.name}</h6>
            </div>
        </a>
    </c:forEach>
</ul>
