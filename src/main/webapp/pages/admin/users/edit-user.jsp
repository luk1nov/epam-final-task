<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit user</title>
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
    <%@include file="../admin-sidebar.jsp"%>
    <div class="app-container">
        <%@include file="../admin-header.jsp"%>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1>Edit user</h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mx-auto">
                            <div class="card">
                                <div class="card-body">
                                    <div class="example-content">
                                        <c:set var="user" value="${user}" />
                                        <form class="row g-3" action="controller" method="post">
                                            <div class="col-md-6">
                                                <label for="inputName" class="form-label">Name</label>
                                                <input name="userName" type="text" class="form-control" id="inputName" value="${user.name}"/>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="inputSurname" class="form-label">Surname</label>
                                                <input name="userSurname" type="text" class="form-control" id="inputSurname" value="${user.surname}"/>
                                            </div>
                                            <div class="col-6">
                                                <label for="inputEmail" class="form-label">Email</label>
                                                <input name="userEmail" type="email" class="form-control" id="inputEmail" value="${user.email}"/>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputRole" class="form-label">Role</label>
                                                <select name="userRole" id="inputRole" class="form-select">
                                                    <option <c:if test="${user.role == 'USER'}">selected</c:if>>User</option>
                                                    <option <c:if test="${user.role == 'MANAGER'}">selected</c:if>>Manager</option>
                                                    <option <c:if test="${user.role == 'ADMIN'}">selected</c:if>>Admin</option>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputStatus" class="form-label">Status</label>
                                                <select name="userStatus" id="inputStatus" class="form-select">
                                                    <option <c:if test="${user.status == 'ACTIVE'}">selected</c:if>>Active</option>
                                                    <option <c:if test="${user.status == 'INACTIVE'}">selected</c:if>>Inactive</option>
                                                    <option <c:if test="${user.status == 'BLOCKED'}">selected</c:if>>Blocked</option>
                                                </select>
                                            </div>
                                            <%--<div class="col-md-12">
                                                <label for="formFile" class="form-label">Driver license</label>
                                                <input class="form-control" type="file" id="formFile">
                                            </div>--%>
                                            <input type="hidden" name="user_id" value="${user.id}">
                                            <input type="hidden" name="command" value="admin_edit_user">
                                            <div class="col-12">
                                                <button type="submit" class="btn btn-primary">Save</button>
                                            </div>
                                        </form>
                                    </div>
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