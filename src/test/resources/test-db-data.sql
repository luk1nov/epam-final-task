delete from order_report;
delete from orders;
delete from car_info;
delete from cars;
delete from users;
delete from car_category;


insert into car_category (car_category_title)
values ('Cars'),
       ('Premium');

insert into cars (brand, model, vin_code, regular_price, is_active, car_category_car_category_id)
values ('Audi', 'A5', 'ZFA22304575556869', 1000, 1, 1),
       ('BMW', 'M8', 'ZFA13533698421778', 240, 0, 1);

insert into car_info (acceleration, power, drivetrain, cars_car_id)
values (3.3, 532, 'AWD', 1),
       (4.3, 745, 'RWD', 2);

insert into users (email, password, name, surname, user_status, user_role, phone, balance)
values ('abv@gmail.com','$argon2i$v=19$m=65536,t=2,p=1$jRD/TWkM7evtnJC9VvL7UQ$2egFe9MB9PHx3wDhq3whg6RaiVkg1AziTp7qcksSn+0', 'David', 'Lukyanov', 'ACTIVE', 'ADMIN', '+375-29-570-06-66', 1499),
       ('dsa@gmail.com','$argon2i$v=19$m=65536,t=2,p=1$jRD/TWkM7evtnJC9VvL7UQ$2egFe9MB9PHx3wDhq3whg6RaiVkg1AziTp7qcksSn+0', 'Alex', 'Petrov', 'INACTIVE', 'USER', '+375-29-570-07-66', 0);

insert into orders (begin_date, end_date, order_status, users_user_id, cars_car_id, price)
values ('2022-07-02', '2022-07-05', 'FINISHED', 1, 1, 1500),
       ('2022-07-05', '2022-07-06', 'ACTIVE', 1, 1, 1000);

insert into order_report (report_text, report_status, orders_order_id)
values ('ok', 'WITHOUT_DEFECTS', 1);

