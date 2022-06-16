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

    <!-- Theme Styles -->
    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">
    <title><fmt:message key="label.sign_in"/></title>
</head>
<body>
<div class="app app-auth-sign-up align-content-stretch d-flex flex-wrap justify-content-end">
    <div class="app-auth-background">

    </div>
    <div class="app-auth-container">
        <h1 href="index.html"><fmt:message key="label.sign_in"/></h1>
        <p class="auth-description"><fmt:message key="label.sign_in_tip"/>.<br><fmt:message key="label.not_register_yet"/>? <a href="${pageContext.request.contextPath}/controller?command=to_sign_up_page"><fmt:message key="label.sign_up"/></a></p>
        <c:import url="${pageContext.request.contextPath}/pages/components/message.jsp"/>
        <form action="/controller" method="post" class="needs-validation" novalidate>
            <div class="auth-credentials m-b-xxl">
                <label for="signInEmail" class="form-label"><fmt:message key="label.email"/></label>
                <input type="email" name="userEmail" class="form-control m-b-md" id="signInEmail" value="<c:out value="${userEmail}"/>" required pattern="^(?=.{1,64}@)[\w\-]+(\.[\w\-]+)*@[^-][\w\-]+(\.[\p{Alpha}\d\-]+)*(\.[\p{Alpha}]{2,})$">

                <label for="signUpPassword" class="form-label"><fmt:message key="label.password"/></label>
                <input type="password" name="userPass" class="form-control m-b-md" id="signUpPassword" required>
            </div>
            <input type="hidden" name="command" value="sign_in">
            <div class="auth-submit">
                <input type="submit" class="btn btn-primary" value="<fmt:message key="label.sign_in"/>">
            </div>
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
</body>
</html>
