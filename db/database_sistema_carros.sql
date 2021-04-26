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

CREATE TABLE telefone(
id int not null primary key,
numero varchar(12),
tipo varchar(20),
usuario int,
FOREIGN KEY (usuario) REFERENCES usuario(id)
);

Create TABLE usuario_carro(
usuario_id int not null,
carro_id int not null,
PRIMARY KEY (usuario_id, carro_id),
FOREIGN KEY (usuario_id) REFERENCES usuario(id),
FOREIGN KEY (carro_id) REFERENCES carro(id)
);