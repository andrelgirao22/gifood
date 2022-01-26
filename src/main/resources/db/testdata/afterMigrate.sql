
TRUNCATE TABLE cidade RESTART IDENTITY CASCADE;
TRUNCATE TABLE cozinha RESTART IDENTITY CASCADE;
TRUNCATE TABLE estado RESTART IDENTITY CASCADE;
TRUNCATE TABLE forma_pagamento RESTART IDENTITY CASCADE;
TRUNCATE TABLE grupo RESTART IDENTITY CASCADE;
TRUNCATE TABLE grupo_permissao RESTART IDENTITY CASCADE;
TRUNCATE TABLE permissao RESTART IDENTITY CASCADE;
TRUNCATE TABLE produto RESTART IDENTITY CASCADE;
TRUNCATE TABLE restaurante RESTART IDENTITY CASCADE;
TRUNCATE TABLE restaurante_forma_pagamento RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuario RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuario_grupo RESTART IDENTITY CASCADE;


insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');
SELECT setval('cozinha_seq', 4);


insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');
SELECT setval('estado_seq', 3);

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);
SELECT setval('cidade_seq', 5);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, ativo) values (1, 'Thai Gourmet', 10, 1, timezone('utc', now()), timezone('utc', now()), 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (2, 'Thai Delivery', 9.50, 1, timezone('utc', now()), timezone('utc', now()), true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (3, 'Tuk Tuk Comida Indiana', 15, 2, timezone('utc', now()), timezone('utc', now()),true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (4, 'Java Steakhouse', 12, 3, timezone('utc', now()), timezone('utc', now()), true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (5, 'Lanchonete do Tio Sam', 11, 4, timezone('utc', now()), timezone('utc', now()), true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (6, 'Bar da Maria', 6, 4, timezone('utc', now()), timezone('utc', now()), true);
SELECT setval('restaurante_seq', 6);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');
SELECT setval('forma_pagamento_seq', 3);


insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, true, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, true, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, true, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, true, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, true, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, true, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, true, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, true, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, true, 6);
SELECT setval('produto_seq', 9);

insert into grupo (nome) values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');
SELECT setval('grupo_seq', 4);
insert into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@algafood.com', '123', timezone('utc', now())),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', timezone('utc', now())),
(3, 'José Souza', 'jose.aux@algafood.com', '123', timezone('utc', now())),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', timezone('utc', now()));
SELECT setval('usuario_seq', 4);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2);

delete from restaurante_usuario;
insert into usuario (id, nome, email, senha, data_cadastro) values (5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', timezone('utc', now()));
insert into restaurante_usuario (restaurante_id, usuario_id) values (1, 5), (3, 5);
