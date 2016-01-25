<div class="panel panel-default col-lg-4 kris-panel">
    <!-- Default panel contents -->
    <div class="panel-heading">
        <h1><%= request.getAttribute("panel-name") %></h1>
    </div>
    <div class="panel-body">
        <div class="col-lg-8 ">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Search for...">
                     <span class="input-group-btn">
                        <button class="btn btn-default" type="button">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                     </span>
            </div><!-- /input-group -->
        </div>
    </div>

    <!-- List group -->
    <ul class="list-group">
        <a href="#" class="list-group-item">Cras justo odio</a>
        <a href="#" class="list-group-item">Dapibus ac facilisis in</a>
        <a href="#" class="list-group-item">Morbi leo risus</a>
        <a href="#" class="list-group-item">Porta ac consectetur ac</a>
        <a href="#"class="list-group-item">Vestibulum at eros</a>
    </ul>
</div>