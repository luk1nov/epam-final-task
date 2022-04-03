<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Theme Styles -->
    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<div class="app app-auth-sign-up align-content-stretch d-flex flex-wrap justify-content-end">
    <div class="app-auth-background">

    </div>
    <div class="app-auth-container">
        <h1 href="index.html">Sign In</h1>
        <p class="auth-description">Please enter your credentials to log in.<br>Not registered yet? <a href="${pageContext.request.contextPath}/controller?command=to_signup_page">Sign Up</a></p>
        <c:if test="${not empty message}">
            <div class="alert alert-danger alert-style-light" role="alert">
                <c:out value="${message}"/>
            </div>
        </c:if>
        <form action="/controller" method="post">
            <div class="auth-credentials m-b-xxl">
                <label for="signInEmail" class="form-label">Email address</label>
                <input type="email" name="login_email" class="form-control m-b-md" id="signInEmail" aria-describedby="signUpEmail" >

                <label for="signUpPassword" class="form-label">Password</label>
                <input type="password" name="login_password" class="form-control m-b-md" id="signUpPassword" aria-describedby="signUpPassword">
            </div>

            <div class="auth-submit">
                <input type="submit" class="btn btn-primary" value="Sign In">
            </div>
            <input type="hidden" name="command" value="signin">
        </form>

        <div class="divider"></div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
</body>
</html>
