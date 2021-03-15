CREATE DATABASE sistema_carros;

CREATE TABLE carro ( 
id int not null primary key, 
modelo varchar(50), 
fabricante varchar(50), 
cor varchar(50), 
ano Date 
);

CREATE TABLE usuario ( 
id int not null primary key, 
login varchar(50), 
senha varchar(50)
);