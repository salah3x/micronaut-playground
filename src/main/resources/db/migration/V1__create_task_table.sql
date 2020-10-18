create table task (
    id varchar(36) not null primary key,
    name varchar(255) not null unique,
    done bit default 0
);

insert into task (id, name) values ('09183c75-a8b6-48a6-99dc-a17255432e25', 'Task 1');
insert into task (id, name) values ('13b0b549-544b-475c-a748-76823af30734', 'Task 2');
insert into task (id, name) values ('25d35483-310a-4b78-959b-b38a5603346b', 'Task 3');
insert into task (id, name) values ('6ace5ff2-21fc-4cad-a6b4-7eb0e876fe5f', 'Task 4');