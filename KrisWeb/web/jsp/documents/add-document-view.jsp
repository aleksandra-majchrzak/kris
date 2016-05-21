<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <c:set var="document" scope="page" value="${documentToEdit}"/>
    <c:set var="documentId" scope="page" value="${document != null ? document.id : null}"/>
    <c:set var="contractorsDoc" scope="page" value="${contractorsDoc}"/>

    <div class="panel-heading kris-panel-heading">
        <h1>
            <c:choose>
                <c:when test="${document == null}">
                    Nowy dokument
                </c:when>
                <c:otherwise>
                    Edytuj dokument
                </c:otherwise>
            </c:choose>
        </h1>
    </div>
    <div class="panel-body kris-panel-body">
        <div class=" col-lg-12">
            <form role="form" action="DocumentServlet" method="post" >
                <div class="list-group document-details-list-group">
                    <div class="form-group">
                        <h4>Numer dokumentu</h4>
                        <input type="datetime" name="number" placeholder="Numer dokumentu" class="form-control" value="${document.number}"/>
                    </div>
                    <div class="form-group">
                        <h4>Typ dokumentu</h4>
                        <select name="docType" id="docType">
                            <option value="0" selected>Zamowienie sprzedazy</option>
                            <option value="1">Faktura sprzedazy</option>
                            <option value="2">Wydanie magazynowe</option>
                        </select>
                        <c:if test="${document != null}">
                            <script type="text/javascript"> $('#docType').val('${document.type.value}') </script>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <h4>Kontrahent</h4>
                        <select name="contractorDocId" id="contractorDocId">
                            <c:forEach items="${contractorsDoc}" var="contractor" varStatus="loop">
                                <c:choose>
                                    <c:when test="${contractor.id == document.contractor.id}">
                                        <option value="${contractor.id}" selected>${contractor.code}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${contractor.id}">${contractor.code}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>

                        <c:if test="${document == null}">
                            <script type="text/javascript"> $('#contractorDocId').val('${contractorsDoc.get(0).id}') </script>
                        </c:if>

                    </div>
          <%--          <div class="form-group">
                        <h4>Data wystawienia</h4>
                        <input type="datetime" name="documentDate" placeholder="Data wystawienia" class="form-control" value="${document.documentDate}"/>
                    </div>
                    <div class="form-group">
                        <h4>Data płatności</h4>
                        <input type="datetime" name="paymentDate" placeholder="Data płatności" class="form-control" value="${document.paymentDate}"/>
                    </div>
            --%>        <div class="form-group">
                        <h4>Forma płatności</h4>
                        <select name="paymentForm" id="paymentForm">
                            <option value="0" selected>Gotowka</option>
                            <option value="1">Przelew</option>
                            <option value="1">Pobranie</option>
                            <option value="2">Karta</option>
                        </select>
                        <c:if test="${document != null}">
                            <script type="text/javascript"> $('#paymentForm').val('${document.paymentForm.value}') </script>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <h4>Wartość netto</h4>
                        <input type="number" step="0.01" name="netValue" placeholder="Wartosc" class="form-control" value="${document.netValue}"/>
                        <h4>Wartość brutto</h4>
                        <input type="number" step="0.01" name="grossValue" placeholder="Wartosc" class="form-control" value="${document.grossValue}"/>
                    </div>
                    <div class="form-group">
                        <h4>Opis</h4>
                        <input type="text" name="description" placeholder="Kod" class="form-control" value="${document.description}"/>
                    </div>
                </div>
                <div class="form-group">
                    <input type="hidden" name="documentId" id="documentId" value="${document.id}" />
                </div>
                <a href="#">
                    <button type="submit" class="btn btn-default accept-button" aria-label="Left Align">
                        <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                    </button>
                </a>
            </form>
        </div>
    </div>

</div>