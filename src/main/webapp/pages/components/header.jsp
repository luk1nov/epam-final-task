<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="app-header" style="width: 100%;">
    <nav class="navbar navbar-light navbar-expand-lg">
        <div class="container-fluid">
            <div class="navbar-nav" id="navbarNav">
                <a href="${pageContext.request.contextPath}/controller?command=default" class="logo" style="text-decoration: none">Car Rental</a>
            </div>
            <div class="d-flex">
                <ul class="navbar-nav">
                    <li class="nav-item hidden-on-mobile">
                        <form action="/controller" method="POST">
                            <input type="hidden" name="carCategory" value="1">
                            <input type="hidden" name="command" value="to_car_category_page">
                            <input type="submit" value="Cars" class="nav-link">
                        </form>
                    </li>
                    <li class="nav-item hidden-on-mobile">
                        <form action="/controller" method="POST">
                            <input type="hidden" name="carCategory" value="2">
                            <input type="hidden" name="command" value="to_car_category_page">
                            <input type="submit" value="Cars" class="nav-link">
                        </form>
                    </li>
                    <li class="nav-item hidden-on-mobile">
                        <form action="/controller" method="POST">
                            <input type="hidden" name="carCategory" value="3">
                            <input type="hidden" name="command" value="to_car_category_page">
                            <input type="submit" value="Cars" class="nav-link">
                        </form>
                    </li>
                </ul>
            </div>
            <div class="d-flex">
                <ul class="navbar-nav">
                    <c:if test="${sessionScope.loggedUser.role eq 'ADMIN' or sessionScope.loggedUser.role eq 'MANAGER'}" >
                        <li class="nav-item hidden-on-mobile" style="align-self: center;padding: 0 10px;">
                            <form action="/controller" method="POST">
                                <input type="hidden" name="command" value="admin_to_all_users">
                                <input type="submit" class="btn btn-outline-info" value="Admin panel">
                            </form>
                        </li>
                    </c:if>
                    <c:if test="${empty sessionScope.loggedUser}" >
                        <li class="nav-item hidden-on-mobile">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=to_signup_page">Sign Up</a>
                        </li>
                        <li class="nav-item hidden-on-mobile">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=to_signin_page">Log In</a>
                        </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.loggedUser}" >
                        <li class="nav-item hidden-on-mobile">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=log_out">Log Out</a>
                        </li>
                    </c:if>
                    <li class="nav-item hidden-on-mobile">
                        <a class="nav-link language-dropdown-toggle" href="#" id="languageDropDown" data-bs-toggle="dropdown"><img src="${pageContext.request.contextPath}/resources/images/flags/us.png" alt=""></a>
                        <ul class="dropdown-menu dropdown-menu-end language-dropdown" aria-labelledby="languageDropDown">
                            <li><a class="dropdown-item" href="#"><img src="${pageContext.request.contextPath}/resources/images/flags/russia.png" alt="">Russian</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>