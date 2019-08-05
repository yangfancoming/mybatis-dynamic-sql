

drop schema schema1 if exists cascade;
drop schema schema2 if exists cascade;

create schema schema1
  create table User (
    user_id int not null,
    user_name varchar(30) not null,
    primary key (user_id)
  );

create schema schema2
  create table User (
    user_id int not null,
    user_name varchar(30) not null,
    primary key (user_id)
  );
