<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new car</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/highlight/styles/github-gist.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/select2/css/select2.min.css" rel="stylesheet">

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
                                <h1>Edit car</h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mx-auto">
                            <div class="card">
                                <div class="card-body">
                                    <div class="example-content">
                                        <form class="row g-3" action="/controller" method="POST">
                                            <div class="col-md-6">
                                                <label for="inputBrand" class="form-label">Brand</label>
                                                <select name="carBrand" class="js-states form-control" tabindex="-1" id="inputBrand" style="display: none; width: 100%">
                                                    <option value="Audi">Audi</option>
                                                    <option value="BMW">BMW</option>
                                                    <option value="Mercedes">Mercedes</option>
                                                    <option value="Toyota">Toyota</option>
                                                    <option value="Hyundai">Hyundai</option>
                                                    <option value="Mitsubishi">Mitsubishi</option>
                                                    <option value="Volkswagen">Volkswagen</option>
                                                    <option value="Bentley">Bentley</option>
                                                    <option value="Porsche">Porsche</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="inputModel" class="form-label">Model</label>
                                                <input name="carModel" type="text" class="form-control" id="inputModel" value="${car.model}">
                                            </div>
                                            <div class="col-6">
                                                <label for="inputPower" class="form-label">Power</label>
                                                <input name="carInfoPower" type="text" class="form-control" id="inputPower" value="${car.info.power}">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputAcceleration" class="form-label">Acceleration 0-100</label>
                                                <input name="carInfoAcceleration" type="text" class="form-control" id="inputAcceleration" value="${car.info.acceleration}">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputDrivetrain" class="form-label">Drivetrain</label>
                                                <select name="carInfoDrivetrain" id="inputDrivetrain" class="form-select">
                                                    <option <c:if test="${car.info.drivetrain == 'AWD'}">selected</c:if>>AWD</option>
                                                    <option <c:if test="${car.info.drivetrain == 'RWD'}">selected</c:if>>RWD</option>
                                                    <option <c:if test="${car.info.drivetrain == 'FWD'}">selected</c:if>>FWD</option>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <label for="inputPrice" class="form-label">Price</label>
                                                <input name="carRegularPrice" type="text" class="form-control" id="inputPrice" value="${car.regularPrice}">
                                            </div>
                                            <div class="col-md-4">
                                                <label for="inputSalePrice" class="form-label">Sale Price</label>
                                                <input name="carSalePrice" type="text" class="form-control" id="inputSalePrice" value="<c:if test="${car.salePrice.isPresent()}">${car.salePrice.get()}</c:if>">
                                            </div>
                                            <div class="col-md-4">
                                                <label for="inputActive" class="form-label">Active</label>
                                                <select name="carActive" id="inputActive" class="form-select">
                                                    <option value="true" <c:if test="${car.active == true}">selected</c:if>>Active</option>
                                                    <option value="false" <c:if test="${car.active == false}">selected</c:if>>Repair</option>
                                                </select>
                                            </div>
                                            <%--<div class="col-md-12">
                                                <label for="formFile" class="form-label">Photo</label>
                                                <input class="form-control" type="file" id="formFile">
                                            </div>--%>
                                            <input type="hidden" name="command" value="edit_car">
                                            <input type="hidden" name="carId" value="${car.id}">
                                            <div class="col-12">
                                                <input type="submit" class="btn btn-primary" value="Save"/>
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
<script src="${pageContext.request.contextPath}/resources/plugins/select2/js/select2.full.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/highlight/highlight.pack.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
<script>
    document.querySelector('#inputBrand option[value="${car.brand}"]').setAttribute('selected', 'selected');
    $('#inputBrand').select2();
</script>
</body>
</html>
