<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="util" uri="customtags" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.orders"/></title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Audiowide&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
</head>
<body>

<div class="app full-width-header align-content-stretch d-flex flex-wrap">
    <div class="app-container" style="width: 100vw; margin-right: 280px;">
        <c:import url="../components/header.jsp"/>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="d-flex page-description align-items-center">
                                <div class="avatar avatar-xxl avatar-rounded m-r-lg">
                                    <c:choose>
                                        <c:when test="${sessionScope.loggedUser.status == 'INACTIVE'}">
                                            <span class="badge rounded-pill badge-warning"><fmt:message key="label.inactive"/></span>
                                        </c:when>
                                        <c:when test="${sessionScope.loggedUser.status == 'ACTIVE'}">
                                            <span class="badge rounded-pill badge-success"><fmt:message key="label.active"/></span>
                                        </c:when>
                                        <c:when test="${sessionScope.loggedUser.status == 'BLOCKED'}">
                                            <span class="badge rounded-pill badge-danger"><fmt:message key="label.blocked"/></span>
                                        </c:when>
                                        <c:when test="${sessionScope.loggedUser.status == 'VERIFICATION'}">
                                            <span class="badge rounded-pill badge-primary"><fmt:message key="label.verification"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge rounded-pill badge-light"><c:out value="${sessionScope.loggedUser.status}"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="avatar-title"><c:out value="${sessionScope.loggedUser.name.charAt(0)}"/><c:out value="${sessionScope.loggedUser.surname.charAt(0)}"/></div>
                                </div>
                                <h1 class="w-auto"><c:out value="${sessionScope.loggedUser.name}"/> <c:out value="${sessionScope.loggedUser.surname}"/></h1>
                                <h2 class="w-auto ms-auto"><fmt:message key="label.balance"/>: $<c:out value="${sessionScope.loggedUser.balance}"/></h2>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8 mx-auto">
                            <ul class="nav nav-pills nav-justified mb-3">
                                <li class="nav-item">
                                    <form action="controller" method="POST">
                                        <input type="hidden" name="command" value="to_user_account">
                                        <input type="submit" class="nav-link" value="<fmt:message key="label.profile"/>">
                                    </form>
                                </li>
                                <li class="nav-item">
                                    <form action="controller" method="POST">
                                        <input type="hidden" name="command" value="find_all_user_orders">
                                        <input type="submit" class="nav-link active disabled" value="<fmt:message key="label.orders"/>">
                                    </form>
                                </li>
                            </ul>
                            <c:import url="${pageContext.request.contextPath}/pages/components/message.jsp"/>
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title"><fmt:message key="label.orders"/></h5>
                                </div>
                                <div class="card-body">
                                    <div class="example-content">
                                        <c:if test="${not empty list}">
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th scope="col">ID</th>
                                                    <th scope="col"><fmt:message key="label.car"/></th>
                                                    <th scope="col"><fmt:message key="label.start_date"/></th>
                                                    <th scope="col"><fmt:message key="label.finish_date"/></th>
                                                    <th scope="col"><fmt:message key="label.price"/></th>
                                                    <th scope="col"><fmt:message key="label.status"/></th>
                                                    <th scope="col"><fmt:message key="label.actions"/></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="order" items="${list}">
                                                        <tr>
                                                            <td><c:out value="${order.id}"/></td>
                                                            <td>
                                                                <form action="/controller" method="post">
                                                                    <input type="hidden" name="command" value="to_car_page">
                                                                    <input type="hidden" name="carId" value="<c:out value='${order.car.id}'/>">
                                                                    <input type="submit" class="order-car-link" value="<c:out value='${order.car.brand}'/> <c:out value='${order.car.model}'/>">
                                                                </form>
                                                            </td>
                                                            <td>
                                                                <fmt:parseDate value="${order.beginDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                                                                <fmt:formatDate value="${parsedDate}" type="date" pattern="dd MMMM yyyy" />
                                                            </td>
                                                            <td>
                                                                <fmt:parseDate value="${order.endDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                                                                <fmt:formatDate value="${parsedDate}" type="date" pattern="dd MMMM yyyy" />
                                                            </td>
                                                            <td>$<c:out value="${order.price}"/></td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${order.orderStatus eq 'PROCESSING'}">
                                                                        <span class="badge badge-style-light rounded-pill badge-warning"><fmt:message key="label.processing"/>
                                                                    </c:when>
                                                                    <c:when test="${order.orderStatus eq 'ACTIVE'}">
                                                                        <span class="badge badge-style-light rounded-pill badge-success"><fmt:message key="label.active"/>
                                                                    </c:when>
                                                                    <c:when test="${order.orderStatus eq 'CANCELED'}">
                                                                        <span class="badge badge-style-light rounded-pill badge-danger" <c:if test="${order.message.isPresent()}">data-bs-toggle="tooltip" data-bs-placement="bottom" title="<c:out value="${order.message.get()}"/>"</c:if>>
                                                                                <fmt:message key="label.canceled"/>
                                                                    </c:when>
                                                                    <c:when test="${order.orderStatus eq 'FINISHED'}">
                                                                        <span class="badge badge-style-light rounded-pill badge-primary"><fmt:message key="label.finished"/>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge badge-style-light rounded-pill badge-light"><c:out value="${order.orderStatus}"/>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                </span>
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${order.orderStatus eq 'PROCESSING'}">
                                                                        <form action="/controller" method="POST">
                                                                            <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                            <input type="hidden" name="command" value="cancel_user_order">
                                                                            <input class="btn btn-danger btn-sm" type="submit" value="<fmt:message key="label.action_cancel"/>">
                                                                        </form>
                                                                    </c:when>
                                                                    <c:when test="${order.orderStatus eq 'ACTIVE'}">
                                                                        <form action="/controller" method="POST">
                                                                            <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                            <input type="hidden" name="command" value="to_return_car">
                                                                            <input class="btn btn-success btn-sm" type="submit" value="<fmt:message key="label.action_finish"/>">
                                                                        </form>
                                                                    </c:when>
                                                                    <c:when test="${order.orderStatus eq 'FINISHED' or order.orderStatus eq 'CANCELED'}">
                                                                        <form action="/controller" method="POST">
                                                                            <input type="hidden" name="carId" value="<c:out value="${order.car.id}"/>">
                                                                            <input type="hidden" name="command" value="to_car_page">
                                                                            <input class="btn btn-primary btn-sm" type="submit" value="<fmt:message key="label.rent_again"/>">
                                                                        </form>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                        <c:if test="${empty list}">
                                            <div class="alert alert-primary alert-style-light" role="alert">
                                                <fmt:message key="label.have_no_orders_yet"/>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <util:Pagination command="find_all_user_orders"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Javascripts -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
</body>
</html>