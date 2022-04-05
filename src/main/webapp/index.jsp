<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Car Rental</title>
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
        <div class="app-header" style="width: 100%;">
            <nav class="navbar navbar-light navbar-expand-lg">
                <div class="container-fluid">
                    <div class="navbar-nav" id="navbarNav">
                        <a href="${pageContext.request.contextPath}/controller?command=default" class="logo" style="text-decoration: none">Car Rental</a>
                    </div>
                    <div class="d-flex">
                        <ul class="navbar-nav">
                            <li class="nav-item hidden-on-mobile">
                                <a class="nav-link" href="#">Cars</a>
                            </li>
                            <li class="nav-item hidden-on-mobile">
                                <a class="nav-link" href="#">E-Cars</a>
                            </li>
                            <li class="nav-item hidden-on-mobile">
                                <a class="nav-link" href="#">Premium</a>
                            </li>
                        </ul>
                    </div>
                    <div class="d-flex">
                        <ul class="navbar-nav">
                            <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}" >
                                <li class="nav-item hidden-on-mobile" style="align-self: center;padding: 0 10px;">
                                    <form action="/controller" method="POST">
                                        <input type="hidden" name="command" value="to_admin_all_users_page">
                                        <input type="submit" class="btn btn-outline-info" value="Admin panel">
                                    </form>
                                </li>
                            </c:if>
                            <c:if test="${empty sessionScope.user}" >
                                <li class="nav-item hidden-on-mobile">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=to_signup_page">Sign Up</a>
                                </li>
                                <li class="nav-item hidden-on-mobile">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=to_signin_page">Log In</a>
                                </li>
                            </c:if>
                            <c:if test="${not empty sessionScope.user}" >
                                <li class="nav-item hidden-on-mobile">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=log_out">Log Out</a>
                                </li>
                            </c:if>
                            <li class="nav-item hidden-on-mobile">
                                <a class="nav-link language-dropdown-toggle" href="#" id="languageDropDown" data-bs-toggle="dropdown"><img src="${pageContext.request.contextPath}/resources/images/flags/us.png" alt=""></a>
                                <ul class="dropdown-menu dropdown-menu-end language-dropdown" aria-labelledby="languageDropDown">
                                    <li><a class="dropdown-item" href="#"><img src="${pageContext.request.contextPath}/resources/images/flags/russia.png" alt="">Russian</a></li>
                                </ul>
                            </li>

                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1>Full-width Header</h1>
                                <span>Header without spacing to sidebar and page edges. <div
                                        class="alert alert-secondary m-t-lg" role="alert">Note! Logo block with user
                                            info in it is not compatible with full-width header.</div></span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="card">
                                <img src="${pageContext.request.contextPath}/resources/images/cards/card.png" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">Card title</h5>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <a href="#" class="btn btn-primary">Go somewhere</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card">
                                <img src="${pageContext.request.contextPath}/resources/images/cards/card.png" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">Card title</h5>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <a href="#" class="btn btn-primary">Go somewhere</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card">
                                <img src="${pageContext.request.contextPath}/resources/images/cards/card.png" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">Card title</h5>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <a href="#" class="btn btn-primary">Go somewhere</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card">
                                <img src="${pageContext.request.contextPath}/resources/images/cards/card.png" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">Card title</h5>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <a href="#" class="btn btn-primary">Go somewhere</a>
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
</body>
</html>