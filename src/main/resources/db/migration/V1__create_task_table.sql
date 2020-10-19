create table task (
    id int not null auto_increment primary key,
    name varchar(255) not null unique,
    done bool default 0
);

insert into task (name) values ('Task 1');
insert into task (name) values ('Task 2');
insert into task (name) values ('Task 3');
insert into task (name) values ('Task 4');