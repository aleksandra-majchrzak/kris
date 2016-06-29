<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="list-group">
    <c:forEach items="${contractors}" var="item" varStatus="loop">
        <a href="ContractorServlet?contractorIndex=${loop.index}" class="list-group-item kris-list-group-item">
            <div>
                <h4>${item.code}</h4>
                <p>${item.address}</p>
            </div>
        </a>
    </c:forEach>
</ul>
