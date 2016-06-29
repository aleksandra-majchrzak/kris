<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <c:set var="item" scope="page" value="${itemToEdit}"/>
    <c:set var="itemId" scope="page" value="${item != null ? item.id : null}"/>

    <div class="panel-heading kris-panel-heading">
        <h1>
            <c:choose>
                <c:when test="${item == null}">
                    Nowy towar
                </c:when>
                <c:otherwise>
                    Edytuj towar
                </c:otherwise>
            </c:choose>
        </h1>
    </div>
    <div class="panel-body kris-panel-body">
        <div class=" col-lg-12 list-group item-details-list-group">
            <form role="form" action="ItemServlet" method="post">
                <div class="form-group">
                    <h4>Kod towaru</h4>
                    <c:choose>
                        <c:when test="${item != null}">
                            <input type="text" name="code" placeholder="Kod" class="form-control" value="${item.code}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="code" placeholder="Kod" class="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <h4>Nazwa towaru</h4>
                    <c:choose>
                        <c:when test="${item != null}">
                            <input type="text" name="name" placeholder="Nazwa" class="form-control" value="${item.name}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="name" placeholder="Nazwa" class="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <h4>Rodzaj towaru</h4>

                    <div class="row">

                            <div class="input-group">
                                <c:choose>
                                <c:when test="${item != null}">
                                    <input id="item-type" type="text" name="type" class="form-control" value="${item.type}">
                                </c:when>
                                    <c:otherwise>
                                        <input id="item-type" type="text" name="type" class="form-control">
                                    </c:otherwise>
                                </c:choose>
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
                                    <ul id="item-type-dropdown-menu" class="dropdown-menu dropdown-menu-right" role="menu">
                                        <c:forEach items="${types}" var="type" varStatus="loop">
                                            <li  class="type-input-lg"><a href="#">${type}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>

                    </div>

                </div>
                <div class="form-group">
                    <h4>Cena netto</h4>
                    <c:choose>
                        <c:when test="${item != null}">
                            <input id = "item-panel-net-price" type="number" step="0.01" name="netPrice" placeholder="CenaNetto" class="form-control" value="${item.price.netPrice}"/>
                        </c:when>
                        <c:otherwise>
                            <input id = "item-panel-net-price" type="number" step="0.01" name="netPrice" placeholder="CenaNetto" class="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <h4>Cena brutto</h4>
                    <c:choose>
                        <c:when test="${item != null}">
                            <input id = "item-panel-gross-price" type="number" step="0.01" name="grossPrice" placeholder="CenaBrutto" class="form-control" value="${item.price.grossPrice}"/>
                        </c:when>
                        <c:otherwise>
                            <input id = "item-panel-gross-price" type="number" step="0.01" name="grossPrice" placeholder="CenaBrutto" class="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <h4>Rozmiar</h4>

                    <div class="row">

                            <div class="input-group">
                                <c:choose>
                                    <c:when test="${item != null}">
                                        <input id="item-size" type="text" name="size" placeholder="Rozmiar" class="form-control" value="${item.size}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input id="item-size" type="text" name="size" placeholder="Rozmiar" class="form-control"/>
                                    </c:otherwise>
                                </c:choose>
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
                                    <ul id="item-size-dropdown-menu" class="dropdown-menu dropdown-menu-right" role="menu">
                                        <c:forEach items="${sizes}" var="size" varStatus="loop">
                                            <li  class="size-input-lg"><a href="#">${size}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>

                    </div>

                </div>
                <div class="form-group">
                    <h4>Material</h4>

                    <div class="row">

                            <div class="input-group">
                                <c:choose>
                                    <c:when test="${item != null}">
                                        <input id="item-material" type="text" name="material" placeholder="Material" class="form-control" value="${item.material}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input id="item-material" type="text" name="material" placeholder="Material" class="form-control"/>
                                    </c:otherwise>
                                </c:choose>
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
                                    <ul id="item-material-dropdown-menu" class="dropdown-menu dropdown-menu-right" role="menu">
                                        <c:forEach items="${materials}" var="material" varStatus="loop">
                                            <li  class="material-input-lg"><a href="#">${material}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>

                    </div>

                </div>
                <!--
                <div class="form-group">
                    <h4>Zasoby</h4>
                    <c:choose>
                        <c:when test="${item != null}">
                            <c:set var="allStocks" value="${0}"/>
                            <c:forEach items="${item.itemStocks}" var="itemStock" varStatus="loop">
                                <c:set var="allStocks"  value="${allStocks + itemStock.quantity}"/>
                            </c:forEach>
                            <input  type="number" step="1" name="stocks" placeholder="Zasoby" class="form-control" value="${allStocks}"/>
                        </c:when>
                        <c:otherwise>
                            <input  type="number" step="1" name="stocks" placeholder="Zasoby" class="form-control" />
                        </c:otherwise>
                    </c:choose>
                </div>
                -->
                <div class="form-group">
                    <h4>Opis</h4>
                    <c:choose>
                        <c:when test="${item != null}">
                            <input type="text" name="description" placeholder="Opis" class="form-control" value="${item.description}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="description" placeholder="Opis" class="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <input type="hidden" name="itemId" id="itemId" value="${item.id}" />
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