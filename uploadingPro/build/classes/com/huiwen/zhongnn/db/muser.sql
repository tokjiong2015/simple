use master
go
if exists(select * from sysdatabases where name='muser')
	drop database muser
go
create database muser
go
use muser
go

create table users
(
	userId int identity(1,1),
	username varchar(20),
	passwd varchar(20),
	email varchar(20),
	grade int
)
alter table users
	add constraint PK_userId primary key (userId)

insert into users values
('admin','admin','admin@sohu.com',1)

insert into users values
('admin2','admin2','admin2@sohu.com',1)

insert into users values
('admin3','admin3','admin3@sohu.com',2)

insert into users values
('admin4','admin4','admin4@sohu.com',2)

insert into users values
('admin5','admin5','admin5@sohu.com',2)


insert into users values
('admin6','admin','admin@sohu.com',1)

insert into users values
('admin7','admin2','admin2@sohu.com',1)

insert into users values
('admin8','admin3','admin3@sohu.com',2)

insert into users values
('admin9','admin4','admin4@sohu.com',2)

insert into users values
('admin10','admin5','admin5@sohu.com',2)
insert into users values
('admin11','admin','admin@sohu.com',1)

insert into users values
('admin12','admin2','admin2@sohu.com',1)

insert into users values
('admin13','admin3','admin3@sohu.com',2)

insert into users values
('admin14','admin4','admin4@sohu.com',2)

insert into users values
('admin15','admin5','admin5@sohu.com',2)
insert into users values
('admin16','admin','admin@sohu.com',1)

insert into users values
('admin17','admin2','admin2@sohu.com',1)

insert into users values
('admin18','admin3','admin3@sohu.com',2)

insert into users values
('admin19','admin4','admin4@sohu.com',2)

insert into users values
('admin20','admin5','admin5@sohu.com',2)
insert into users values
('admin21','admin','admin@sohu.com',1)

insert into users values
('admin22','admin2','admin2@sohu.com',1)

insert into users values
('admin23','admin3','admin3@sohu.com',2)

insert into users values
('admin24','admin4','admin4@sohu.com',2)

insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)
insert into users values
('admin25','admin5','admin5@sohu.com',2)

insert into users values
('12admin25234','admin5','admin5@sohu.com',2)
insert into users values
('234admin25234','admin5','admin5@sohu.com',2)
insert into users values
('234admin25234','admin5','admin5@sohu.com',2)

select * from users

use muser
go

select top 1 * from users where username = 'admin'

select userId from users where username='admin25'

select top 5  * from users  where userId not in (select top 10 userId from users where username='admin25') and username='admin25'

select top 5  * from users where userId not in (select top 5 userId from users where username like '%admin25%') and username like '%admin25%'