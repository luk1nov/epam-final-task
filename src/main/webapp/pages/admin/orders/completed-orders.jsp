<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="util" uri="customtags" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Responsive Admin Dashboard Template">
    <meta name="keywords" content="admin,dashboard">
    <meta name="author" content="stacks">
    <!-- The above 6 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Title -->
    <title>Completed orders</title>

    <!-- Styles -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.css" rel="stylesheet">

    <!-- Theme Styles -->
    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/images/neptune.png" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="app align-content-stretch d-flex flex-wrap">
    <%@include file="../admin-sidebar.jsp"%>
    <div class="app-container">
        <%@include file="../admin-header.jsp"%>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1>Completed orders</h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-body">
                                    <c:if test="${empty list}">
                                        <div class="alert alert-primary alert-style-light" role="alert">
                                            <fmt:message key="label.rental_requests_empty"/>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty list}">
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">User</th>
                                                <th scope="col">Car</th>
                                                <th scope="col">From</th>
                                                <th scope="col">To</th>
                                                <th scope="col">Car status</th>
                                                <th scope="col">User status</th>
                                                <th scope="col">Order status</th>
                                                <th scope="col">Actions</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="order" items="${list}">
                                                <tr>
                                                    <td><c:out value="${order.id}"/></td>
                                                    <td><c:out value="${order.user.name}"/> <c:out value="${order.user.surname}"/></td>
                                                    <td>
                                                        <form action="/controller" method="POST" class="m-0">
                                                            <input type="hidden" name="command" value="to_car_page">
                                                            <input type="hidden" name="carId" value="<c:out value="${order.car.id}"/>">
                                                            <input class="order-car-link" type="submit" value="<c:out value="${order.car.brand}"/> <c:out value="${order.car.model}"/>">
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
                                                    <td>
                                                        <c:if test="${order.car.active}">
                                                        <span class="badge badge-style-light rounded-pill badge-success">ACTIVE
                                                        </c:if>
                                                        <c:if test="${not order.car.active}">
                                                            <span class="badge badge-style-light rounded-pill badge-danger">REPAIR
                                                        </c:if>
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                        <c:when test="${order.user.status == 'INACTIVE'}">
                                                        <span class="badge badge-style-light rounded-pill badge-warning">
                                                            </c:when>
                                                            <c:when test="${order.user.status == 'ACTIVE'}">
                                                                <span class="badge badge-style-light rounded-pill badge-success">
                                                            </c:when>
                                                            <c:when test="${order.user.status == 'BLOCKED'}">
                                                                <span class="badge badge-style-light rounded-pill badge-danger">
                                                            </c:when>
                                                            <c:when test="${order.user.status == 'VERIFICATION'}">
                                                                <span class="badge badge-style-light rounded-pill badge-primary">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-style-light rounded-pill badge-light">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:out value="${order.user.status}"/></span>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                        <c:when test="${order.orderStatus eq 'PROCESSING'}">
                                                        <span class="badge badge-style-light rounded-pill badge-warning">
                                                            </c:when>
                                                            <c:when test="${order.orderStatus eq 'ACTIVE'}">
                                                                <span class="badge badge-style-light rounded-pill badge-success">
                                                            </c:when>
                                                            <c:when test="${order.orderStatus eq 'CANCELED'}">
                                                                <span class="badge badge-style-light rounded-pill badge-danger" data-bs-toggle="tooltip" data-bs-placement="top" title='<c:out value="${order.message.orElse(null)}" default="Canceled by user"/>'>
                                                            </c:when>
                                                            <c:when test="${order.orderStatus eq 'FINISHED'}">
                                                                <span class="badge badge-style-light rounded-pill badge-primary">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-style-light rounded-pill badge-light">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:out value="${order.orderStatus}"/></span>
                                                    </td>
                                                    <td>
                                                        <div class="btn-group" role="group" aria-label="Basic example">
                                                            <c:choose>
                                                                <c:when test="${order.orderStatus eq 'PROCESSING'}">
                                                                    <form action="/controller" method="POST" class="m-0 me-2">
                                                                        <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                        <input type="hidden" name="command" value="admin_to_decline_order">
                                                                        <input class="btn btn-danger" type="submit" value="Decline">
                                                                    </form>
                                                                    <form action="/controller" method="POST" class="m-0">
                                                                        <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                        <input type="hidden" name="command" value="admin_accept_order">
                                                                        <input class="btn btn-success" type="submit" value="Accept">
                                                                    </form>
                                                                </c:when>
                                                                <c:when test="${order.orderStatus eq 'CANCELED' or order.orderStatus eq 'FINISHED'}">
                                                                    <form action="/controller" method="POST" class="m-0 me-2" onsubmit="return confirm('Are you sure?')">
                                                                        <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                        <input type="hidden" name="command" value="admin_delete_order">
                                                                        <input class="btn btn-danger" type="submit" value="Delete">
                                                                    </form>
                                                                    <c:if test="${order.report.isPresent()}">
                                                                        <form action="/controller" method="POST" class="m-0">
                                                                            <input type="hidden" name="reportId" value="<c:out value="${order.report.get().id}"/>">
                                                                            <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                            <input type="hidden" name="command" value="admin_show_order_report">
                                                                            <input class="btn btn-primary" type="submit" value="Report">
                                                                        </form>
                                                                    </c:if>
                                                                </c:when>
                                                            </c:choose>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>
                                </div>
                            </div>
                            <util:Pagination command="admin_find_completed_orders"/>
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
<script src="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
</body>

</html>
