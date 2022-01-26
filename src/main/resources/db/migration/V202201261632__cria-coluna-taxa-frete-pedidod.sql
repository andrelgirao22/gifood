ALTER TABLE pedido ADD COLUMN taxa_frete numeric(19,2) not null;
update pedido set taxa_frete = 0.0;