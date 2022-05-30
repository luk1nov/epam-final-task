<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="util" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All cars</title>
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
    <%@include file="../admin-sidebar.jsp"%>
    <div class="app-container">
        <%@include file="../admin-header.jsp"%>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1>All cars</h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-body">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Category</th>
                                            <th scope="col">Brand</th>
                                            <th scope="col">Model</th>
                                            <th scope="col">Vin</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">0-100</th>
                                            <th scope="col">Drivetrain</th>
                                            <th scope="col">Power</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach var="car" items="${list}">
                                                <tr>
                                                    <td><c:out value="${car.id}"/></td>
                                                    <td><c:out value="${car.carCategory.title}"/></td>
                                                    <td><c:out value="${car.brand}"/></td>
                                                    <td><c:out value="${car.model}"/></td>
                                                    <td><c:out value="${car.vinCode}"/></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${car.active eq 'true'}">
                                                            <span class="badge badge-style-light rounded-pill badge-success">Active</span>
                                                            </c:when>
                                                            <c:when test="${car.active eq 'false'}">
                                                                <span class="badge badge-style-light rounded-pill badge-danger">Repair</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-style-light rounded-pill badge-light"><c:out value="${car.active}"/></span>
                                                            </c:otherwise>
                                                    </c:choose>
                                                    </td>
                                                    <td><c:out value="${car.info.acceleration}"/></td>
                                                    <td><c:out value="${car.info.drivetrain}"/></td>
                                                    <td><c:out value="${car.info.power}"/></td>
                                                    <td>
                                                        <c:if test="${car.salePrice.isEmpty()}">
                                                            $<fmt:formatNumber value = "${car.regularPrice}" maxFractionDigits = "2"/>
                                                        </c:if>
                                                        <c:if test="${car.salePrice.isPresent()}">
                                                            <del>$<c:out value="${car.regularPrice}"/></del> $<fmt:formatNumber value = "${car.salePrice.get()}" maxFractionDigits = "2"/>
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <div class="btn-group dropstart">
                                                            <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                                Action
                                                            </button>
                                                            <ul class="dropdown-menu">
                                                                <li>
                                                                    <form action="/controller" method="POST">
                                                                        <input type="hidden" name="carId" value="<c:out value="${car.id}"/>">
                                                                        <input type="hidden" name="command" value="admin_to_edit_car">
                                                                        <input type="submit" class="dropdown-item" value="Edit">
                                                                    </form>
                                                                </li>
                                                                <li>
                                                                    <form action="/controller" method="POST">
                                                                        <input type="hidden" name="carId" value="<c:out value="${car.id}"/>">
                                                                        <input type="hidden" name="command" value="admin_delete_car">
                                                                        <input type="submit" class="dropdown-item" value="Delete">
                                                                    </form>
                                                                </li>
                                                                <li>
                                                                    <form action="/controller" method="POST">
                                                                        <input type="hidden" name="carId" value="<c:out value="${car.id}"/>">
                                                                        <input type="hidden" name="command" value="admin_change_car_active_status">
                                                                        <input type="hidden" name="carActive" value="<c:out value="${car.active}"/>">
                                                                        <c:if test="${car.active == true}">
                                                                            <input type="submit" class="dropdown-item" value="Send for repair">
                                                                        </c:if>
                                                                        <c:if test="${car.active == false}">
                                                                            <input type="submit" class="dropdown-item" value="Receive from repair">
                                                                        </c:if>
                                                                    </form>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <util:Pagination command="admin_to_all_cars"/>
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
