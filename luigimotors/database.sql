DROP DATABASE IF EXISTS luigimotors;
CREATE DATABASE IF NOT EXISTS luigimotors;
USE luigimotors;

CREATE TABLE cliente (
    id_cliente INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE reparacion (
    id_reparacion INT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    coste DOUBLE NOT NULL,
    id_cliente_FK INT,
    FOREIGN KEY (id_cliente_FK) REFERENCES cliente(id_cliente) ON DELETE CASCADE
);
