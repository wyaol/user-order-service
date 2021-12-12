create table t_order
(
    id           bigint not null auto_increment,
    create_time  bigint,
    deduction    integer,
    orderDetails      text,
    order_status integer,
    total_price  integer,
    user_id  integer,
    merchant_id  integer,
    update_time  bigint,
    primary key (id)
);