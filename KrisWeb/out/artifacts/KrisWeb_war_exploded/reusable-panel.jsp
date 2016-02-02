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
            <button type="button" class="btn btn-default add-new-button" aria-label="Left Align">
                <a href="#">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                </a>
            </button>
        </h1>

    </div>
    <div class="panel-body">
        <div class="col-lg-8 ">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Search for...">
                     <span class="input-group-btn">
                        <button class="btn btn-default" type="button">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                     </span>
            </div><!-- /input-group -->
        </div>
    </div>

    <!-- List group -->
    <c:choose>
        <c:when test = "${contractors != null}">
            <jsp:include page="contractors-list.jsp"  flush="true" />
        </c:when>
        <c:when test = "${documents != null}">
            <jsp:include page="documents-list.jsp"  flush="true" />
        </c:when>
        <c:when test = "${items != null}">
            <jsp:include page="items-list.jsp"  flush="true" />
        </c:when>
        <c:when test = "${payments != null}">
            <jsp:include page="payments-list.jsp"  flush="true" />
        </c:when>
        <c:when test = "${warehouses != null}">
            <jsp:include page="warehouses-list.jsp"  flush="true" />
        </c:when>
    </c:choose>

</div>