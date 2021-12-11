create table t_order
(
    id           bigint not null auto_increment,
    create_time  bigint,
    deduction    integer,
    details      text,
    order_status integer,
    total_price  integer,
    update_time  bigint,
    primary key (id)
);