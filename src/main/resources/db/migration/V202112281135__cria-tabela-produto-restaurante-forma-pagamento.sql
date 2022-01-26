create table produto (id serial, ativo boolean not null, descricao varchar(255) not null, nome varchar(255) not null, preco numeric(19, 2) not null, restaurante_id int8 not null, primary key (id));

create table restaurante (id serial, data_atualizacao timestamp not null, data_cadastro timestamp not null, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255) not null, taxa_frete numeric(19, 2) not null, cozinha_id int8 not null, endereco_cidade_id int8, primary key (id));

create table restaurante_forma_pagamento (restaurante_id int8 not null, forma_pagamento_id int8 not null);

alter table if exists produto add constraint fk_produto_restaurante foreign key (restaurante_id) references restaurante;
alter table if exists restaurante add constraint fk_restaurante_cozinha foreign key (cozinha_id) references cozinha;
alter table if exists restaurante add constraint fk_restaurante_endereco_cidade foreign key (endereco_cidade_id) references cidade;

alter table if exists restaurante_forma_pagamento add constraint fk_restaurante_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento;
alter table if exists restaurante_forma_pagamento add constraint fk_forma_pagamento_restaurante foreign key (restaurante_id) references restaurante;
