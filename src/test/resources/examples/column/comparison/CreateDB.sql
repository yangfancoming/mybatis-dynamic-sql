

drop table ColumnComparison if exists;

create table ColumnComparison (
  number1 int not null,
  number2 int not null,
  primary key(number1, number2)
);

insert into ColumnComparison values(1, 11);
insert into ColumnComparison values(2, 10);
insert into ColumnComparison values(3, 9);
insert into ColumnComparison values(4, 8);
insert into ColumnComparison values(5, 7);
insert into ColumnComparison values(6, 6);
insert into ColumnComparison values(7, 5);
insert into ColumnComparison values(8, 4);
insert into ColumnComparison values(9, 3);
insert into ColumnComparison values(10, 2);
insert into ColumnComparison values(11, 1);
