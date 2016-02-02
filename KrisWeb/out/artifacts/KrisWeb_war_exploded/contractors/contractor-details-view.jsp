<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <div class="panel-heading kris-panel-heading">
        <h1>
            ${contractor.code}
                <div class="edit-buttons">
                    <a href="ContractorServlet?contractorToEditIndex=${contractorIndex}">
                        <button type="button" class="btn btn-default edit-button" aria-label="Left Align">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
                    </a>
                    <form role="form" action="ContractorServlet" method="post" class="delete-button">
                        <input type="hidden" name="contractorToDeleteIndex" id="contractorToDeleteIndex" value="${contractorIndex}" />
                        <button type="submit" class="btn btn-default delete-button" aria-label="Left Align">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                        </button>
                    </form>

                </div>

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