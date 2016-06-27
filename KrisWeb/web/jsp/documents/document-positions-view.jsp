<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="list-group">
    <c:forEach items="${document.positionsList}" var="position" varStatus="loop">
        <a href="DocumentServlet?documentIndex=${loop.index}" class="list-group-item kris-list-group-item">
            <div>
                <h4>${position.item.name}</h4> <!-- to musze ftormatowac ladnie jako date -->
            </div>
        </a>
    </c:forEach>
</ul>
