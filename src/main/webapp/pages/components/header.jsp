<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="pagecontent"/>
<div class="app-header" style="width: 100%;">
    <nav class="navbar navbar-light navbar-expand-lg">
        <div class="container-fluid">
            <div class="navbar-nav" id="navbarNav">
                <a href="${pageContext.request.contextPath}/controller?command=default" class="logo" style="text-decoration: none">Car Rental</a>
            </div>
            <div class="d-flex">
                <ul class="navbar-nav custom-nav-bar">
                    <li class="nav-item hidden-on-mobile">
                        <form action="/controller" method="POST">
                            <input type="hidden" name="command" value="to_all_categories">
                            <input type="submit" value="<fmt:message key="label.all_categories"/>" class="nav-link">
                        </form>
                    </li>
                    <li class="nav-item hidden-on-mobile">
                        <form action="/controller" method="POST">
                            <input type="hidden" name="command" value="to_all_cars">
                            <input type="submit" value="<fmt:message key="label.all_cars"/>" class="nav-link">
                        </form>
                    </li>
                </ul>
            </div>
            <div class="d-flex">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a href="#" class="nav-link"><c:out value="${applicationScope.myAttr}"/></a>
                    </li>
                    <c:if test="${empty sessionScope.loggedUser}" >
                        <li class="nav-item hidden-on-mobile">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=to_sign_up_page"><fmt:message key="label.sign_up"/></a>
                        </li>
                        <li class="nav-item hidden-on-mobile">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=to_sign_in_page"><fmt:message key="label.sign_in"/></a>
                        </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.loggedUser}" >
                        <li class="nav-item hidden-on-mobile">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=log_out"><fmt:message key="label.log_out"/></a>
                        </li>
                        <li class="nav-item hidden-on-mobile" style="align-self: center;padding: 0 10px;">
                            <form action="/controller" method="POST">
                                <input type="hidden" name="command" value="to_refill_balance">
                                <input type="submit" class="btn btn-outline-success balance-btn" value="<c:out value="$${sessionScope.loggedUser.balance}"/>">
                            </form>
                        </li>
                        <li class="nav-item hidden-on-mobile" style="align-self: center;padding: 0 10px;">
                            <form action="/controller" method="POST">
                                <input type="hidden" name="command" value="to_user_account">
                                <input type="submit" class="btn btn-outline-warning profile-btn" value="">
                            </form>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.loggedUser.role eq 'ADMIN' or sessionScope.loggedUser.role eq 'MANAGER'}" >
                        <li class="nav-item hidden-on-mobile" style="align-self: center;padding: 0 10px;">
                            <form action="/controller" method="POST">
                                <input type="hidden" name="command" value="admin_to_all_users">
                                <input type="submit" class="btn btn-outline-info admin-panel-btn" value="">
                            </form>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.locale == 'en_US'}">
                        <li class="nav-item hidden-on-mobile">
                            <a class="nav-link language-dropdown-toggle" href="#" data-bs-toggle="dropdown"><img src="${pageContext.request.contextPath}/resources/images/flags/us.png" alt=""></a>
                            <ul class="dropdown-menu dropdown-menu-end language-dropdown" aria-labelledby="languageDropDown">
                                <li>
                                    <form action="/controller" class="lang-button ru-button" method="POST">
                                        <input type="hidden" name="language" value="ru_RU">
                                        <input type="hidden" name="command" value="change_locale">
                                        <input class="dropdown-item" type="submit" value="Русский" class="nav-link">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.locale == 'ru_RU'}">
                        <li class="nav-item hidden-on-mobile">
                            <a class="nav-link language-dropdown-toggle" href="#" data-bs-toggle="dropdown"><img src="${pageContext.request.contextPath}/resources/images/flags/russia.png" alt=""></a>
                            <ul class="dropdown-menu dropdown-menu-end language-dropdown" aria-labelledby="languageDropDown">
                                <li>
                                    <form action="/controller" class="lang-button eng-button" method="POST">
                                        <input type="hidden" name="language" value="en_US">
                                        <input type="hidden" name="command" value="change_locale">
                                        <input class="dropdown-item" type="submit" value="English" class="nav-link">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
</div>