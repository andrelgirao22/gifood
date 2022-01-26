create table grupo (id serial, nome varchar(255) not null, primary key (id));
create table permissao (id serial, descricao varchar(255), nome varchar(255) not null, primary key (id));
create table grupo_permissao (grupo_id int8 not null, permissao_id int8 not null);

create table usuario (id serial, data_cadastro timestamp not null, email varchar(255) not null, nome varchar(255) not null, senha varchar(255) not null, primary key (id));
create table usuario_grupo (usuario_id int8 not null, grupo_id int8 not null);

alter table if exists grupo_permissao add constraint fk_grupo_permissao foreign key (permissao_id) references permissao;
alter table if exists grupo_permissao add constraint fk_permissao_grupoi foreign key (grupo_id) references grupo;

alter table if exists usuario_grupo add constraint fk_usuario_grupo foreign key (grupo_id) references grupo;
alter table if exists usuario_grupo add constraint fk_grupo_usuario foreign key (usuario_id) references usuario;