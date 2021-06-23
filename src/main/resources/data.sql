DROP TABLE IF EXISTS customer;

create table customer
(
   id SERIAL PRIMARY KEY,
   name varchar (255),
   cpf varchar (255),
   birth_date timestamp
);