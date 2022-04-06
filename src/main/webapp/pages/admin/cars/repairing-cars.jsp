<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Repairing cars</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/plugins/perfectscroll/perfect-scrollbar.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/neptune.png" />

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
                                <h1>Repairing cars</h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-body">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th scope="col">Brand</th>
                                            <th scope="col">Model</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">0-100</th>
                                            <th scope="col">Drivetrain</th>
                                            <th scope="col">Power</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>Audi</td>
                                            <td>RS5 2019</td>
                                            <td><span class="badge badge-style-light rounded-pill badge-danger">Repair</span></td>
                                            <td>4.2s</td>
                                            <td>AWD</td>
                                            <td>637hp</td>
                                            <td>$499.00</td>
                                            <td>
                                                <div class="btn-group dropstart">
                                                    <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                        Action
                                                    </button>
                                                    <ul class="dropdown-menu">
                                                        <li><a class="dropdown-item" href="edit_car.html">Edit</a></li>
                                                        <li><a class="dropdown-item" href="#">Delete</a></li>
                                                        <li><a class="dropdown-item" href="#">Send for repair</a></li>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Mercedes</td>
                                            <td>E63s 2021</td>
                                            <td><span class="badge badge-style-light rounded-pill badge-danger">Repair</span></td>
                                            <td>3.4s</td>
                                            <td>AWD</td>
                                            <td>737hp</td>
                                            <td>$649.00</td>
                                            <td>
                                                <div class="btn-group dropstart">
                                                    <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                        Action
                                                    </button>
                                                    <ul class="dropdown-menu">
                                                        <li><a class="dropdown-item" href="edit_car.html">Edit</a></li>
                                                        <li><a class="dropdown-item" href="#">Delete</a></li>
                                                        <li><a class="dropdown-item" href="#">Send for repair</a></li>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>BMW</td>
                                            <td>M5 CS 2022</td>
                                            <td><span class="badge badge-style-light rounded-pill badge-danger">Repair</span></td>
                                            <td>3.3s</td>
                                            <td>AWD</td>
                                            <td>823hp</td>
                                            <td><del>$799.00</del> $499.00</td>
                                            <td>
                                                <div class="btn-group dropstart">
                                                    <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                        Action
                                                    </button>
                                                    <ul class="dropdown-menu">
                                                        <li><a class="dropdown-item" href="edit_car.html">Edit</a></li>
                                                        <li><a class="dropdown-item" href="#">Delete</a></li>
                                                        <li><a class="dropdown-item" href="#">Finish repair</a></li>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
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
<script src="${pageContext.request.contextPath}/plugins/jquery/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/perfectscroll/perfect-scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/js/custom.js"></script>
</body>
</html>
