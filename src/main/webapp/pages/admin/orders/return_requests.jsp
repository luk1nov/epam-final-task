<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 30.03.2022
  Time: 0:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Responsive Admin Dashboard Template">
    <meta name="keywords" content="admin,dashboard">
    <meta name="author" content="stacks">
    <!-- The above 6 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Title -->
    <title>Return requests</title>

    <!-- Styles -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/plugins/perfectscroll/perfect-scrollbar.css" rel="stylesheet">

    <!-- Theme Styles -->
    <link href="${pageContext.request.contextPath}/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/neptune.png" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
                                <h1>Return requests</h1>
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
                                            <th scope="col">User</th>
                                            <th scope="col">Car</th>
                                            <th scope="col">Rent eds</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td><a href="#">Mark Otto</a></td>
                                            <td><a href="#">Bentley Continental 2019</a></td>
                                            <td>27/03/2022 19:51</td>
                                            <td><span class="badge badge-style-light rounded-pill badge-danger">Overdue</span></td>
                                            <td>
                                                <button type="button" class="btn btn-primary">Accept</button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><a href="#">Mark Otto</a></td>
                                            <td><a href="#">Bentley Continental 2019</a></td>
                                            <td>27/03/2022 19:51</td>
                                            <td><span class="badge badge-style-light rounded-pill badge-success">In use</span></td>
                                            <td>
                                                <button type="button" class="btn btn-primary">Accept</button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><a href="#">Mark Otto</a></td>
                                            <td><a href="#">Bentley Continental 2019</a></td>
                                            <td>27/03/2022 19:51</td>
                                            <td><span class="badge badge-style-light rounded-pill badge-success">In use</span></td>
                                            <td>
                                                <button type="button" class="btn btn-primary">Accept</button>
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
