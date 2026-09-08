create database proyecto_quickmart_inventory_kinal_in4av;
use proyecto_quickmart_inventory_kinal_in4av;
 -- drop database proyecto_quickmart_inventory_kinal_in4av;

create table Roles(
	nombre_rol varchar (50) not null check (length(nombre_rol)<=50),
    id_rol varchar(36) not null,
    constraint pk_roles primary key (id_rol)
);

create table Users(
	name varchar (50) not null check (length(name)<=50),
    lastname varchar (50) not null check (length(lastname)<=50),
	email varchar (50) not null check (length(email)<=50),
    user varchar (25) not null check (length(user)<=25),
    password varchar (35) not null check (length(password)<=35),
    id_user int (36) auto_increment,
    id_rol varchar(36) not null,
    constraint pk_users primary key (id_user),
    constraint fk_users_rol foreign key (id_rol) references Roles(id_rol)
);

-- ===========================================================================
-- CREATE

delimiter $$
	create procedure sp_create_roles(in nombre_rol_p varchar(50))
    begin
		insert into Roles(nombre_rol, descripcion, id_rol)
			values(nombre_rol_p,  uuid());
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

-- =====================================================
-- MOSTRAR 
-- call sp_mostrar_users;

delimiter $$
	create procedure sp_mostrar_roles()
    begin
		select "ID Roles",
         "Nombre De Rol"
         from Roles;
    end$$
delimiter ;

delimiter $$
	create procedure sp_mostrar_users()
    begin
		select"ID USUARIO",
         "Nombre" ,
         "Apellidos",
         "EMAIL", 
        "Usuario",
		"ID ROL"  from Users;
    end$$
delimiter ;

-- =====================================================
-- EDITAR 

delimiter $$
	create procedure sp_editar_roles(in id_rol_p varchar(36),
									 in nombre_rol_p varchar(50))
    begin
		update Roles
        set nombre_rol = nombre_rol_p
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

-- =====================================================
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

