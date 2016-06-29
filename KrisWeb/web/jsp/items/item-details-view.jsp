<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>

<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <div class="panel-heading kris-panel-heading">
        <h1>
            ${item.code}
            <div class="edit-buttons">
                <a href="ItemServlet?itemToEditIndex=${itemIndex}">
                    <button type="button" class="btn btn-default edit-button" aria-label="Left Align">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    </button>
                </a>
                <form role="form" action="ItemServlet" method="post" class="delete-button">
                    <input type="hidden" name="itemToDeleteIndex" id="itemToDeleteIndex" value="${itemIndex}" />
                    <button type="submit" class="btn btn-default delete-button" aria-label="Left Align">
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </button>
                </form>

            </div>

        </h1>
    </div>
    <div class="panel-body kris-panel-body">
        <div class=" col-lg-12">
            <ul class="list-group item-details-list-group">
                <li class="list-group-item kris-list-group-item">
                    <h4>Nazwa towaru</h4>
                    <p>${item.name}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Rodzaj towaru</h4>
                    <p>${item.type}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Cena netto</h4>
                    <p>${item.price.netPrice}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Cena brutto</h4>
                    <p>${item.price.grossPrice}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Rozmiar</h4>
                    <p>${item.size}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Material</h4>
                    <p>${item.material}</p>
                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Zasoby</h4>
                    <c:choose>
                        <c:when test="${empty item.itemStocks}">
                            <p>Brak</p>
                        </c:when>
                        <c:otherwise>
                            <c:set var="allStocks" value="${0}"/>
                            <c:forEach items="${item.itemStocks}" var="itemStock" varStatus="loop">
                                <c:set var="allStocks"  value="${allStocks + itemStock.quantity}"/>
                            </c:forEach>
                            <p>${allStocks}</p>
                        </c:otherwise>
                    </c:choose>

                </li>
                <li class="list-group-item kris-list-group-item">
                    <h4>Opis</h4>
                    <p>${item.description}</p>
                </li>
            </ul>
        </div>
    </div>

</div>