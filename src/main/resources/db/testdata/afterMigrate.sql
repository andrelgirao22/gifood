
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

insert into forma_pagamento (id, descricao, data_atualizacao) values (1, 'Cartão de crédito', timezone('utc', now()));
insert into forma_pagamento (id, descricao, data_atualizacao) values (2, 'Cartão de débito', timezone('utc', now()));
insert into forma_pagamento (id, descricao, data_atualizacao) values (3, 'Dinheiro', timezone('utc', now()));
SELECT setval('forma_pagamento_seq', 3);

insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissao (id, nome, descricao) values (6, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissao (id, nome, descricao) values (8, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissao (id, nome, descricao) values (9, 'CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar usuários');
insert into permissao (id, nome, descricao) values (10, 'EDITAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite criar ou editar usuários');
insert into permissao (id, nome, descricao) values (12, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissao (id, nome, descricao) values (15, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissao (id, nome, descricao) values (16, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissao (id, nome, descricao) values (17, 'GERAR_RELATORIOS', 'Permite gerar relatórios');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, false, 1);
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

-- Adiciona todas as permissoes no grupo do gerente
insert into grupo_permissao (grupo_id, permissao_id)
select 1, id from permissao;

-- Adiciona permissoes no grupo do vendedor
insert into grupo_permissao (grupo_id, permissao_id)
select 2, id from permissao where nome like 'CONSULTAR_%';

-- Adiciona permissoes no grupo do auxiliar
insert into grupo_permissao (grupo_id, permissao_id)
select 3, id from permissao where nome like 'CONSULTAR_%';

-- Adiciona permissoes no grupo cadastrador
insert into grupo_permissao (grupo_id, permissao_id)
select 4, id from permissao where nome like '%_RESTAURANTES' or nome like '%_PRODUTOS';

insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@algafood.com', '$2a$12$6dgPBdkaklU5WIyka38V9.5wGsof47yTcNhv8cwkJ61J0gKs52PaO', timezone('utc', now())),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '$2a$12$6dgPBdkaklU5WIyka38V9.5wGsof47yTcNhv8cwkJ61J0gKs52PaO', timezone('utc', now())),
(3, 'José Souza', 'jose.aux@algafood.com', '$2a$12$6dgPBdkaklU5WIyka38V9.5wGsof47yTcNhv8cwkJ61J0gKs52PaO', timezone('utc', now())),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '$2a$12$6dgPBdkaklU5WIyka38V9.5wGsof47yTcNhv8cwkJ61J0gKs52PaO', timezone('utc', now())),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', timezone('utc', now())),
(6, 'Débora Mendonça', 'email.teste.aw+debora@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', timezone('utc', now())),
(7, 'Carlos Lima', 'email.teste.aw+carlos@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', timezone('utc', now()));
SELECT setval('usuario_seq', 7);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2);

delete from restaurante_usuario;
insert into restaurante_usuario (restaurante_id, usuario_id) values (1, 5), (3, 5);


delete from pedido;
delete from item_pedido;

insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (1, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
        'CRIADO', timezone('utc', now()), 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (2, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 6, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CRIADO', timezone('utc', now()), 79, 0, 79);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');


insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'ENTREGUE', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (4, 3, 2, 1, 110, 110, null);


insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'ENTREGUE', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (5, 4, 3, 2, 87.2, 174.4, null);


insert into pedido (id, codigo, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'ENTREGUE', '2019-11-03 02:00:30', '2019-11-03 02:01:21', '2019-11-03 02:20:10', 87.2, 10, 97.2);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (6, 5, 3, 1, 87.2, 87.2, null);

SELECT setval('pedido_seq', 5);
SELECT setval('item_pedido_seq', 6);

delete from oauth_client_details where 1 = 1;

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'gifood-web', null, '$2y$12$w3igMjsfS5XoAYuowoH3C.54vRFWlcXSHLjX7MwF990Kc2KKKh72e',
  'READ,WRITE', 'password', null, null,
  60 * 60 * 6, 60 * 24 * 60 * 60, null
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'foodanalytics', null, '$2a$12$EsfnhEoBylVy3PNJmBleheZTys7j.VYWo0VhnEXXKpknUx7a62JgS',
  'READ,WRITE', 'authorization_code', 'http://www.foodanalytics.local:8082', null,
  null, null, null
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'faturamento', null, '$2y$12$fHixriC7yXX/i1/CmpnGH.RFyK/l5YapLCFOEbIktONjE8ZDykSnu',
  'READ,WRITE', 'client_credentials', null, 'CONSULTAR_PEDIDOS,GERAR_RELATORIOS',
  null, null, null
);