<div class="app-sidebar">
    <div class="logo">
        <div href="index.html" class="logo-icon"><span class="logo-text">CarRental</span></div>
    </div>
    <div class="app-menu">
        <ul class="accordion-menu">
            <li class="sidebar-title">
                Orders
            </li>
            <li>
                <a href="../orders/rental_requests.jsp"><i class="material-icons-two-tone">upload</i>Rental requests<span class="badge rounded-pill badge-danger float-end">5</span></a>
            </li>
            <li>
                <a href="../orders/return_requests.jsp"><i class="material-icons-two-tone">download</i>Return requests<span class="badge rounded-pill badge-warning float-end">14</span></a>
            </li>
            <li>
                <a href="calendar.html"><i class="material-icons-two-tone">check_circle</i>Completed orders</a>
            </li>
            <li class="sidebar-title">
                Users
            </li>
            <li>
                <a href="../users/add-new-user.jsp"><i class="material-icons-two-tone">person_add</i>Add new user</a>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="to_admin_all_users_page">
                    <i class="material-icons-two-tone">people</i>
                    <input type="submit" value="All users">
                </form>
            </li>
            <li class="sidebar-title">
                Cars
            </li>
            <li>
                <a href="../cars/add-new-car.jsp"><i class="material-icons-two-tone">directions_car</i>Add new car</a>
            </li>
            <li>
                <a href="../cars/all-cars.jsp" class="active"><i class="material-icons-two-tone">garage</i>All cars</a>
            </li>
            <li>
                <a href="../cars/repairing-cars.jsp"><i class="material-icons-two-tone">car_crash</i>Repairing cars<span class="badge rounded-pill badge-danger float-end">5</span></a>
            </li>
        </ul>
    </div>
</div>