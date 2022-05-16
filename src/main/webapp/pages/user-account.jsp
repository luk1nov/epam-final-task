<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title>Refill balance</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Audiowide&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
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
                            <div class="d-flex page-description align-items-center">
                                <div class="avatar avatar-xxl avatar-rounded m-r-lg">
                                    <c:choose>
                                        <c:when test="${sessionScope.loggedUser.status == 'INACTIVE'}">
                                            <span class="badge rounded-pill badge-warning">Inactive</span>
                                        </c:when>
                                        <c:when test="${sessionScope.loggedUser.status == 'ACTIVE'}">
                                            <span class="badge rounded-pill badge-success">Active</span>
                                        </c:when>
                                        <c:when test="${sessionScope.loggedUser.status == 'BLOCKED'}">
                                            <span class="badge rounded-pill badge-danger">Blocked</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge rounded-pill badge-primary"><c:out value="${sessionScope.loggedUser.status}"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="avatar-title"><c:out value="${sessionScope.loggedUser.name.charAt(0)}"/><c:out value="${sessionScope.loggedUser.surname.charAt(0)}"/></div>
                                </div>
                                <h1 class="w-auto"><c:out value="${sessionScope.loggedUser.name}"/> <c:out value="${sessionScope.loggedUser.surname}"/></h1>
                                <h2 class="w-auto ms-auto">Balance: $<c:out value="${sessionScope.loggedUser.balance}"/></h2>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8 mx-auto">
                            <ul class="nav nav-pills nav-justified mb-3">
                                <li class="nav-item">
                                    <form action="/controller" method="POST">
                                        <input type="hidden" name="command" value="to_user_account">
                                        <input type="submit" class="nav-link active disabled" value="Profile">
                                    </form>
                                </li>
                                <li class="nav-item">
                                    <form action="/controller" method="POST">
                                        <input type="hidden" name="command" value="find_all_user_orders">
                                        <input type="submit" class="nav-link" value="Orders">
                                    </form>
                                </li>
                            </ul>
                            <c:if test="${message ne null}">
                                <div class="alert alert-danger alert-style-light" role="alert">
                                    Error. <fmt:message key="${fn:escapeXml(message)}"/>.
                                </div>
                            </c:if>
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Edit information</h5>
                                </div>
                                <div class="card-body">
                                    <div class="example-content">
                                        <form class="row g-3" action="/controller" method="POST">
                                            <div class="col-md-6">
                                                <label for="inputName" class="form-label">Name</label>
                                                <input name="userName" type="text" class="form-control" id="inputName" value="<c:out value="${sessionScope.loggedUser.name}"/>">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="inputSurname" class="form-label">Surname</label>
                                                <input name="userSurname" type="text" class="form-control" id="inputSurname" value="<c:out value="${sessionScope.loggedUser.surname}"/>">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="inputEmail" class="form-label">Email</label>
                                                <input name="userEmail" type="text" class="form-control" id="inputEmail" value="<c:out value="${sessionScope.loggedUser.email}"/>">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="inputPhone" class="form-label">Phone</label>
                                                <div class="input-group mb-3">
                                                    <span class="input-group-text" id="basic-addon1">+375</span>
                                                    <input type="text" name="userPhone" class="form-control" id="inputPhone" value="<c:out value="${sessionScope.loggedUser.phone}"/>">
                                                </div>
                                            </div>
                                            <input type="hidden" name="command" value="update_user_info">
                                            <div class="col-12">
                                                <input type="submit" class="btn btn-primary" value="Save"/>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8 mx-auto">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Upload driver license</h5>
                                </div>
                                <div class="card-body">
                                    <div class="example-content">
                                        <c:if test="${sessionScope.loggedUser.driverLicense != null}">
                                            <img class="mb-4 full-width" src="data:image/jpg;base64,${sessionScope.loggedUser.driverLicense}"/>
                                        </c:if>
                                        <c:if test="${sessionScope.loggedUser.driverLicense == null}">
                                            <div class="alert alert-danger alert-style-light" role="alert">
                                                Your account is not active. Upload your driver license photo to verify your account.
                                            </div>
                                        </c:if>
                                        <form class="row g-3" action="/controller" method="POST" enctype="multipart/form-data">
                                            <div class="col-md-12">
                                                <input name="userDriverLicense" type="file" class="form-control" id="formFile" required>
                                            </div>
                                            <input type="hidden" name="command" value="update_driver_license">
                                            <div class="col-12">
                                                <input type="submit" class="btn btn-primary" value="Save"/>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8 mx-auto">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Change password</h5>
                                </div>
                                <div class="card-body">
                                    <div class="example-content">
                                        <form class="row g-3" action="/controller" method="POST">
                                            <div class="col-md-12">
                                                <label for="inputSurname" class="form-label">Current password</label>
                                                <input name="currentPass" type="password" class="form-control" id="inputCurrentPassword">
                                            </div>
                                            <div class="col-md-12">
                                                <label for="inputEmail" class="form-label">New password</label>
                                                <input name="newPass" type="password" class="form-control" id="inputNewPassword">
                                            </div>
                                            <div class="col-md-12">
                                                <label for="inputEmail" class="form-label">Repeat new password</label>
                                                <input name="repeatNewPass" type="password" class="form-control" id="inputNewRepPassword">
                                            </div>
                                            <input type="hidden" name="command" value="change_password">
                                            <div class="col-12">
                                                <input type="submit" class="btn btn-primary" value="Change"/>
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
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
<script>
    document.getElementById('inputPhone').addEventListener('input', function (y) {
        var b = y.target.value.replace(/\D/g, '').match(/(\d{0,2})(\d{0,3})(\d{0,2})(\d{0,2})/);
        y.target.value = !b[2] ? b[1] : '' + b[1] + '-' + b[2] + (b[3] ? '-' + b[3] : '') + (b[4] ? '-' + b[4] : '');
    });
</script>
</body>
</html>