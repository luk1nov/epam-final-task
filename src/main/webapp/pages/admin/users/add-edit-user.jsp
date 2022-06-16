<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <c:if test="${user == null}"><fmt:message key="label.add_new_user"/></c:if>
        <c:if test="${user != null}"><fmt:message key="label.edit_user"/></c:if>
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
                            <div class="page-description">
                                <h1>
                                    <c:if test="${user == null}"><fmt:message key="label.add_new_user"/></c:if>
                                    <c:if test="${user != null}"><fmt:message key="label.edit_user"/></c:if>
                                </h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <c:import url="${pageContext.request.contextPath}/pages/components/message.jsp"/>
                        <div class="col-md-6 mx-auto">
                            <div class="card">
                                <div class="card-body">
                                    <div class="example-content">
                                        <form class="row g-3 needs-validation" action="controller" method="post" novalidate>
                                            <div class="col-md-4">
                                                <label for="inputName" class="form-label"><fmt:message key="label.name"/></label>
                                                <input name="userName" type="text" class="form-control" id="inputName" value="<c:out value="${user.name}"/>"  required pattern="^[\p{Alpha}А-яЁё]{2,40}$"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label for="inputSurname" class="form-label"><fmt:message key="label.surname"/></label>
                                                <input name="userSurname" type="text" class="form-control" id="inputSurname" value="<c:out value="${user.surname}"/>" required pattern="^[\p{Alpha}А-яЁё]{2,20}-[\p{Alpha}А-яЁё]{2,20}$|^[\p{Alpha}А-яЁё]{2,45}$"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label for="inputPhone" class="form-label"><fmt:message key="label.phone"/></label>
                                                <div class="input-group">
                                                    <span class="input-group-text" id="basic-addon1">+375</span>
                                                    <input type="text" name="userPhone" class="form-control" id="inputPhone" aria-describedby="inputPhone" value="<c:out value="${user.phone}"/>" required pattern="^\d{2}-\d{3}-\d{2}-\d{2}$">
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <label for="inputEmail" class="form-label"><fmt:message key="label.email"/></label>
                                                <input name="userEmail" type="email" class="form-control" id="inputEmail" value="<c:out value="${user.email}"/>" required pattern="^(?=.{1,64}@)[\w\-]+(\.[\w\-]+)*@[^-][\w\-]+(\.[\p{Alpha}\d\-]+)*(\.[\p{Alpha}]{2,})$"/>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputRole" class="form-label"><fmt:message key="label.role"/></label>
                                                <select name="userRole" id="inputRole" class="form-select" required>
                                                    <option value="USER"><fmt:message key="label.user"/></option>
                                                    <option value="MANAGER"><fmt:message key="label.manager"/></option>
                                                    <option value="ADMIN"><fmt:message key="label.admin"/></option>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputStatus" class="form-label"><fmt:message key="label.status"/></label>
                                                <select name="userStatus" id="inputStatus" class="form-select" required>
                                                    <option value="ACTIVE"><fmt:message key="label.active"/></option>
                                                    <option value="VERIFICATION"><fmt:message key="label.verification"/></option>
                                                    <option value="INACTIVE"><fmt:message key="label.inactive"/></option>
                                                    <option value="BLOCKED"><fmt:message key="label.blocked"/></option>
                                                </select>
                                            </div>
                                            <input type="hidden" name="userId" value="<c:out value="${user.id}"/>">
                                            <div class="col-12">
                                                <c:if test="${user == null}">
                                                    <input type="hidden" name="command" value="admin_add_new_user">
                                                    <input type="submit" class="btn btn-primary" value="<fmt:message key="label.action_add"/>">
                                                </c:if>
                                                <c:if test="${user != null}">
                                                    <input type="hidden" name="command" value="admin_edit_user">
                                                    <input type="submit" class="btn btn-primary" value="<fmt:message key="label.action_edit"/>">
                                                </c:if>
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
<script>
    document.querySelector('#inputRole option[value="<c:out value="${user.role}"/>"]').setAttribute('selected', 'selected');
    document.querySelector('#inputStatus option[value="<c:out value="${user.status}"/>"]').setAttribute('selected', 'selected');
    document.getElementById('inputPhone').addEventListener('input', function (y) {
        var b = y.target.value.replace(/\D/g, '').match(/(\d{0,2})(\d{0,3})(\d{0,2})(\d{0,2})/);
        y.target.value = !b[2] ? b[1] : '' + b[1] + '-' + b[2] + (b[3] ? '-' + b[3] : '') + (b[4] ? '-' + b[4] : '');
    });
</script>
</body>
</html>