

drop table GeneratedAlways if exists;

create table GeneratedAlways (
   id int not null,
   first_name varchar(30) not null,
   last_name varchar(30) not null,
   age integer null,
   full_name varchar(60) generated always as (first_name || ' ' || last_name),
   primary key(id)
);

insert into GeneratedAlways(id, first_name, last_name) values(1, 'Fred', 'Flintstone');
insert into GeneratedAlways(id, first_name, last_name) values(2, 'Wilma', 'Flintstone');
insert into GeneratedAlways(id, first_name, last_name) values(3, 'Pebbles', 'Flintstone');
insert into GeneratedAlways(id, first_name, last_name) values(4, 'Barney', 'Rubble');
insert into GeneratedAlways(id, first_name, last_name) values(5, 'Betty', 'Rubble');
insert into GeneratedAlways(id, first_name, last_name) values(6, 'Bamm Bamm', 'Rubble');
