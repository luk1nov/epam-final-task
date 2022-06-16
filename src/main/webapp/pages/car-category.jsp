<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="util" uri="customtags" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title>Cars</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Audiowide&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">
</head>
<body>

<div class="app full-width-header align-content-stretch d-flex flex-wrap">
    <div class="app-container" style="width: 100vw; margin-right: 280px;">
        <c:import url="components/header.jsp"/>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1>
                                    <c:if test="${not empty carCategoryTitle}">
                                        <c:out value="${carCategoryTitle}"/>
                                    </c:if>
                                    <c:if test="${empty carCategoryTitle}">
                                        All cars
                                    </c:if>
                                </h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <c:import url="${pageContext.request.contextPath}/pages/components/message.jsp"/>
                        <c:if test="${empty list}">
                            <div class="alert alert-primary alert-style-light" role="alert">
                                <fmt:message key="label.cars_empty"/>
                            </div>
                        </c:if>
                        <c:forEach var="car" items="${list}">
                            <div class="col-md-3">
                                <div class="card">
                                    <div class="car-card-img-container">
                                        <c:if test="${car.image ne null}">
                                            <img class="car-card-img" src="data:image/jpg;base64,<c:out value="${car.image}"/>"/>
                                        </c:if>
                                        <c:if test="${car.image eq null}">
                                            <img src="${pageContext.request.contextPath}/resources/images/cards/card.png" class="card-img-top" alt="...">
                                        </c:if>
                                    </div>
                                    <div class="card-body">
                                        <h5 class="card-title"><c:out value="${car.brand}"/> <c:out value="${car.model}"/></h5>
                                        <p class="card-text">
                                            <fmt:message key="label.car_acceleration"/> 0-100: <c:out value="${car.info.acceleration}"/>s<br>
                                            <fmt:message key="label.car_power"/>: <c:out value="${car.info.power}"/>hp<br>
                                            <fmt:message key="label.car_drivetrain"/>: <c:out value="${car.info.drivetrain}"/><br>
                                        </p>
                                        <div class="row car-card-row">
                                            <p class="card-text bold">
                                                <c:if test="${car.salePrice.isPresent()}">
                                                    <del>$<c:out value="${car.regularPrice}"/></del><br/>
                                                $<c:out value="${car.salePrice.get()}"/>
                                                </c:if>
                                                <c:if test="${car.salePrice.isEmpty()}">
                                                    $<c:out value="${car.regularPrice}"/>
                                                </c:if>/ <fmt:message key="label.day"/>
                                            </p>
                                            <form action="/controller" method="POST">
                                                <input type="hidden" name="carId" value="<c:out value="${car.id}"/>">
                                                <input type="hidden" name="command" value="to_car_page">
                                                <input class="btn btn-primary" type="submit" value="<fmt:message key="label.rent"/>">
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <c:if test="${not empty carCategoryTitle}">
                        <util:Pagination command="to_car_category_page"/>
                    </c:if>
                    <c:if test="${empty carCategoryTitle}">
                        <util:Pagination command="to_all_cars"/>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Javascripts -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
</body>
</html>