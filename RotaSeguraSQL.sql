CREATE DATABASE rotasegura;
USE rotasegura;

CREATE TABLE cliente (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);


CREATE TABLE veiculo (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    diaria DOUBLE NOT NULL,
    seguro DOUBLE NOT NULL,
    manutencao DOUBLE NOT NULL,
    disponivel BOOLEAN NOT NULL
);


CREATE TABLE contrato (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    id_veiculo INT NOT NULL,
    data_retirada DATE NOT NULL,
    data_devolucao DATE NOT NULL,
    diarias INT NOT NULL,
    valor_diaria DOUBLE NOT NULL,
    valor_seguro DOUBLE NOT NULL,
    valor_manutencao DOUBLE NOT NULL,
    valor_total DOUBLE NOT NULL
);


ALTER TABLE contratoclientecliente
ADD CONSTRAINT fk_contrato_cliente
FOREIGN KEY (id_cliente)
REFERENCES cliente(id);

ALTER TABLE contrato
ADD CONSTRAINT fk_contrato_veiculo
FOREIGN KEY (id_veiculo)
REFERENCES veiculo(id);