<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <div class="panel-heading kris-panel-heading">
        <h1>
            ${contractor.code}

                <c:set var="isNew" scope="page" value="${isNew}"/>
                <c:choose>
                    <c:when test="${isNew != null && isNew}">
                        <button type="button" class="btn btn-default accept-button" aria-label="Left Align">
                            <a href="#">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </a>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-default edit-button" aria-label="Left Align">
                            <a href="#">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                            </a>
                        </button>
                        <button type="button" class="btn btn-default delete-button" aria-label="Left Align">
                            <a href="#">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                            </a>
                        </button>
                    </c:otherwise>
                </c:choose>
        </h1>
    </div>
    <div class="panel-body kris-panel-body">
        <div class=" col-lg-12">
            <ul class="list-group details-list-group">
                <li class="list-group-item kris-list-group-item">
                    <h4>Typ kontrahenta</h4>
                    <p>${contractor.type.name}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Adres</h4>
                    <p>${contractor.address}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>NIP</h4>
                    <p>${contractor.NIP}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Opis</h4>
                    <p>${contractor.description}</p>
                </li>
            </ul>
        </div>
    </div>

</div>