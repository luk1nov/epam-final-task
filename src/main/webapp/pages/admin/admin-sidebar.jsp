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
                <a href="orders/rental_requests.jsp"><i class="material-icons-two-tone">upload</i>Rental requests</a>
            </li>
            <li>
                <a href="orders/return_requests.jsp"><i class="material-icons-two-tone">download</i>Return requests</a>
            </li>
            <li>
                <a href="calendar.html"><i class="material-icons-two-tone">check_circle</i>Completed orders</a>
            </li>
            <li class="sidebar-title">
                Users
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_add_new_user">
                    <i class="material-icons-two-tone">person_add</i>
                    <input type="submit" value="Add new user">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_all_users">
                    <i class="material-icons-two-tone">people</i>
                    <input type="submit" value="All users">
                </form>
            </li>
            <li class="sidebar-title">
                Cars
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_all_car_categories">
                    <i class="material-icons-two-tone">category</i>
                    <input type="submit" value="All categories">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_add_new_car">
                    <i class="material-icons-two-tone">directions_car</i>
                    <input type="submit" value="Add new car">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_all_cars">
                    <i class="material-icons-two-tone">garage</i>
                    <input type="submit" value="All cars">
                </form>
            </li>
            <li>
                <a href="cars/repairing-cars.jsp"><i class="material-icons-two-tone">car_crash</i>Repairing cars</a>
            </li>
        </ul>
    </div>
</div>