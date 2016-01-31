<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-4 kris-panel">
    <!-- Default panel contents -->
    <div class="panel-heading">
        <h1><%= request.getAttribute("panel-name") %></h1>
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
    <c:set var="contractors" scope="page" value="${contractors}"/>
    <c:if test = "${contractors != null}">
        <jsp:include page="contractors-list.jsp"  flush="true" />
    </c:if>

</div>