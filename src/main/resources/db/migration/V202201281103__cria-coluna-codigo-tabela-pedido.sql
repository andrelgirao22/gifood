alter table pedido ADD COLUMN codigo varchar(60) not null default uuid_in(md5(random()::text || clock_timestamp()::text)::cstring);
alter table pedido ADD CONSTRAINT uk_pedido_codigo unique(codigo);