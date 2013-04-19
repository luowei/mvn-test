drop database if exists db_test;
create database db_test;
use db_test;

drop table if exists user_roles;
drop table if exists users;
drop table if exists roles_permissions;
drop table if exists roles;
drop table if exists permissions;
drop table if exists menu;


create table users(
  id bigint(20) primary key auto_increment,
  username varchar(100) not null,
  password varchar(92) not null,
  email varchar(50)
);

create table roles(
  id bigint(20) primary key auto_increment,
  role_name varchar(30) not null,
  role_description varchar(100),
  create_time timestamp not null

);

create table user_roles(
  id bigint(20) primary key auto_increment,
  user_id bigint(20) not null,
  role_id bigint(20) not null,

  constraint uq_user_roles unique(user_id, role_id)
);

create table permissions(
  id bigint(20) primary key auto_increment,
  permission varchar(100) not null,
  description varchar(50) not null,
  permission_group_id bigint(20),

  constraint fk_permissions_permission_group_id foreign key(permission_group_id) references permissions(id)
);

create table roles_permissions(
  role_id bigint(20) not null,
  permissions_id bigint(20) not null,

  constraint pk_roles_permissions primary key(role_id, permissions_id)
);

create table menu(
  id int(11) primary key auto_increment,
  name varchar(50) not null,
  url varchar(200) default null,
  create_time timestamp not null,
  sequence varchar(50),
  parent_id int(11)
);

-- clong 110, user1 user1, admin admin
insert into users(id, username, password, email) values (1, 'user1','$shiro1$SHA-256$500000$Cz8CvbpUrpgkk+k8puy3iA==$VRXptpQeeCwzYDTq+ZEr8rrTFFUIIan/Xk5jwHXFRYg=', 'user1@test.com');
insert into users(id, username, password, email) values (2, 'admin','$shiro1$SHA-256$500000$DWirWtjc+NmEBW83qYr2gQ==$917j5L+RHCd6IKJ24nrSxaGXdelol8lVrzVlJrLsl3w=', 'admin@test.com');
insert into users(id, username, password, email) values (3, 'user2','$shiro1$SHA-256$500000$l48hH1mNJTZC35z6YPyj0w==$FyrwtiltMAdv7bwghfmGzqReJFliYcocbgiZkSaavMU=', 'user2@test.com');
insert into users(id, username, password, email) values (4, 'clong','$shiro1$SHA-256$500000$gYP8/KguX0+ufjYHw0Y83A==$Kg/HdrlYiwSZ7LPs/rq6jikgan9W7bzOFycwDFr4klE=', 'clong@test.com');




insert into roles(id, role_name, role_description, create_time) values (1, 'user', '用户', '2012-04-01');
insert into roles(id, role_name, role_description, create_time) values (2, 'admin', '管理员', '2012-04-02');
insert into roles(id, role_name, role_description, create_time) values (3, 'customer', '顾客', '2012-04-02');


insert into permissions(id, permission, description, permission_group_id) values (1, '*','超级权限', null);

insert into permissions(id, permission, description, permission_group_id) values (2, 'user:manage','用户管理', null);
insert into permissions(id, permission, description, permission_group_id) values (3, 'user:create','创建用户', 2);
insert into permissions(id, permission, description, permission_group_id) values (4, 'user:read','查询用户', 2);
insert into permissions(id, permission, description, permission_group_id) values (5, 'user:update','更新用户', 2);
insert into permissions(id, permission, description, permission_group_id) values (6, 'user:delete','删除用户', 2);
insert into permissions(id, permission, description, permission_group_id) values (7, 'user:assignRoles','分配角色', 2);

insert into permissions(id, permission, description, permission_group_id) values (8, 'menu:manage','菜单管理', null);
insert into permissions(id, permission, description, permission_group_id) values (9, 'menu:create','创建菜单', 8);
insert into permissions(id, permission, description, permission_group_id) values (10, 'menu:read','查询菜单', 8);
insert into permissions(id, permission, description, permission_group_id) values (11, 'menu:update','更新菜单', 8);
insert into permissions(id, permission, description, permission_group_id) values (12, 'menu:delete','删除菜单', 8);





insert into roles_permissions(role_id, permissions_id) values (1, 3);
insert into roles_permissions(role_id, permissions_id) values (1, 4);
insert into roles_permissions(role_id, permissions_id) values (1, 5);
insert into roles_permissions(role_id, permissions_id) values (1, 7);
insert into roles_permissions(role_id, permissions_id) values (1, 10);
insert into roles_permissions(role_id, permissions_id) values (1, 11);

insert into roles_permissions(role_id, permissions_id) values (2, 1);

insert into roles_permissions(role_id, permissions_id) values (3, 4);
insert into roles_permissions(role_id, permissions_id) values (3, 10);


insert into user_roles (user_id, role_id) values (1, 1);
insert into user_roles (user_id, role_id) values (2, 1);
insert into user_roles (user_id, role_id) values (3, 2);
insert into user_roles (user_id, role_id) values (4, 3);



insert into menu(name, url, create_time, sequence, parent_id) values ('用户管理', '', '2012-03-04', '1', 0);
insert into menu(name, url, create_time, sequence, parent_id) values ('人员管理', 'manage/user/list', '2012-03-04', '1.1', 1);
insert into menu(name, url, create_time, sequence, parent_id) values ('角色管理', 'manage/role/list', '2012-03-04', '1.2', 1);
insert into menu(name, url, create_time, sequence, parent_id) values ('权限管理', 'manage/permissions/list', '2012-03-04', '1.4', 1);
insert into menu(name, url, create_time, sequence, parent_id) values ('菜单管理', 'manage/menu/list', '2012-03-04', '1.5', 1);


insert into menu(name, url, create_time, sequence, parent_id) values ('综合管理', '', '2012-03-05', '2', 0);
insert into menu(name, url, create_time, sequence, parent_id) values ('test1', 'hello.jsp', '2012-03-05', '2.1', 6);

insert into menu(name, url, create_time, sequence, parent_id) values ('aa管理', '', '2012-03-05', '3', 0);
insert into menu(name, url, create_time, sequence, parent_id) values ('bb管理', '', '2012-03-05', '4', 0);
insert into menu(name, url, create_time, sequence, parent_id) values ('cc管理', '', '2012-03-05', '5', 0);
insert into menu(name, url, create_time, sequence, parent_id) values ('dd管理', '', '2012-03-05', '6', 0);
insert into menu(name, url, create_time, sequence, parent_id) values ('ee管理', '', '2012-03-05', '7', 0);
insert into menu(name, url, create_time, sequence, parent_id) values ('杂项', '', '2012-03-05', '8', 0);
insert into menu(name, url, create_time, sequence, parent_id) values ('生成密码', 'pwdGen', '2012-03-05', '8.1', 13);
