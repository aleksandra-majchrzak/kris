<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
       <!--  <li role="presentation" id="contractors-tab"><a href="${pageContext.request.contextPath}/contractors.jsp">Kontrahenci</a></li> -->
        <li role="presentation" id="contractors-tab"><a href="ContractorServlet">Kontrahenci</a></li>
        <li role="presentation" id="documents-tab"><a href="DocumentServlet">Dokumenty</a></li>
        <li role="presentation" id="items-tab"><a href="ItemServlet">Towary</a></li>
        <li role="presentation" id="payments-tab"><a href="PaymentServlet">Platnosci</a></li>
        <li role="presentation" id="warehouses-tab"><a href="WarehouseServlet">Magazyny</a></li>
    </ul>
</div>