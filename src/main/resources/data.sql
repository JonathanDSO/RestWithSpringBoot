DROP TABLE IF EXISTS cliente;

create table cliente
(
   id SERIAL PRIMARY KEY,
   name varchar (255),
   cpf varchar (255),
   birth_date timestamp
);