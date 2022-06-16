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

    <title><fmt:message key="label.all_orders"/></title>

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/images/neptune.png" />

    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>

<body>
<div class="app align-content-stretch d-flex flex-wrap">
    <c:import url="${pageContext.request.contextPath}/pages/admin/template-parts/admin-sidebar.jsp"/>
    <div class="app-container">
        <c:import url="${pageContext.request.contextPath}/pages/admin/template-parts/admin-header.jsp"/>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1><fmt:message key="label.all_orders"/></h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <c:import url="${pageContext.request.contextPath}/pages/components/message.jsp"/>
                        <c:if test="${empty list}">
                            <div class="alert alert-primary alert-style-light" role="alert">
                                <fmt:message key="label.orders_empty"/>
                            </div>
                        </c:if>
                        <c:if test="${not empty list}">
                            <div class="col-md-12">
                                <form action="/controller" method="GET">
                                    <div class="col-md-3 mb-3 ms-auto">
                                        <div class="input-group">
                                            <input type="text" name="search" class="form-control" placeholder="<fmt:message key="label.search_order_placeholder"/>" id="inputSearchQuery" value="<c:out value="${search}"/>">
                                            <input type="hidden" name="command" value="admin_search_order">
                                            <span class="input-group-text p-0" id="basic-addon1">
                                            <input type="submit" class="custom-search" value="<fmt:message key="label.search"/>">
                                        </span>
                                        </div>
                                    </div>
                                </form>
                                <div class="card">
                                    <div class="card-body">
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col"><fmt:message key="label.user"/></th>
                                                <th scope="col"><fmt:message key="label.car"/></th>
                                                <th scope="col"><fmt:message key="label.start_date"/></th>
                                                <th scope="col"><fmt:message key="label.finish_date"/></th>
                                                <th scope="col"><fmt:message key="label.car_status"/></th>
                                                <th scope="col"><fmt:message key="label.user_status"/></th>
                                                <th scope="col"><fmt:message key="label.order_status"/></th>
                                                <th scope="col"><fmt:message key="label.actions"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="default_cancel_reason"><fmt:message key="label.canceled_by_user"/></c:set>
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
                                                        <span class="badge badge-style-light rounded-pill badge-success"><fmt:message key="label.active"/>
                                                        </c:if>
                                                        <c:if test="${not order.car.active}">
                                                            <span class="badge badge-style-light rounded-pill badge-danger"><fmt:message key="label.repair"/>
                                                        </c:if>
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${order.user.status == 'INACTIVE'}">
                                                                <span class="badge badge-style-light rounded-pill badge-warning"><fmt:message key="label.inactive"/></span>
                                                            </c:when>
                                                            <c:when test="${order.user.status == 'ACTIVE'}">
                                                                <span class="badge badge-style-light rounded-pill badge-success"><fmt:message key="label.active"/></span>
                                                            </c:when>
                                                            <c:when test="${order.user.status == 'BLOCKED'}">
                                                                <span class="badge badge-style-light rounded-pill badge-danger"><fmt:message key="label.blocked"/></span>
                                                            </c:when>
                                                            <c:when test="${order.user.status == 'VERIFICATION'}">
                                                                <span class="badge badge-style-light rounded-pill badge-primary"><fmt:message key="label.verification"/></span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-style-light rounded-pill badge-light"><c:out value="${order.user.status}"/></span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${order.orderStatus eq 'PROCESSING'}">
                                                                <span class="badge badge-style-light rounded-pill badge-warning"><fmt:message key="label.processing"/></span>
                                                            </c:when>
                                                            <c:when test="${order.orderStatus eq 'ACTIVE'}">
                                                                <span class="badge badge-style-light rounded-pill badge-success"><fmt:message key="label.active"/></span>
                                                            </c:when>
                                                            <c:when test="${order.orderStatus eq 'CANCELED'}">
                                                                <span class="badge badge-style-light rounded-pill badge-danger" data-bs-toggle="tooltip" data-bs-placement="top" title='<c:out value="${order.message.orElse(null)}" default="${default_cancel_reason}"/>'>
                                                                    <fmt:message key="label.canceled"/>
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${order.orderStatus eq 'FINISHED'}">
                                                                <span class="badge badge-style-light rounded-pill badge-primary"><fmt:message key="label.finished"/></span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-style-light rounded-pill badge-light"><c:out value="${order.orderStatus}"/></span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <div class="btn-group" role="group" aria-label="Basic example">
                                                            <c:choose>
                                                                <c:when test="${order.orderStatus eq 'PROCESSING'}">
                                                                    <form action="/controller" method="POST" class="m-0 me-2">
                                                                        <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                        <input type="hidden" name="command" value="admin_to_decline_order">
                                                                        <input class="btn btn-danger" type="submit" value="<fmt:message key="label.decline"/>">
                                                                    </form>
                                                                    <form action="/controller" method="POST" class="m-0">
                                                                        <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                        <input type="hidden" name="command" value="admin_accept_order">
                                                                        <input class="btn btn-success" type="submit" value="<fmt:message key="label.accept"/>">
                                                                    </form>
                                                                </c:when>
                                                                <c:when test="${order.orderStatus eq 'CANCELED' or order.orderStatus eq 'FINISHED'}">
                                                                    <form action="/controller" method="POST" class="m-0 me-2" onsubmit="return confirm('Are you sure?')">
                                                                        <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                        <input type="hidden" name="command" value="admin_delete_order">
                                                                        <input class="btn btn-danger" type="submit" value="<fmt:message key="label.action_delete"/>">
                                                                    </form>
                                                                    <c:if test="${order.report.isPresent()}">
                                                                        <form action="/controller" method="POST" class="m-0">
                                                                            <input type="hidden" name="reportId" value="<c:out value="${order.report.get().id}"/>">
                                                                            <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                                            <input type="hidden" name="command" value="admin_show_order_report">
                                                                            <input class="btn btn-primary" type="submit" value="<fmt:message key="label.report"/>">
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
                                    </div>
                                </div>
                                <util:Pagination command="admin_find_all_orders"/>
                            </div>
                        </c:if>
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
