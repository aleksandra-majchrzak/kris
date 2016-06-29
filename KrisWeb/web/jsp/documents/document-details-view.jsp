<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <div class="panel-heading kris-panel-heading">
        <h1>
            ${document.number}

                <div class="edit-buttons">
                    <!--  <a href="DocumentServlet?documentToEditIndex=${documentIndex}">
                        <button type="button" class="btn btn-default edit-button" aria-label="Left Align">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
                    </a>
             -->      <form role="form" action="DocumentServlet" method="post" class="delete-button">
                        <input type="hidden" name="documentToDeleteIndex" id="documentToDeleteIndex" value="${documentIndex}" />
                        <button type="submit" class="btn btn-default delete-button" aria-label="Left Align">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                        </button>

                    </form>

                </div>
        </h1>
    </div>
    <div class="panel-body kris-panel-body">
        <div  id ="kris-panel-document-tabs" class=" col-lg-12">
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="#document-tab-positions">Pozycje</a></li>
                <li role="presentation" ><a href="#document-tab-header">Nagłówek</a></li>
            </ul>

            <div id="document-tab-positions" class="visible">
                <jsp:include page="/documents/document-positions-view.jsp"  flush="true" />
            </div>

            <div id="document-tab-header" class="gone">
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
                        <p>${document.paymentForm.name}</p>
                    </li>
                    <li class="list-group-item kris-list-group-item">
                        <h4>Wartość netto</h4>
                        <p>${document.netValue}</p>
                    </li>
                    <li class="list-group-item kris-list-group-item">
                        <h4>Wartość brutto</h4>
                        <p>${document.grossValue}</p>
                    </li>
                    <li class="list-group-item kris-list-group-item">
                        <h4>Opis</h4>
                        <p>${document.description}</p>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>