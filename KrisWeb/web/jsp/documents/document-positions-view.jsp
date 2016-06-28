<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="addNewDocument" scope="page" value="${addNewDocument}"/>
<c:if test="${addNewDocument != null && addNewDocument}">
    <c:set var="document" scope="page" value="${documentToEdit}"/>
</c:if>

<ul class="list-group">
    <c:forEach items="${document.positionsList}" var="position" varStatus="loop">
        <a href="#" class="list-group-item kris-list-group-item">
            <div>
                <h4>${position.item.name}</h4> <!-- to musze ftormatowac ladnie jako date -->
            </div>
        </a>
    </c:forEach>
</ul>
