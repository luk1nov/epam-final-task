<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.all_categories"/></title>
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
                                <h1><fmt:message key="label.all_categories"/></h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mx-auto">
                            <div class="card">
                                <div class="card-body">
                                    <form action="/controller" method="POST">
                                        <div class="col-12">
                                            <input type="hidden" name="command" value="admin_to_add_new_car_category">
                                            <input type="submit" class="btn btn-primary" value="<fmt:message key="label.add_new_category"/>">
                                        </div>
                                    </form>
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col"><fmt:message key="label.title"/></th>
                                            <th scope="col"><fmt:message key="label.actions"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="category" items="${list}">
                                            <tr>
                                                <td><c:out value="${category.id}"/></td>
                                                <td><c:out value="${category.title}"/></td>
                                                <td>
                                                    <div class="btn-group" role="group" aria-label="Basic example">
                                                        <form action="/controller" method="POST">
                                                            <input type="hidden" name="carCategoryId" value="<c:out value="${category.id}"/>">
                                                            <input type="hidden" name="command" value="admin_to_edit_car_category">
                                                            <input type="submit" class="btn btn-info" value="<fmt:message key="label.action_edit"/>">
                                                        </form>
                                                        <c:if test="${category.id != 1}">
                                                            <form action="/controller" method="POST">
                                                                <input type="hidden" name="carCategoryId" value="<c:out value="${category.id}"/>">
                                                                <input type="hidden" name="command" value="admin_delete_car_category">
                                                                <input type="submit" class="btn btn-danger" value="<fmt:message key="label.action_delete"/>">
                                                            </form>
                                                        </c:if>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
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
