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
                                    <c:choose>
                                        <c:when test="${fn:toLowerCase(carCategoryTitle) eq 'cars'}">
                                            <fmt:message key="label.category_cars"/>
                                        </c:when>
                                        <c:when test="${fn:toLowerCase(carCategoryTitle) eq 'e-cars'}">
                                            <fmt:message key="label.category_e_cars"/>
                                        </c:when>
                                        <c:when test="${fn:toLowerCase(carCategoryTitle) eq 'premium'}">
                                            <fmt:message key="label.category_premium"/>
                                        </c:when>
                                        <c:otherwise>Car category</c:otherwise>
                                    </c:choose>
                                </h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <c:import url="${pageContext.request.contextPath}/pages/components/message.jsp"/>
                        <c:forEach var="category" items="${applicationScope.contextCategories}">
                            <div class="col-md-2">
                                <a href="/controller?command=to_car_category_page&carCategoryId=<c:out value="${category.id}"/>">
                                    <div class="card text-center m-1">
                                        <div class="card-body pt-5 pb-5">
                                            <h5 class="card-title"><c:out value="${category.title}"/></h5>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </c:forEach>
                        <div class="col-md-2">
                            <a href="/controller?command=to_all_cars">
                                <div class="card text-center m-1">
                                    <div class="card-body pt-5 pb-5">
                                        <h5 class="card-title"><fmt:message key="label.all_cars"/></h5>
                                    </div>
                                </div>
                            </a>
                        </div>
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