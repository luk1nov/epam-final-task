<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title>Car page</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Audiowide&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/flatpickr/flatpickr.min.css" rel="stylesheet">
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
                            <div class="page-description">

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="example-content row g-3">
                                            <div class="col-8">
                                                <c:if test="${car.image ne null}">
                                                    <img src="data:image/jpg;base64,${car.image}" class="full-width rounded"/>
                                                </c:if>
                                                <c:if test="${car.image eq null}">
                                                    <img src="../resources/images/car-coming-soon.png" class="full-width rounded"/>
                                                </c:if>
                                            </div>
                                            <div class="col-4">
                                                <h1 class="mb-3"><c:out value="${car.brand}"/> <c:out value="${car.model}"/></h1>
                                                <p><fmt:message key="label.car_acceleration"/> 0-100: <c:out value="${car.info.acceleration}"/>s</p>
                                                <p><fmt:message key="label.car_power"/>: <c:out value="${car.info.power}"/>hp</p>
                                                <p><fmt:message key="label.car_drivetrain"/>: <c:out value="${car.info.drivetrain}"/></p>
                                                <c:if test="${car.salePrice.isEmpty()}">
                                                    <p class="bold">Price per day: $<c:out value="${car.regularPrice}"/></p>
                                                </c:if>
                                                <c:if test="${car.salePrice.isPresent()}">
                                                    <p class="bold">Price per day: $<c:out value="${car.salePrice.get()}"/></p>
                                                </c:if>
                                                <form class="mb-3" action="/controller" method="POST">
                                                    <input name="orderDateRange" class="form-control flatpickr1 mb-4" type="text" placeholder="<fmt:message key='label.date_label'/>">
                                                    <h4 id="final_price" class="mb-4">Final price: choose rent date</h4>
                                                    <input type="hidden" id="hidden_price" name="hiddenPrice">
                                                    <input type="hidden" name="command" value="create_order">
                                                    <input type="hidden" name="carId" value="<c:out value='${car.id}'/>">
                                                    <input type="submit" class="btn btn-primary" value="Rent"/>
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
</div>

<!-- Javascripts -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/flatpickr/flatpickr.js"></script>
<script src="https://npmcdn.com/flatpickr/dist/l10n/ru.js"></script>
<script>
    const final_price_block = $("#final_price");
    const default_price_text = "Final price: choose rent date";
    const hidden_price = $("#hidden_price");
    $(".flatpickr1").flatpickr({
        locale: "<fmt:message key='option.flatpickr_locale'/>",
        mode:"range",
        altInput: true,
        minDate: "today",
        altFormat: "j F Y",
        disable: [
            <c:forEach var="order" items="${list}">
            {
                from: "<c:out value='${order.beginDate}'/>",
                to: "<c:out value='${order.endDate}'/>"
            },
            </c:forEach>
        ],
        onChange: function(selectedDates, dateStr, instance) {
            var price_text = default_price_text;
            console.log(dateStr);
            if (selectedDates.length == 2){
                var time_difference = selectedDates[1] - selectedDates[0];
                var days = (time_difference / (1000 * 60 * 60 * 24)) + 1;
                <c:if test="${car.salePrice.isEmpty()}">
                var price = days * <c:out value="${car.regularPrice}"/>;
                </c:if>
                <c:if test="${car.salePrice.isPresent()}">
                var price = days * <c:out value="${car.salePrice.get()}"/>;
                </c:if>
                price_text = "Final price: $" + price;
                hidden_price.val(price).change();
            }
            final_price_block.text(price_text);
        },
    });
</script>
<script></script>
</body>
</html>