<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<div class="app-sidebar">
    <div class="logo">
        <div href="index.html" class="logo-icon"><span class="logo-text">CarRental</span></div>
    </div>
    <div class="app-menu">
        <ul class="accordion-menu">
            <li class="sidebar-title">
                <fmt:message key="label.orders"/>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_find_all_orders">
                    <i class="material-icons-two-tone">list_alt</i>
                    <input type="submit" value="<fmt:message key="label.all_orders"/>">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_find_processing_orders">
                    <i class="material-icons-two-tone">upload</i>
                    <input type="submit" value="<fmt:message key="label.rental_requests"/>">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_find_completed_orders">
                    <i class="material-icons-two-tone">check_circle</i>
                    <input type="submit" value="<fmt:message key="label.completed_orders"/>">
                </form>
            </li>
            <c:if test="${sessionScope.loggedUser.role eq 'ADMIN'}">
                <li class="sidebar-title">
                    <fmt:message key="label.users"/>
                </li>
                <li>
                    <form action="/controller" method="POST">
                        <input type="hidden" name="command" value="admin_to_all_users">
                        <i class="material-icons-two-tone">people</i>
                        <input type="submit" value="<fmt:message key="label.all_users"/>">
                    </form>
                </li>
                <li>
                    <form action="/controller" method="POST">
                        <input type="hidden" name="command" value="admin_to_unverified_users">
                        <i class="material-icons-two-tone">manage_accounts</i>
                        <input type="submit" class="wrapped-submit" value="<fmt:message key="label.unverified_users"/>">
                    </form>
                </li>
            </c:if>
            <li class="sidebar-title">
                <fmt:message key="label.cars"/>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_all_car_categories">
                    <i class="material-icons-two-tone">category</i>
                    <input type="submit" value="<fmt:message key="label.all_categories"/>">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_add_new_car">
                    <i class="material-icons-two-tone">directions_car</i>
                    <input type="submit" value="<fmt:message key="label.add_new_car"/>">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_all_cars">
                    <i class="material-icons-two-tone">garage</i>
                    <input type="submit" value="<fmt:message key="label.all_cars"/>">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_repairing_cars">
                    <i class="material-icons-two-tone">car_crash</i>
                    <input type="submit" value="<fmt:message key="label.repairing_cars"/>">
                </form>
            </li>
        </ul>
    </div>
</div>