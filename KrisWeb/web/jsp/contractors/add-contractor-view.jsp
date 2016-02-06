<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <c:set var="contractor" scope="page" value="${contractorToEdit}"/>
     <c:set var="contractorId" scope="page" value="${contractor != null ? contractor.id : null}"/>

    <div class="panel-heading kris-panel-heading">
        <h1>
            <c:choose>
                <c:when test="${contractor == null}">
                    Nowy kontrahent
                </c:when>
                <c:otherwise>
                    Edytuj kontrahenta
                </c:otherwise>
            </c:choose>
        </h1>
    </div>
    <div class="panel-body kris-panel-body">
        <div class=" col-lg-12">
            <form role="form" action="ContractorServlet" method="post">
                <div class="form-group">
                    <h4>Kod kontrahenta</h4>
                    <c:choose>
                        <c:when test="${contractor != null}">
                            <input type="text" name="code" placeholder="Kod" class="form-control" value="${contractor.code}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="code" placeholder="Kod" class="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <h4>Typ kontrahenta</h4>
                    <c:choose>
                        <c:when test="${contractor != null}">
                            <select name="conType" id="conType">
                                <option value="0">Dostawca</option>
                                <option value="1">Kupiec</option>
                            </select>
                            <script type="text/javascript"> $('#conType').val('${contractor.type.value}') </script>
                        </c:when>
                        <c:otherwise>
                            <select name="type" id="type">
                                <option value="0">Dostawca</option>
                                <option value="1">Kupiec</option>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <h4>Adres</h4>
                    <c:choose>
                        <c:when test="${contractor != null}">
                            <input type="text" name="address" placeholder="Adres" class="form-control" value="${contractor.address}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="address" placeholder="Adres" class="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <h4>NIP</h4>
                    <c:choose>
                        <c:when test="${contractor != null}">
                            <input type="text" name="NIP" placeholder="NIP" class="form-control" value="${contractor.NIP}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="NIP" placeholder="NIP" class="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <h4>Opis</h4>
                    <c:choose>
                        <c:when test="${contractor != null}">
                            <input type="text" name="description" placeholder="Opis" class="form-control" value="${contractor.description}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="description" placeholder="Opis" class="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <input type="hidden" name="contractorId" id="contractorId" value="${contractor.id}" />
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