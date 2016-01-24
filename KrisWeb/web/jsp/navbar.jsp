<div class="main-navbar">
    <a id="main-logo" href="${pageContext.request.contextPath}/">
        <img src="logo.png">
    </a>
    <!-- jesli nie zalogowany, to wyswietlaj navbar do logowania, jesli zalogowany to komunikat o zaligowaniu -->
    <ul class="nav nav-pills navbar-right nav-menu">
        <li role="presentation" id="register-tab"><a href="${pageContext.request.contextPath}/register.jsp">Zarejestruj</a></li>
        <li role="presentation" id="login-tab"><a href="${pageContext.request.contextPath}/login.jsp">Zaloguj</a></li>
    </ul>
    <br/>
    <ul class="nav nav-pills navbar-right nav-submenu">
        <li role="presentation" id="contractors-tab"><a href="${pageContext.request.contextPath}/contractors.jsp">Kontrahenci</a></li>
        <li role="presentation" id="documents-tab"><a href="${pageContext.request.contextPath}/documents.jsp">Dokumenty</a></li>
        <li role="presentation" id="items-tab"><a href="${pageContext.request.contextPath}/items.jsp">Towary</a></li>
        <li role="presentation" id="payments-tab"><a href="${pageContext.request.contextPath}/payments.jsp">Platnosci</a></li>
        <li role="presentation" id="warehouses-tab"><a href="${pageContext.request.contextPath}/warehouses.jsp">Magazyny</a></li>
    </ul>
</div>