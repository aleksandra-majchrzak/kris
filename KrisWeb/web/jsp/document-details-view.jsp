<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <div class="panel-heading kris-panel-heading">
        <h1>
            ${document.number}

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
                </c:otherwise>
            </c:choose>
        </h1>
    </div>
    <div class="panel-body kris-panel-body">
        <div class=" col-lg-12">
            <ul class="list-group document-details-list-group">
                <li class="list-group-item kris-list-group-item">
                    <h4>Typ dokumentu</h4>
                    <p>${document.type.name}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Kontrahent</h4>
                    <p>${document.contractor.code}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Data wystawienia</h4>
                    <p>${document.documentDate}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Data płatności</h4>
                    <p>${document.paymentDate}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Forma płatności</h4>
                    <p>${document.paymentDate}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Wartość</h4>
                    <p>${document.value}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Opis</h4>
                    <p>${document.description}</p>
                </li>
            </ul>
        </div>
    </div>

</div>