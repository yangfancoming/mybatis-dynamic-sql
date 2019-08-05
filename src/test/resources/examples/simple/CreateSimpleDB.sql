

drop table SimpleTable if exists;

create table SimpleTable (
   id int not null,
   first_name varchar(30) not null,
   last_name varchar(30) not null,
   birth_date date not null,
   employed varchar(3) not null,
   occupation varchar(30) null,
   primary key(id)
);

insert into SimpleTable values(1, 'Fred', 'Flintstone', '1935-02-01', 'Yes', 'Brontosaurus Operator');
insert into SimpleTable values(2, 'Wilma', 'Flintstone', '1940-02-01', 'Yes', 'Accountant');
insert into SimpleTable(id, first_name, last_name, birth_date, employed) values(3, 'Pebbles', 'Flintstone', '1960-05-06', 'No');
insert into SimpleTable values(4, 'Barney', 'Rubble', '1937-02-01', 'Yes', 'Brontosaurus Operator');
insert into SimpleTable values(5, 'Betty', 'Rubble', '1943-02-01', 'Yes', 'Engineer');
insert into SimpleTable(id, first_name, last_name, birth_date, employed) values(6, 'Bamm Bamm', 'Rubble', '1963-07-08', 'No');
