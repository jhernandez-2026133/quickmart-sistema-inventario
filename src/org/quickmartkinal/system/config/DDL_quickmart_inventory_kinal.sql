drop database if exists proyecto_quickmart_inventory_kinal_in4av;
create database proyecto_quickmart_inventory_kinal_in4av;
use proyecto_quickmart_inventory_kinal_in4av;

--  TABLAS Roles y Users
-- ============================================================================

create table Roles(
	nombre_rol varchar (50) not null check (length(nombre_rol)<=50),
    descripcion varchar (200) not null check (length(descripcion)<=200),
    id_rol varchar(36) not null,
    constraint pk_roles primary key (id_rol),
    constraint uq_roles_nombre unique (nombre_rol)
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

-- NOTA: esta tabla unifica las dos versiones que quedaron en conflicto.
-- Se mantiene el codigo de barras y la categoria (rama A) y se agrega
-- el costo separado del precio de venta (rama B), ya que ambos son
-- requisitos del documento del proyecto (HU-02).
create table Producto(
	codigo varchar (20) not null check (length(codigo)<=20),
    nombre varchar (100) not null check (length(nombre)<=100),
    stock int not null check (stock>=0),
    costo decimal(10,2) not null check (costo>=0),
    precio_venta decimal(10,2) not null check (precio_venta>=0),
    id_producto varchar(36) not null,
    id_categoria varchar(36) not null,
    constraint pk_producto primary key (id_producto),
    constraint uq_producto_codigo unique (codigo),
    constraint fk_producto_categoria foreign key (id_categoria) references Categoria(id_categoria)
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

-- Unifica sp_create_producto (rama A) y la validacion de precio de
-- sp_insertar_producto (rama B) en un solo procedimiento.
delimiter $$
	create procedure sp_create_producto(in codigo_p varchar(20),
										in nombre_p varchar(100),
                                        in stock_p int,
                                        in costo_p decimal(10,2),
                                        in precio_venta_p decimal(10,2),
                                        in id_categoria_p varchar(36))
    begin
		IF precio_venta_p <= costo_p THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = "El precio de venta debe ser mayor al costo del producto.";
		END IF;

		insert into Producto (codigo, nombre, stock, costo, precio_venta, id_producto, id_categoria)
			values (codigo_p, nombre_p, stock_p, costo_p, precio_venta_p, uuid(), id_categoria_p);
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
               p.stock            as 'Stock',
               p.costo            as 'Costo',
               p.precio_venta     as 'Precio Venta',
               c.nombre_categoria as 'Categoria'
        from Producto p
        inner join Categoria c on p.id_categoria = c.id_categoria;
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
               p.stock         as 'Stock',
               p.costo         as 'Costo',
               p.precio_venta  as 'Precio Venta',
               p.id_categoria  as 'ID Categoria'
        from Producto p
        where p.id_producto = id_producto_p;
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
                                        in stock_p int,
                                        in costo_p decimal(10,2),
                                        in precio_venta_p decimal(10,2),
                                        in id_categoria_p varchar(36))
    begin
		IF precio_venta_p <= costo_p THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = "El precio de venta debe ser mayor al costo del producto.";
		END IF;

		update Producto
        set codigo = codigo_p,
			nombre = nombre_p,
            stock = stock_p,
            costo = costo_p,
            precio_venta = precio_venta_p,
            id_categoria = id_categoria_p
        where id_producto = id_producto_p;
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
-- REGISTRAR ENTRADA/SALIDA DE STOCK

delimiter $$
	create procedure sp_registrar_movimiento_stock (in id_producto_p varchar (36),
													in tipo_movimiento_p varchar (10),  -- Tiene que ser: "ENTRADA" o "SALIDA"
													in cantidad_p int)
	begin
    -- Validar que la cantidad sea positiva
		if cantidad_p <= 0 then
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = "La cantidad debe ser mayor a 0.";
		end if;

    -- Validar que el producto exista
		if (select count(*) from Producto where id_producto = id_producto_p) = 0 then
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = "El producto ingresado no existe.";
		end if;

		if tipo_movimiento_p = "ENTRADA" then
			update Producto
			set stock = stock + cantidad_p
			where id_producto = id_producto_p;

		elseif tipo_movimiento_p = "SALIDA" then
        -- Solo actualiza si hay stock suficiente (condición en el WHERE)
			update Producto
			set stock = stock - cantidad_p
			where id_producto = id_producto_p
			   and stock >= cantidad_p;

        -- Si no afectó ninguna fila, es porque no había stock suficiente
        if row_count() = 0 then
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = "No hay suficiente stock disponible para esta salida.";
        end if;

		else
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = "Tipo de movimiento inválido. Usa ENTRADA o SALIDA.";
		end if;
	end $$
delimiter ;

-- ============================================================================
-- PRUEBAS

call sp_create_roles('Administrador', 'Gestiona el catalogo, los usuarios y la configuracion del sistema');
call sp_create_roles('Gerente', 'Supervisa el inventario y el estado general del negocio');
call sp_create_roles('Bodeguero', 'Controla entradas y salidas de stock');
call sp_create_roles('Cliente', 'Usuario registrado desde la aplicacion para realizar compras');
call sp_mostrar_roles();

call sp_create_users('Dereck', 'Marroquin', 'Derml@correo.com', 'Kirely1', 'KD1233',
    (select id_rol from Roles where nombre_rol = 'Administrador' limit 1));
call sp_create_users('David', 'Hernandez', 'David@gmail.com', 'Davdd2', 'DDVID',
    (select id_rol from Roles where nombre_rol = 'Gerente' limit 1));
call sp_mostrar_users();
call sp_create_users ('jeison', 'Garcia', 'jeison@gmail.com', 'jeison', '121212',
	(select id_rol from Roles where nombre_rol = 'Bodeguero' limit 1));


-- ==================================================================
-- PRUEBAS DE CATEGORIA


call sp_create_categoria('Lacteos', 'Leche, queso, yogurt y derivados');
call sp_create_categoria('Abarrotes', 'Productos basicos de despensa');
call sp_create_categoria('Golosinas', 'Productos con Texturas suaves, sabores intensos y pura felicidad');

call sp_mostrar_categoria();

call sp_create_producto('7501234567890', 'Leche Entera 1L', 50, 8.50, 12.00,
    (select id_categoria 
		from Categoria 
		where nombre_categoria = 'Lacteos' 
			limit 1));
call sp_mostrar_producto();

call sp_registrar_movimiento_stock(
    (select id_producto from Producto where codigo = '7501234567890' limit 1),
    'ENTRADA', 20);
call sp_mostrar_producto();
