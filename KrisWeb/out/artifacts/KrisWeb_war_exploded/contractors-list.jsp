<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="list-group">
    <c:forEach items="${contractors}" var="contractor">
        <a href="#" class="list-group-item">
            <div>
                <h4>${contractor.code}</h4>
                <p>${contractor.address}</p>
            </div>
        </a>
    </c:forEach>
</ul>
