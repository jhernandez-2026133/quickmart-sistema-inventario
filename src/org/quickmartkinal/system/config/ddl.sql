drop database if exists quickmart_db;
create database quickmart_db;
use quickmart_db;

create table rol(
    id_rol int not null auto_increment,
    nombre_rol varchar(30) not null,
    constraint pk_rol primary key (id_rol),
    constraint uq_rol_nombre unique (nombre_rol)
);

create table usuario(
    id_usuario int not null auto_increment,
    nombre_usuario varchar(50) not null,
    contrasena varchar(255) not null,
    id_rol int not null,
    constraint pk_usuario primary key (id_usuario),
    constraint uq_usuario_nombre unique (nombre_usuario),
    constraint fk_usuario_rol foreign key (id_rol) references rol(id_rol)
);

insert into rol (nombre_rol) values
    ('Administrador'),
    ('Gerente'),
    ('Bodeguero'),
    ('Cajero');

insert into usuario (nombre_usuario, contrasena, id_rol) values
    ('admin', 'admin123', (select id_rol from rol where nombre_rol = 'Administrador'));

delimiter $$
create procedure sp_validar_login(
    in p_usuario varchar(50),
    in p_password varchar(255)
)
begin
    select u.nombre_usuario, r.nombre_rol
        from usuario u
        inner join rol r on u.id_rol = r.id_rol
        where u.nombre_usuario = p_usuario
        and u.contrasena = p_password;
end$$
delimiter ;

call sp_validar_login('admin', 'admin123');
