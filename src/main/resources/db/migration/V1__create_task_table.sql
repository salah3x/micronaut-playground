create table Task
(
    id   int          not null auto_increment primary key,
    name varchar(255) not null unique,
    done bool default 0
);

insert into Task (name)
values ('Task 1');
insert into Task (name)
values ('Task 2');
insert into Task (name)
values ('Task 3');
insert into Task (name)
values ('Task 4');