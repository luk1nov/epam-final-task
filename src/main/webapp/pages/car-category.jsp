<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                                <h1>Full-width Header</h1>
                                <span>Header without spacing to sidebar and page edges. <div
                                        class="alert alert-secondary m-t-lg" role="alert">Note! Logo block with user
                                            info in it is not compatible with full-width header.</div></span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
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
                                            Acceleration 0-100: <c:out value="${car.info.acceleration}"/>s<br>
                                            Power: <c:out value="${car.info.power}"/>hp<br>
                                            Drivetrain: <c:out value="${car.info.drivetrain}"/><br>
                                        </p>
                                        <div class="row car-card-row">
                                            <p class="card-text">Price: $<c:out value="${car.salePrice.orElse(null)}" default="${car.regularPrice}"/>/hour</p>
                                            <form action="/controller" method="POST">
                                                <input type="hidden" name="car_id" value="<c:out value="${car.id}"/>">
                                                <input type="hidden" name="command" value="">
                                                <input class="btn btn-primary" type="submit" value="Rent">
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
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