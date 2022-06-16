package by.lukyanov.finaltask.command;

public final class PagePath {
    public static final String MAIN_PAGE = "index.jsp";
    public static final String SIGNUP_PAGE = "pages/signup.jsp";
    public static final String ERROR_404 = "pages/error/404_error.jsp";
    public static final String ERROR_500 = "pages/error/404_error.jsp";
    public static final String SIGNIN_PAGE = "pages/signin.jsp";
    public static final String ADMIN_ALL_USERS = "pages/admin/users/all-users.jsp";
    public static final String ADMIN_SEARCH_USERS_RESULTS = "pages/admin/users/user-search-results.jsp";
    public static final String ADMIN_ALL_ORDERS = "pages/admin/orders/all-orders.jsp";
    public static final String ADMIN_SEARCH_ORDERS_RESULTS = "pages/admin/orders/order-search-results.jsp";
    public static final String ADMIN_COMPLETED_ORDERS = "pages/admin/orders/completed-orders.jsp";
    public static final String ADMIN_PROCESSING_ORDERS = "pages/admin/orders/rental_requests.jsp";
    public static final String ADMIN_DECLINE_ORDER = "pages/admin/orders/decline-order.jsp";
    public static final String ADMIN_ORDER_REPORT = "pages/admin/orders/order-report.jsp";
    public static final String ADMIN_ADD_EDIT_USER = "pages/admin/users/add-edit-user.jsp";
    public static final String UNVERIFIED_USERS = "pages/admin/users/unverified-users.jsp";
    public static final String ADMIN_SUCCESS_PAGE = "pages/admin/util/success-page.jsp";
    public static final String ADMIN_FAIL_PAGE = "pages/admin/util/fail-page.jsp";
    public static final String ADMIN_ALL_CARS = "pages/admin/cars/all-cars.jsp";
    public static final String ADMIN_SEARCH_CARS_RESULTS = "pages/admin/cars/car-search-results.jsp";
    public static final String ADMIN_REPAIRING_CARS = "pages/admin/cars/repairing-cars.jsp";
    public static final String ADMIN_ADD_NEW_CAR = "pages/admin/cars/add-new-car.jsp";
    public static final String ADMIN_EDIT_CAR = "pages/admin/cars/edit-car.jsp";
    public static final String CAR_CATEGORY_PAGE = "pages/car-category.jsp";
    public static final String ADMIN_ALL_CAR_CATEGORIES = "pages/admin/cars/all-categories.jsp";
    public static final String ADMIN_ADD_CAR_CATEGORY = "pages/admin/cars/add-new-car-category.jsp";
    public static final String ADMIN_EDIT_CAR_CATEGORY = "pages/admin/cars/edit-car-category.jsp";
    public static final String REFILL_BALANCE = "pages/refill-balance.jsp";
    public static final String SUCCESSFUL_REFILL_BALANCE = "pages/successful-refill.jsp";
    public static final String FAIL_REFILL_BALANCE = "pages/fail-refill.jsp";
    public static final String USER_ACCOUNT = "pages/user-account.jsp";
    public static final String USER_ACCOUNT_ORDERS = "pages/user-account-orders.jsp";
    public static final String CAR_PAGE = "pages/car-page.jsp";
    public static final String CATEGORIES_PAGE = "pages/shop-categories.jsp";
    public static final String SUCCESSFUL_ORDER = "pages/order/successful-order.jsp";
    public static final String FAILED_ORDER = "pages/order/failed-order.jsp";
    public static final String ORDER_REPORT = "pages/order/return-order-form.jsp";

    public static final String TO_LOG_OUT = "controller?command=log_out";
    public static final String TO_CAR_PAGE = "controller?command=to_car_page";
    public static final String TO_USER_ORDERS = "controller?command=find_all_user_orders";
    public static final String TO_CAR_CATEGORY_PAGE = "controller?command=to_car_category_page";
    public static final String TO_ALL_CARS_CATEGORY_PAGE = "controller?command=to_all_cars";
    public static final String TO_ADMIN_UNVERIFIED_USERS = "controller?command=admin_to_unverified_users";
    public static final String TO_ADMIN_PROCESSING_ORDERS = "controller?command=admin_find_processing_orders";
    public static final String TO_ADMIN_ALL_ORDERS = "controller?command=admin_find_all_orders";
    public static final String TO_ADMIN_ALL_CARS = "controller?command=admin_to_all_cars";
    public static final String TO_ADMIN_ALL_USERS = "controller?command=admin_to_all_users";
    public static final String TO_ADMIN_COMPLETED_ORDERS = "controller?command=admin_find_completed_orders";
    public static final String TO_ADMIN_EDIT_CAR = "controller?command=admin_to_edit_car";
    public static final String TO_ADMIN_SEARCH_CAR = "controller?command=admin_search_car";
    public static final String TO_ADMIN_SEARCH_USER = "controller?command=admin_search_user";
    public static final String TO_ADMIN_SEARCH_ORDER = "controller?command=admin_search_order";
    public static final String TO_ADMIN_REPAIRING_CARS = "controller?command=admin_to_repairing_cars";
    public static final String TO_ADMIN_SHOW_ORDER_REPORT = "controller?command=admin_show_order_report";
    public static final String TO_ADMIN_EDIT_USER = "controller?command=admin_to_edit_user";
    public static final String TO_ADMIN_DECLINE_ORDER = "controller?command=admin_to_decline_order";
    public static final String TO_FIND_ALL_USER_ORDERS = "controller?command=find_all_user_orders";
    public static final String TO_GO_USER_ACCOUNT = "controller?command=to_user_account";
    public static final String TO_GO_RETURN_CAR = "controller?command=to_return_car";
    public static final String TO_ADMIN_ALL_CATEGORIES = "controller?command=admin_to_all_car_categories";
    public static final String TO_ADMIN_EDIT_CATEGORY = "controller?command=admin_to_edit_car_category";
    public static final String TO_ADMIN_ADD_NEW_CAR = "controller?command=admin_to_add_new_car";
    public static final String TO_SIGN_IN = "controller?command=to_sign_in_page";

    private PagePath() {
    }
}
