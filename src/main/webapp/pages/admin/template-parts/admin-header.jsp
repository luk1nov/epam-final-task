<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<div class="app-header">
    <nav class="navbar navbar-light navbar-expand-lg">
        <div class="container-fluid">
            <div class="navbar-nav" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link hide-sidebar-toggle-button" href="#"><i class="material-icons">first_page</i></a>
                    </li>
                    <li class="nav-item admin-home-link">
                        <form action="/controller" method="POST">
                            <input type="hidden" name="command" value="default">
                            <input type="submit" value="<fmt:message key="label.back_to_site"/>">
                        </form>
                    </li>
                </ul>
            </div>
            <div class="d-flex">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=log_out"><fmt:message key="label.log_out"/></a>
                    </li>
                    <c:choose>
                        <c:when test="${sessionScope.locale eq 'en'}">
                            <li class="nav-item hidden-on-mobile">
                                <a class="nav-link language-dropdown-toggle" href="#" data-bs-toggle="dropdown"><img src="${pageContext.request.contextPath}/resources/images/flags/us.png" alt=""></a>
                                <ul class="dropdown-menu dropdown-menu-end language-dropdown" aria-labelledby="languageDropDown">
                                    <li>
                                        <form action="/controller" class="lang-button ru-button" method="POST">
                                            <input type="hidden" name="locale" value="ru">
                                            <input type="hidden" name="command" value="change_locale">
                                            <input class="dropdown-item" type="submit" value="Русский" class="nav-link">
                                        </form>
                                    </li>
                                </ul>
                            </li>
                        </c:when>
                        <c:when test="${sessionScope.locale eq 'ru'}">
                            <li class="nav-item hidden-on-mobile">
                                <a class="nav-link language-dropdown-toggle" href="#" data-bs-toggle="dropdown"><img src="${pageContext.request.contextPath}/resources/images/flags/russia.png" alt=""></a>
                                <ul class="dropdown-menu dropdown-menu-end language-dropdown" aria-labelledby="languageDropDown">
                                    <li>
                                        <form action="/controller" class="lang-button eng-button" method="POST">
                                            <input type="hidden" name="locale" value="en">
                                            <input type="hidden" name="command" value="change_locale">
                                            <input class="dropdown-item" type="submit" value="English" class="nav-link">
                                        </form>
                                    </li>
                                </ul>
                            </li>
                        </c:when>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</div>