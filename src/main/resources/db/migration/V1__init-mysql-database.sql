
    drop table if exists beer;

    drop table if exists customer;

    create table beer (
        beer_style tinyint check (beer_style between 0 and 10),
        price decimal(38,2) not null,
        quantity_on_hand integer,
        created_date datetime(6),
        update_date datetime(6),
        version bigint,
        id varchar(36) not null,
        beer_name varchar(255) not null,
        upc varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6),
        last_modified_date datetime(6),
        version bigint,
        id varchar(36) not null,
        customer_name varchar(255),
        primary key (id)
    ) engine=InnoDB;
