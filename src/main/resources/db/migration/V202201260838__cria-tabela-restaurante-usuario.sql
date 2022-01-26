create table restaurante_usuario (
    restaurante_id serial,
    usuario_id serial
);

alter table if exists restaurante_usuario add constraint pk_restaurante_usuario primary key (restaurante_id, usuario_id);