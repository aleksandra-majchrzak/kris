<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default col-lg-6 col-md-offset-1 details-panel" >
    <!-- Default panel contents -->
    <div class="panel-heading kris-panel-heading">
        <h1>${contractor.code}</h1>
    </div>
    <div class="panel-body kris-panel-body">
        <div class="col-lg-6">
            <h5>Typ kontrahenta</h5>
            <p>${contractor.type.name}</p>
            <h5>Adres</h5>
            <p>${contractor.address}</p>
            <h5>NIP</h5>
            <p>${contractor.NIP}</p>
            <h5>Opis</h5>
            <p>${contractor.description}</p>
        </div>
    </div>

</div>