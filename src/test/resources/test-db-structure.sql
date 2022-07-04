create table if not exists car_category (
    car_category_id bigint auto_increment primary key,
    car_category_title varchar(45) not null,
    unique (car_category_title)
);


create table if not exists cars (
    car_id bigint auto_increment primary key,
    brand varchar(45) not null,
    model varchar(45) not null,
    vin_code varchar(17) not null,
    regular_price decimal(7, 2) not null,
    sale_price decimal(7, 2) default null,
    is_active tinyint not null,
    image blob,
    car_category_car_category_id bigint not null,
    constraint vin_code_UNIQUE
        unique (vin_code),
    constraint fk_cars_car_category1
        foreign key (car_category_car_category_id)
            references car_category (car_category_id)
);


create table if not exists car_info (
    car_info_id bigint auto_increment primary key,
    acceleration double not null,
    power int not null,
    drivetrain enum('AWD', 'FWD', 'RWD') not null,
    cars_car_id bigint not null,
    constraint fk_car_info_cars1
        foreign key (cars_car_id)
            references cars (car_id) on delete cascade on update cascade
);

create table if not exists users (
     user_id bigint auto_increment primary key,
     email varchar(45) not null,
     password varchar(96) not null,
     name varchar(20) not null,
     surname varchar(45) not null,
     user_status enum('ACTIVE', 'VERIFICATION', 'INACTIVE', 'BLOCKED') not null default 'INACTIVE',
     user_role enum('ADMIN', 'MANAGER', 'USER', 'GUEST') not null default 'GUEST',
     driver_license_photo blob,
     phone varchar(20) not null,
     balance decimal(10) not null default '0',
     constraint email_UNIQUE
         unique (email)
);

create table if not exists orders (
    order_id bigint auto_increment primary key,
    begin_date date not null,
    end_date date not null,
    order_status enum('PROCESSING', 'ACTIVE', 'CANCELED', 'FINISHED') not null default 'PROCESSING',
    message varchar(200) default null,
    users_user_id bigint not null,
    cars_car_id bigint not null,
    price decimal(7, 2) not null default '0.00',
    constraint fk_orders_cars1
        foreign key (cars_car_id)
            references cars (car_id),
    constraint fk_orders_users
        foreign key (users_user_id)
            references users (user_id)
);


create table if not exists order_report (
    report_id bigint auto_increment primary key,
    report_photo blob,
    report_text varchar(200) default null,
    report_status enum('VISIBLE_DEFECT', 'TECHNICAL_DEFECT', 'WITHOUT_DEFECTS') not null,
    orders_order_id bigint not null,
    constraint orders_order_id_UNIQUE
        unique (orders_order_id),
    constraint fk_order_report_orders1
        foreign key (orders_order_id)
            references orders (order_id) on delete cascade on update cascade
);
