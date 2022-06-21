<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">
    <title><fmt:message key="label.sign_up"/></title>
</head>
<body>
<div class="app app-auth-sign-up align-content-stretch d-flex flex-wrap justify-content-end">
    <div class="app-auth-background">
    </div>
    <div class="app-auth-container">
        <h1 href="index.html"><fmt:message key="label.sign_up"/></h1>
        <p class="auth-description"><fmt:message key="label.sign_up_tip"/>.<br><fmt:message key="label.already_have_acc"/>? <a href="${pageContext.request.contextPath}/controller?command=to_sign_in_page"><fmt:message key="label.sign_in"/></a></p>
        <c:import url="${pageContext.request.contextPath}/pages/components/message.jsp"/>
        <form action="/controller" method="post" class="needs-validation" novalidate oninput='userRepeatPass.setCustomValidity(userRepeatPass.value !== userPass.value ? "Passwords do not match." : "");'>
            <div class="auth-credentials m-b-xxl row">
                <div class="col-md-6">
                    <label for="signUpName" class="form-label"><fmt:message key="label.name"/></label>
                    <input type="text" name="userName" class="form-control m-b-md" id="signUpName" aria-describedby="signUpName" value="<c:out value="${userName}"/>" required pattern="^[\p{Alpha}А-яЁё]{2,40}$">
                </div>
                <div class="col-md-6">
                    <label for="signUpSurname" class="form-label"><fmt:message key="label.surname"/></label>
                    <input type="text" name="userSurname" class="form-control m-b-md" id="signUpSurname" aria-describedby="signUpSurname" value="<c:out value="${userSurname}"/>" required pattern="^[\p{Alpha}А-яЁё]{2,20}-[\p{Alpha}А-яЁё]{2,20}$|^[\p{Alpha}А-яЁё]{2,45}$">
                </div>
                <div class="col-md-12">
                    <label for="signUpPhone" class="form-label"><fmt:message key="label.phone"/></label>
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">+375</span>
                        <input type="text" name="userPhone" class="form-control" id="signUpPhone" value="<c:out value="${userPhone}"/>" required pattern="^\d{2}-\d{3}-\d{2}-\d{2}$">
                    </div>
                </div>
                <div class="col-md-12">
                    <label for="signUpEmail" class="form-label"><fmt:message key="label.email"/></label>
                    <input type="email" name="userEmail" class="form-control m-b-md" id="signUpEmail" value="<c:out value="${userEmail}"/>" required pattern="^(?=.{1,64}@)[\w\-]+(\.[\w\-]+)*@[^-][\w\-]+(\.[\p{Alpha}\d\-]+)*(\.[\p{Alpha}]{2,})$">
                </div>
                <div class="col-md-6">
                    <label for="signUpPassword" class="form-label"><fmt:message key="label.password"/></label>
                    <input type="password" name="userPass" class="form-control m-b-md" id="signUpPassword" aria-describedby="signUpPassword" required pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$">
                    <div class="invalid-feedback">
                        Passwords must have at least 8 symbols, 1 digit, 1 uppercase letter and 1 lowercase letter.
                    </div>
                </div>
                <div class="col-md-6">
                    <label for="signUpRepPassword" class="form-label"><fmt:message key="label.repeat_password"/></label>
                    <input type="password" name="userRepeatPass" class="form-control" id="signUpRepPassword" aria-describedby="signUpRepPassword" required>
                    <div class="invalid-feedback">
                        Passwords must match.
                    </div>
                </div>
            </div>
            <div class="auth-submit">
                <input type="submit" class="btn btn-primary" value="<fmt:message key="label.sign_up"/>">
            </div>
            <input type="hidden" name="command" value="sign_up">
        </form>
        <div class="divider"></div>
        <form action="/controller" method="POST">
            <input type="hidden" name="command" value="default">
            <input type="submit" class="btn btn-danger" value="<fmt:message key="label.back_to_site"/>">
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
<script>
    document.getElementById('signUpPhone').addEventListener('input', function (y) {
        var b = y.target.value.replace(/\D/g, '').match(/(\d{0,2})(\d{0,3})(\d{0,2})(\d{0,2})/);
        y.target.value = !b[2] ? b[1] : '' + b[1] + '-' + b[2] + (b[3] ? '-' + b[3] : '') + (b[4] ? '-' + b[4] : '');
    });
</script>
</body>
</html>
