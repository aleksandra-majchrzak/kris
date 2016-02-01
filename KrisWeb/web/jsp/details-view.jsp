<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <div class="panel-heading kris-panel-heading">
        <h1>${contractor.code}</h1>
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