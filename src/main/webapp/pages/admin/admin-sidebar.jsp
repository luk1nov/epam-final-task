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
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_find_all_orders">
                    <i class="material-icons-two-tone">list_alt</i>
                    <input type="submit" value="All orders">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_find_processing_orders">
                    <i class="material-icons-two-tone">upload</i>
                    <input type="submit" value="Rental requests">
                </form>
            </li>
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_find_completed_orders">
                    <i class="material-icons-two-tone">check_circle</i>
                    <input type="submit" value="Completed orders">
                </form>
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
            <li>
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_unverified_users">
                    <i class="material-icons-two-tone">manage_accounts</i>
                    <input type="submit" value="Unverified users">
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
                <form action="/controller" method="POST">
                    <input type="hidden" name="command" value="admin_to_repairing_cars">
                    <i class="material-icons-two-tone">car_crash</i>
                    <input type="submit" value="Repairing cars">
                </form>
            </li>
        </ul>
    </div>
</div>