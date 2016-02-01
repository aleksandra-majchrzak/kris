<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="list-group">
    <c:forEach items="${contractors}" var="contractor" varStatus="loop">
        <a href="ContractorServlet?contractorIndex=${loop.index}" class="list-group-item kris-list-group-item">
            <div>
                <h4>${contractor.code}</h4>
                <p>${contractor.address}</p>
            </div>
        </a>
    </c:forEach>
</ul>
