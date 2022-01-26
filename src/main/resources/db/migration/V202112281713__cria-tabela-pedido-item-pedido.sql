create table pedido(
	id serial not null,
	subtotal numeric(19,2) not null,
	valor_total numeric(19,2) not null,
	data_criacao timestamp not null,
	data_confirmacao timestamp,
	data_cancelamento timestamp,
	data_entrega timestamp,
	forma_pagamento_id bigint not null,
	restaurante_id bigint not null,
	cliente_id bigint not null,
	status varchar(30) not null,
	endereco_cidade_id bigint not null,
	endereco_bairro varchar(255) not null,
    endereco_cep varchar(255) not null,
    endereco_logradouro varchar(255) not null,
    endereco_numero varchar(255) not null,
    endereco_complemento varchar(255),
	primary key(id)
);

create table item_pedido(
	id serial not null,
	quantidade int not null,
	preco_unitario numeric(19,2) not null,
	preco_total numeric(19,2) not null,
	observacao varchar(255),
	pedido_id bigint not null,
	produto_id bigint not null,
	primary key(id)
);


alter table if exists pedido add constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento;
alter table if exists pedido add constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante;
alter table if exists pedido add constraint fk_pedido_cliente foreign key (cliente_id) references usuario;
alter table if exists pedido add constraint fk_pedido_endereco_cidade foreign key (endereco_cidade_id) references cidade;

alter table if exists item_pedido add constraint fk_item_pedido_produto foreign key (produto_id) references produto;