DROP DATABASE IF EXISTS luigimotors;
CREATE DATABASE IF NOT EXISTS luigimotors;
USE luigimotors;

CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(9) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    contrasenya VARCHAR(50) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

CREATE TABLE reparacion (
    id_reparacion INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(10) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    coste DECIMAL(10, 2),
    fecha_ingreso DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    id_cliente INT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE CASCADE
);