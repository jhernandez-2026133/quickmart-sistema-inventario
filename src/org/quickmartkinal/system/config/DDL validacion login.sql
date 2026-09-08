create database proyecto_quickmart_inventory_kinal_in4av;
use proyecto_quickmart_inventory_kinal_in4av;

delimiter $$
	create procedure sp_validar_login (in username_p varchar (30),
										in password_p varchar (50))
	begin
		select 
			u.id_user,
			u.name,
			u.last_name,
			u.email,
			u.username,
			r.id_rol,
			r.name_user AS name_rol,
			r.description_rol
		from Users u
        inner join RolUser r on u.id_rol = r.id_rol
        where u.username = username_p
			and u.password = password_p;
	end $$
delimiter ;

call sp_validar_login ("cramirez", "123");