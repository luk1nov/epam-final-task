<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Title</title>
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
    <c:import url="../admin-sidebar.jsp"/>
    <div class="app-container">
        <c:import url="../admin-header.jsp"/>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1>Unverified users</h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <c:if test="${message ne null}">
                            <div class="alert alert-primary" role="alert">
                                <fmt:message key="${fn:escapeXml(message)}"/>
                            </div>
                        </c:if>
                        <c:if test="${empty list}">
                            <div class="alert alert-primary alert-style-light" role="alert">
                                <fmt:message key="label.users_empty"/>
                            </div>
                        </c:if>
                        <c:forEach var="user" items="${list}">
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="car-card-img-container h-100">
                                        <img class="car-card-img" src="data:image/jpg;base64,<c:out value="${user.driverLicense}"/>"/>
                                    </div>
                                    <div class="card-body">
                                        <h5 class="card-title"><c:out value="${user.name}"/> <c:out value="${user.surname}"/></h5>
                                        <p class="card-text">
                                            Email: <c:out value="${user.email}"/>
                                        </p>
                                        <div class="row car-card-row align-items-start">
                                            <div class="col-6">
                                                <form action="/controller" method="POST" class="m-0">
                                                    <input type="hidden" name="userId" value="<c:out value="${user.id}"/>">
                                                    <input type="hidden" name="command" value="admin_decline_user">
                                                    <input class="btn btn-danger" type="submit" value="Decline">
                                                </form>
                                            </div>
                                            <div class="col-6">
                                                <form action="/controller" method="POST" class="m-0">
                                                    <input type="hidden" name="userId" value="<c:out value="${user.id}"/>">
                                                    <input type="hidden" name="command" value="admin_verify_user">
                                                    <input class="btn btn-success" type="submit" value="Accept">
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <nav aria-label="Page navigation example">
                        <ul class="pagination">
                            <c:if test="${page - 2 >= 1}">
                                <li class="page-item">
                                    <form action="/controller" method="POST" class="m-0">
                                        <input type="hidden" name="page" value="<c:out value="${page - 2}"/>">
                                        <input type="hidden" name="command" value="admin_to_unverified_users">
                                        <input class="page-link" type="submit" value="<c:out value="${page - 2}"/>">
                                    </form>
                                </li>
                            </c:if>
                            <c:if test="${page - 1 >= 1}">
                                <li class="page-item">
                                    <form action="/controller" method="POST" class="m-0">
                                        <input type="hidden" name="page" value="<c:out value="${page - 1}"/>">
                                        <input type="hidden" name="command" value="admin_to_unverified_users">
                                        <input class="page-link" type="submit" value="<c:out value="${page - 1}"/>">
                                    </form>
                                </li>
                            </c:if>
                            <li class="page-item disabled active">
                                <form action="/controller" method="POST" class="m-0">
                                    <input type="hidden" name="page" value="${page}">
                                    <input type="hidden" name="command" value="admin_to_unverified_users">
                                    <input class="page-link" type="submit" value="${page}">
                                </form>
                            </li>
                            <c:if test="${page + 1 <= pagesCount}">
                                <li class="page-item">
                                    <form action="/controller" method="POST" class="m-0">
                                        <input type="hidden" name="page" value="<c:out value="${page + 1}"/>">
                                        <input type="hidden" name="command" value="admin_to_unverified_users">
                                        <input class="page-link" type="submit" value="<c:out value="${page + 1}"/>">
                                    </form>
                                </li>
                            </c:if>
                            <c:if test="${page + 2 <= pagesCount}">
                                <li class="page-item">
                                    <form action="/controller" method="POST" class="m-0">
                                        <input type="hidden" name="page" value="<c:out value="${page + 2}"/>">
                                        <input type="hidden" name="command" value="admin_to_unverified_users">
                                        <input class="page-link" type="submit" value="<c:out value="${page + 2}"/>">
                                    </form>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
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
