 -- drop database if exists proyecto_quickmart_inventory_kinal_in4av;
create database proyecto_quickmart_inventory_kinal_in4av;
use proyecto_quickmart_inventory_kinal_in4av;
 
-- ============================================================================
-- TABLAS
-- ============================================================================
 
create table Roles(
	nombre_rol varchar (50) not null check (length(nombre_rol)<=50),
    descripcion varchar (200) not null check (length(descripcion)<=200),
    id_rol varchar(36) not null,
    constraint pk_roles primary key (id_rol)
);
 
create table Users(
	name varchar (50) not null check (length(name)<=50),
    lastname varchar (50) not null check (length(lastname)<=50),
	email varchar (50) not null check (length(email)<=50),
    user varchar (25) not null check (length(user)<=25),
    password varchar (35) not null check (length(password)<=35),
    id_user varchar(36) not null,
    id_rol varchar(36) not null,
    constraint pk_users primary key (id_user),
    constraint fk_users_rol foreign key (id_rol) references Roles(id_rol)
);
 
-- ============================================================================
-- CREATE
 
delimiter $$
	create procedure sp_create_roles(in nombre_rol_p varchar(50),
									 in descripcion_p varchar(200))
    begin
		insert into Roles(nombre_rol, descripcion, id_rol)
			values(nombre_rol_p, descripcion_p, uuid());
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_create_users(in name_p varchar (50),
									 in lastname_p varchar(50),
                                     in email_p varchar(50),
                                     in user_p varchar (25),
                                     in password_p varchar(35),
                                     in id_rol_p varchar(36))
    begin
		insert into Users(name, lastname, email, user, password, id_user, id_rol)
			values(name_p, lastname_p, email_p, user_p, password_p, uuid(), id_rol_p);
    end$$
delimiter ; 
 
-- ============================================================================
-- MOSTRAR 
 
delimiter $$
	create procedure sp_mostrar_roles()
    begin
		select id_rol      as 'ID Rol',
               nombre_rol  as 'Nombre De Rol',
               descripcion as 'Descripcion'
        from Roles;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_mostrar_users()
    begin
		select id_user  as 'ID Usuario',
               name     as 'Nombre',
               lastname as 'Apellidos',
               email    as 'Email',
               user     as 'Usuario',
               id_rol   as 'ID Rol'
        from Users;
    end$$
delimiter ;
 
-- ============================================================================
-- LEER 
 
delimiter $$
	create procedure sp_leer_roles(in id_rol_p varchar(36))
    begin
		select id_rol      as 'ID Rol',
               nombre_rol  as 'Nombre De Rol',
               descripcion as 'Descripcion'
        from Roles
        where id_rol = id_rol_p;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_leer_users(in id_user_p varchar(36))
    begin
		select id_user  as 'ID Usuario',
               name     as 'Nombre',
               lastname as 'Apellidos',
               email    as 'Email',
               user     as 'Usuario',
               id_rol   as 'ID Rol'
        from Users
        where id_user = id_user_p;
    end$$
delimiter ;
 
-- ============================================================================
-- EDITAR
 
delimiter $$
	create procedure sp_editar_roles(in id_rol_p varchar(36),
									 in nombre_rol_p varchar(50),
                                     in descripcion_p varchar(200))
    begin
		update Roles
        set nombre_rol = nombre_rol_p,
            descripcion = descripcion_p
        where id_rol = id_rol_p;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_editar_users(in id_user_p varchar(36),
									 in name_p varchar(50),
									 in lastname_p varchar(50),
                                     in email_p varchar(50),
                                     in user_p varchar(25),
                                     in password_p varchar(35),
                                     in id_rol_p varchar(36))
    begin
		update Users
        set name = name_p,
			lastname = lastname_p,
            email = email_p,
            user = user_p,
            password = password_p,
            id_rol = id_rol_p
        where id_user = id_user_p;
    end$$
delimiter ;
 
-- ============================================================================
-- ELIMINAR
 
delimiter $$
	create procedure sp_eliminar_roles(in id_rol_p varchar(36))
    begin
		delete from Roles where id_rol = id_rol_p;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_eliminar_users(in id_user_p varchar(36))
    begin
		delete from Users where id_user = id_user_p;
    end$$
delimiter ;

call sp_create_roles('Gerente', 'Supervisa reportes de ventas e inventario');
call sp_create_roles("Cajero", "El atiende al cliente y es encargado en cobrabrarel producto");
call sp_mostrar_roles();
call sp_create_users('Dereck', 'Marroquin', 'Derml@correo.com', 'Kirely1', 'KD123', 'ae7fa370-abd6-11f1-9762-04d9f5886b91');
call sp_create_users("David", "Hernandez", "David@gmail.com", "Davdd2", "DDVID", "1137f2da-ac8e-11f1-b77f-04d9f5886b91");
call sp_mostrar_users();

