-- drop database if exists proyecto_quickmart_inventory_kinal_in4av;
create database proyecto_quickmart_inventory_kinal_in4av;
use proyecto_quickmart_inventory_kinal_in4av;
 
--  TABLAS Roles y Users
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
    constraint uq_users_user unique (user),
    constraint uq_users_email unique (email),
    constraint fk_users_rol foreign key (id_rol) references Roles(id_rol)
);

-- TABLAS Producto y Categoria
-- ==========================================================================
create table Categoria(
	nombre_categoria varchar (50) not null check (length(nombre_categoria)<=50),
    descripcion varchar (200) not null check (length(descripcion)<=200),
    id_categoria varchar(36) not null,
    constraint pk_categoria primary key (id_categoria),
    constraint uq_categoria_nombre unique (nombre_categoria)
);
 
create table Producto(
	codigo varchar (20) not null check (length(codigo)<=20),
    nombre varchar (100) not null check (length(nombre)<=100),
    existencia int not null check (existencia>=0),
    precio decimal(10,2) not null check (precio>=0),
    id_producto varchar(36) not null,
    id_categoria varchar(36) not null,
    constraint pk_producto primary key (id_producto),
    constraint uq_producto_codigo unique (codigo),
    constraint fk_producto_categoria foreign key (id_categoria) references Categoria(id_categoria)
);

-- Ventas Y Detalle de ventas
-- =======================================================================
create table Venta(
	fecha datetime not null,
    total decimal(10,2) not null check (total>=0),
    id_venta varchar(36) not null,
    id_user varchar(36) not null,
    constraint pk_venta primary key (id_venta),
    constraint fk_venta_user foreign key (id_user) references Users(id_user)
);
 
