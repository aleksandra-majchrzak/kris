<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-4 kris-panel">
    <!-- Default panel contents -->

    <c:set var="contractors" scope="page" value="${contractors}"/>
    <c:set var="documents" scope="page" value="${documents}"/>
    <c:set var="items" scope="page" value="${items}"/>
    <c:set var="payments" scope="page" value="${payments}"/>
    <c:set var="warehouses" scope="page" value="${warehouses}"/>

    <div class="panel-heading kris-panel-heading">
        <h1>
            <%= request.getAttribute("panel-name") %>
            <c:choose>
                <c:when test = "${contractors != null}">
                    <jsp:include page="buttons/add-contractor-button.jsp" />
                </c:when>
                <c:when test = "${documents != null}">
                    <jsp:include page="buttons/add-document-button.jsp" />
                </c:when>
                <c:when test = "${items != null}">
                    <jsp:include page="buttons/add-item-button.jsp" />
                </c:when>
            </c:choose>
        </h1>

    </div>
    <%--
    <div class="panel-body">
        <div class="col-lg-8 ">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Search for...">
                     <span class="input-group-btn">
                        <button class="btn btn-default" type="button">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                     </span>
            </div>
        </div>
    </div>--%>

    <!-- List group -->
    <c:choose>
        <c:when test = "${contractors != null}">
            <jsp:include page="contractors/contractors-list.jsp"  flush="true" />
        </c:when>
        <c:when test = "${documents != null}">
            <jsp:include page="documents/documents-list.jsp"  flush="true" />
        </c:when>
        <c:when test = "${items != null}">
            <jsp:include page="items/items-list.jsp"  flush="true" />
        </c:when>
        <c:when test = "${payments != null}">
            <jsp:include page="payments/payments-list.jsp"  flush="true" />
        </c:when>
        <c:when test = "${warehouses != null}">
            <jsp:include page="warehouses/warehouses-list.jsp"  flush="true" />
        </c:when>
    </c:choose>

</div>