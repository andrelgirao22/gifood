ALTER TABLE restaurante ADD COLUMN aberto boolean NOT NULL DEFAULT true;
update restaurante set aberto = false;