create table Detalle_venta(
	cantidad int not null check (cantidad>0),
    precio_unitario decimal(10,2) not null check (precio_unitario>=0),
    subtotal decimal(10,2) not null check (subtotal>=0),
    id_detalle_venta varchar(36) not null,
    id_venta varchar(36) not null,
    id_producto varchar(36) not null,
    constraint pk_detalle_venta primary key (id_detalle_venta),
    constraint fk_detalle_venta_venta foreign key (id_venta) references Venta(id_venta),
    constraint fk_detalle_venta_producto foreign key (id_producto) references Producto(id_producto)
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

-- ===============================================================================
delimiter $$
	create procedure sp_create_categoria(in nombre_categoria_p varchar(50),
										 in descripcion_p varchar(200))
    begin
		insert into Categoria(nombre_categoria, descripcion, id_categoria)
			values(nombre_categoria_p, descripcion_p, uuid());
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_create_producto(in codigo_p varchar(20),
										in nombre_p varchar(100),
                                        in existencia_p int,
                                        in precio_p decimal(10,2),
                                        in id_categoria_p varchar(36))
    begin
		insert into Producto(codigo, nombre, existencia, precio, id_producto, id_categoria)
			values(codigo_p, nombre_p, existencia_p, precio_p, uuid(), id_categoria_p);
    end$$
delimiter ;
-- -------------------------------------------------------------------------------------
delimiter $$
	create procedure sp_create_venta(in fecha_p datetime,
									 in total_p decimal(10,2),
                                     in id_user_p varchar(36))
    begin
		insert into Venta(fecha, total, id_venta, id_user)
			values(fecha_p, total_p, uuid(), id_user_p);
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_create_detalle_venta(in cantidad_p int,
											 in precio_unitario_p decimal(10,2),
                                             in subtotal_p decimal(10,2),
                                             in id_venta_p varchar(36),
                                             in id_producto_p varchar(36))
    begin
		insert into Detalle_venta(cantidad, precio_unitario, subtotal, id_detalle_venta, id_venta, id_producto)
			values(cantidad_p, precio_unitario_p, subtotal_p, uuid(), id_venta_p, id_producto_p);
    end$$
delimiter ;
 
-- --------------------------------------------------------------------------------------
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
-- ---------------------------------------------------------------------------------
delimiter $$
	create procedure sp_mostrar_categoria()
    begin
		select id_categoria     as 'ID Categoria',
               nombre_categoria as 'Nombre De Categoria',
               descripcion      as 'Descripcion'
        from Categoria;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_mostrar_producto()
    begin
		select p.id_producto      as 'ID Producto',
               p.codigo           as 'Codigo',
               p.nombre           as 'Nombre',
               p.existencia       as 'Existencia',
               p.precio           as 'Precio',
               c.nombre_categoria as 'Categoria'
        from Producto p
        inner join Categoria c on p.id_categoria = c.id_categoria;
    end$$
delimiter ;
-- ------------------------------------------------------------------------
delimiter $$
	create procedure sp_mostrar_venta()
    begin
		select v.id_venta as 'ID Venta',
               v.fecha    as 'Fecha',
               v.total    as 'Total',
               u.user     as 'Usuario'
        from Venta v
        inner join Users u on v.id_user = u.id_user;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_mostrar_detalle_venta()
    begin
		select dv.id_detalle_venta as 'ID Detalle Venta',
               dv.cantidad         as 'Cantidad',
               dv.precio_unitario  as 'Precio Unitario',
               dv.subtotal         as 'Subtotal',
               p.nombre            as 'Producto',
               v.id_venta          as 'ID Venta'
        from Detalle_venta dv
        inner join Producto p on dv.id_producto = p.id_producto
        inner join Venta v on dv.id_venta = v.id_venta;
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
-- ----------------------------------------------------------------------------
delimiter $$
	create procedure sp_leer_categoria(in id_categoria_p varchar(36))
    begin
		select id_categoria     as 'ID Categoria',
               nombre_categoria as 'Nombre De Categoria',
               descripcion      as 'Descripcion'
        from Categoria
        where id_categoria = id_categoria_p;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_leer_producto(in id_producto_p varchar(36))
    begin
		select p.id_producto   as 'ID Producto',
               p.codigo        as 'Codigo',
               p.nombre        as 'Nombre',
               p.existencia    as 'Existencia',
               p.precio        as 'Precio',
               p.id_categoria  as 'ID Categoria'
        from Producto p
        where p.id_producto = id_producto_p;
    end$$
delimiter ;
-- ---------------------------------------------------------------------------
delimiter $$
	create procedure sp_leer_venta(in id_venta_p varchar(36))
    begin
		select id_venta as 'ID Venta',
               fecha    as 'Fecha',
               total    as 'Total',
               id_user  as 'ID Usuario'
        from Venta
        where id_venta = id_venta_p;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_leer_detalle_venta(in id_detalle_venta_p varchar(36))
    begin
		select id_detalle_venta as 'ID Detalle Venta',
               cantidad         as 'Cantidad',
               precio_unitario  as 'Precio Unitario',
               subtotal         as 'Subtotal',
               id_venta         as 'ID Venta',
               id_producto      as 'ID Producto'
        from Detalle_venta
        where id_detalle_venta = id_detalle_venta_p;
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
-- ----------------------------------------------------------------------------
delimiter $$
	create procedure sp_editar_categoria(in id_categoria_p varchar(36),
										 in nombre_categoria_p varchar(50),
                                         in descripcion_p varchar(200))
    begin
		update Categoria
        set nombre_categoria = nombre_categoria_p,
            descripcion = descripcion_p
        where id_categoria = id_categoria_p;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_editar_producto(in id_producto_p varchar(36),
										in codigo_p varchar(20),
                                        in nombre_p varchar(100),
                                        in existencia_p int,
                                        in precio_p decimal(10,2),
                                        in id_categoria_p varchar(36))
    begin
		update Producto
        set codigo = codigo_p,
			nombre = nombre_p,
            existencia = existencia_p,
            precio = precio_p,
            id_categoria = id_categoria_p
        where id_producto = id_producto_p;
    end$$
delimiter ;
-- --------------------------------------------------------------------------
delimiter $$
	create procedure sp_editar_venta(in id_venta_p varchar(36),
									 in fecha_p datetime,
                                     in total_p decimal(10,2),
                                     in id_user_p varchar(36))
    begin
		update Venta
        set fecha = fecha_p,
            total = total_p,
            id_user = id_user_p
        where id_venta = id_venta_p;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_editar_detalle_venta(in id_detalle_venta_p varchar(36),
											 in cantidad_p int,
                                             in precio_unitario_p decimal(10,2),
                                             in subtotal_p decimal(10,2),
                                             in id_venta_p varchar(36),
                                             in id_producto_p varchar(36))
    begin
		update Detalle_venta
        set cantidad = cantidad_p,
            precio_unitario = precio_unitario_p,
            subtotal = subtotal_p,
            id_venta = id_venta_p,
            id_producto = id_producto_p
        where id_detalle_venta = id_detalle_venta_p;
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

delimiter $$
	create procedure sp_obtener_rol_por_nombre(in nombre_rol_p varchar(50))
    begin
		select id_rol, nombre_rol, descripcion
        from Roles
        where nombre_rol = nombre_rol_p;
    end$$
delimiter ;

delimiter $$
	create procedure sp_existe_usuario(in user_p varchar(25), in email_p varchar(50))
    begin
		select count(*) as total
        from Users
        where user = user_p or email = email_p;
    end$$
delimiter ;
-- ----------------------------------------------------------------------------
delimiter $$
	create procedure sp_eliminar_categoria(in id_categoria_p varchar(36))
    begin
		delete from Categoria where id_categoria = id_categoria_p;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_eliminar_producto(in id_producto_p varchar(36))
    begin
		delete from Producto where id_producto = id_producto_p;
    end$$
delimiter ;
-- -----------------------------------------------------------
delimiter $$
	create procedure sp_eliminar_venta(in id_venta_p varchar(36))
    begin
		delete from Venta where id_venta = id_venta_p;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_eliminar_detalle_venta(in id_detalle_venta_p varchar(36))
    begin
		delete from Detalle_venta where id_detalle_venta = id_detalle_venta_p;
    end$$
delimiter ;
-- ============================================================================


delimiter $$
	create procedure sp_validar_login(in username_p varchar(25),
									   in password_p varchar(35))
    begin
		select
			u.id_user,
			u.user      as nombre_usuario,
			r.nombre_rol as nombre_rol
        from Users u
        inner join Roles r on u.id_rol = r.id_rol
        where u.user = username_p
			and u.password = password_p;
    end$$
delimiter ;

-- ============================================================================
-- DATOS DE PRUEBA

call sp_create_roles('Gerente', 'Supervisa reportes de ventas e inventario');
call sp_create_roles("Cajero", "El atiende al cliente y es encargado en cobrabrarel producto");
call sp_create_roles('Cliente', 'Usuario registrado desde la aplicacion');
call sp_create_roles ("Bodeguero", "Se encarga ver el inventario de los productos");

call sp_mostrar_roles();
call sp_create_users('Dereck', 'Marroquin', 'Derml@correo.com', 'Kirely1', 'KD1233', 'bc874ba1-b21b-11f1-afe0-04d9f5886b91');
call sp_create_users("David", "Hernandez", "David@gmail.com", "Davdd2", "DDVID", "1137f2da-ac8e-11f1-b77f-04d9f5886b91");
call sp_create_users("Checha", "Piojon", "Checha@gmail.com", "Checha", "c12351","d139a37f-b6ba-11f1-a438-04d9f5886b91");
call sp_mostrar_producto();
call sp_mostrar_users();

