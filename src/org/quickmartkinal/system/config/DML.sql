use proyecto_quickmart_inventory_kinal_in4av;

-- ============================================================================
-- datos de prueba

-- 1. roles
call sp_create_roles('administrador', 'gestiona el catalogo, los usuarios y la configuracion del sistema');
call sp_create_roles('gerente', 'supervisa el inventario y el estado general del negocio');
call sp_create_roles('bodeguero', 'controla entradas y salidas de stock');
call sp_create_roles('cliente', 'usuario registrado desde la aplicacion para realizar compras');
call sp_mostrar_roles();

-- 2. usuarios
call sp_create_users('dereck', 'marroquin', 'derml@correo.com', 'kirely1', 'kd1233',
    (select id_rol from roles where nombre_rol = 'administrador' limit 1));
call sp_create_users('david', 'hernandez', 'david@gmail.com', 'davdd2', 'ddvid',
    (select id_rol from roles where nombre_rol = 'gerente' limit 1));
call sp_create_users('jeison', 'garcia', 'jeison@gmail.com', 'jeison', '121212',
    (select id_rol from roles where nombre_rol = 'bodeguero' limit 1));
call sp_mostrar_users();

-- 3. categorias
call sp_create_categoria('lacteos', 'leche, queso, yogurt y derivados');
call sp_create_categoria('abarrotes', 'productos basicos de despensa');
call sp_create_categoria('golosinas', 'productos con texturas suaves y sabores intensos');
call sp_create_categoria('carnes', 'productos carnicos frescos y embutidos');
call sp_create_categoria('panaderia', 'panes, reposteria y productos horneados');
call sp_create_categoria('snacks', 'frituras, frutos secos y aperitivos');
call sp_create_categoria('bebidas', 'jugos, refrescos y aguas');
call sp_mostrar_categoria();

-- 4. productos
call sp_create_producto(
    '7501234567890', 
    'leche entera 1l', 
    50, 
    8.50, 
    12.00, 
    (select id_categoria from categoria where nombre_categoria = 'lacteos' limit 1)
);

-- 1. arroz (abarrotes)
call sp_create_producto(
    '7501000000001', 
    'arroz blanco 1kg', 
    100, 
    5.00, 
    7.50, 
    (select id_categoria from categoria where nombre_categoria = 'abarrotes' limit 1)
);

-- 2. pollo entero (carnes)
call sp_create_producto(
    '7501000000002', 
    'pollo entero limpio lb', 
    30, 
    10.00, 
    14.00, 
    (select id_categoria from categoria where nombre_categoria = 'carnes' limit 1)
);

-- 3. pan de molde (panaderia)
call sp_create_producto(
    '7501000000003', 
    'pan de molde blanco 500g', 
    40, 
    12.00, 
    16.50, 
    (select id_categoria from categoria where nombre_categoria = 'panaderia' limit 1)
);

-- 4. papas fritas (snacks)
call sp_create_producto(
    '7501000000004', 
    'papas fritas saladas 150g', 
    80, 
    4.50, 
    7.00, 
    (select id_categoria from categoria where nombre_categoria = 'snacks' limit 1)
);

-- 5. jugo de naranja (bebidas)
call sp_create_producto(
    '7501000000005', 
    'jugo de naranja 1l', 
    60, 
    9.00, 
    13.00, 
    (select id_categoria from categoria where nombre_categoria = 'bebidas' limit 1)
);

call sp_mostrar_producto();

-- 5. movimiento de stock
call sp_registrar_movimiento_stock(
    (select id_producto from producto where codigo = '7501234567890' limit 1),
    'entrada', 20);
call sp_mostrar_producto();

-- 6. ventas y detalle
call sp_create_venta(now(), 24.00,
    (select id_user from users where user = 'davdd2' limit 1));

call sp_create_detalle_venta(2, 12.00, 24.00,
    (select id_venta from venta order by fecha desc limit 1),
    (select id_producto from producto where codigo = '7501234567890' limit 1));

call sp_mostrar_venta();
call sp_mostrar_detalle_venta();
call sp_comprobante_venta((select id_venta from venta order by fecha desc limit 1));