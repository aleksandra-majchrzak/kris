<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="addNewDocument" scope="page" value="${addNewDocument}"/>
<c:if test="${addNewDocument != null && addNewDocument}">
    <c:set var="document" scope="page" value="${documentToEdit}"/>
</c:if>

<ul class="list-group">
    <c:forEach items="${document.positionsList}" var="position" varStatus="loop">
        <a  class="list-group-item kris-list-group-item position-row">
            <div>
                <h4>${position.item.name}</h4>
                <div id="document-position-info">
                    <h6>${position.quantity} szt. <span> </span> <span> </span> ${position.netValue} (N) <span> </span> ${position.grossValue} (B)</h6>
                </div>
            </div>
        </a>

    </c:forEach>
</ul>
