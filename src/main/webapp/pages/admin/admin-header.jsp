<div class="app-header">
    <nav class="navbar navbar-light navbar-expand-lg">
        <div class="container-fluid">
            <div class="navbar-nav" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link hide-sidebar-toggle-button" href="#"><i class="material-icons">first_page</i></a>
                    </li>
                    <li class="nav-item admin-home-link">
                        <form action="/controller" method="POST">
                            <input type="hidden" name="command" value="default">
                            <input type="submit" value="Back to site">
                        </form>
                    </li>
                </ul>
            </div>
            <div class="d-flex">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link hide-sidebar-toggle-button" href="#">Log out</a>
                    </li>
                    <li class="nav-item hidden-on-mobile">
                        <a class="nav-link language-dropdown-toggle" href="#" id="languageDropDown" data-bs-toggle="dropdown"><img src="${pageContext.request.contextPath}/images/flags/us.png" alt=""></a>
                        <ul class="dropdown-menu dropdown-menu-end language-dropdown" aria-labelledby="languageDropDown">
                            <li><a class="dropdown-item" href="#"><img src="${pageContext.request.contextPath}/images/flags/russia.png" alt="">Russian</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>