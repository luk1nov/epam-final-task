<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <c:out value="${user.name}"/> <c:out value="${user.surname}"/>
    </title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/pace/pace.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/highlight/styles/github-gist.css" rel="stylesheet">

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
                            <div class="page-description user-info-description">
                                <h1>
                                    <c:out value="${user.name}"/> <c:out value="${user.surname}"/>
                                </h1>
                                <h5>
                                    <c:choose>
                                        <c:when test="${user.status == 'INACTIVE'}">
                                            <span class="badge rounded-pill badge-warning"><fmt:message key="label.inactive"/></span>
                                        </c:when>
                                        <c:when test="${user.status == 'ACTIVE'}">
                                            <span class="badge rounded-pill badge-success"><fmt:message key="label.active"/></span>
                                        </c:when>
                                        <c:when test="${user.status == 'BLOCKED'}">
                                            <span class="badge rounded-pill badge-danger"><fmt:message key="label.blocked"/></span>
                                        </c:when>
                                        <c:when test="${user.status == 'VERIFICATION'}">
                                            <span class="badge rounded-pill badge-primary"><fmt:message key="label.verification"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge rounded-pill badge-light"><c:out value="${user.status}"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </h5>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <c:import url="${pageContext.request.contextPath}/pages/components/message.jsp"/>
                        <div class="col-md-6 mx-auto">
                            <div class="card">
                                <c:if test="${not empty user.driverLicense}">
                                    <img src="data:image/jpg;base64,<c:out value="${user.driverLicense}"/>" class="card-img-top">
                                </c:if>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item"><span class="fw-bold"><fmt:message key="label.email"/></span>: <c:out value="${user.email}"/></li>
                                    <li class="list-group-item"><span class="fw-bold"><fmt:message key="label.phone"/></span>: +375-<c:out value="${user.phone}"/></li>
                                    <li class="list-group-item"><span class="fw-bold"><fmt:message key="label.role"/></span>:
                                        <c:choose>
                                            <c:when test="${user.role == 'USER'}">
                                                <fmt:message key="label.user"/>
                                            </c:when>
                                            <c:when test="${user.role == 'MANAGER'}">
                                                <fmt:message key="label.manager"/>
                                            </c:when>
                                            <c:when test="${user.role == 'ADMIN'}">
                                                <fmt:message key="label.admin"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${user.role}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                    <li class="list-group-item"><span class="fw-bold"><fmt:message key="label.balance"/></span>: $<c:out value="${user.balance}"/></li>
                                </ul>
                            </div>
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