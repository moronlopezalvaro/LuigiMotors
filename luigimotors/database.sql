DROP DATABASE IF EXISTS luigimotors; -- depurar la base de datos
CREATE DATABASE IF NOT EXISTS luigimotors;
USE luigimotors;

-- crear las tablas
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

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    contrasenya_usuario VARCHAR(255) NOT NULL
);

CREATE TABLE citas (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora VARCHAR(5) NOT NULL,
    matricula VARCHAR(10) NOT NULL,
    descripcion TEXT NOT NULL,
    id_cliente INT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE CASCADE,
    UNIQUE(fecha, hora)
);

CREATE TABLE presupuestos (
    id_presupuesto INT AUTO_INCREMENT PRIMARY KEY,
    id_cita INT NOT NULL,
    desglose TEXT NOT NULL,
    mano_obra DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_cita) REFERENCES citas(id_cita) ON DELETE CASCADE
);

CREATE TABLE catalogo_reparaciones (
    id_catalogo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio_total DECIMAL(10, 2) NOT NULL,
    mano_obra DECIMAL(10, 2) NOT NULL
);

-- insertar datos en las tablas
INSERT INTO Cliente (dni, nombre, telefono, contrasenya, rol) VALUES 
('12345678A', 'Admin Mecánico', '600111222', 'admin123', 'Administrador'),
('87654321B', 'Manuel', '600333444', 'cliente1', 'Cliente'),
('11223344C', 'Álvaro', '600555666', 'cliente2', 'Cliente');

INSERT INTO Reparacion (matricula, descripcion, coste, fecha_ingreso, estado, id_cliente) VALUES 
('1234ABC', 'Cambio de aceite y filtros', 120.50, '2023-10-01', 'Terminado', 2),
('1234ABC', 'Sustitución pastillas de freno', 85.00, '2023-10-15', 'Terminado', 2),
('9876XYZ', 'Reparación tubo de escape', 250.00, '2023-11-02', 'Terminado', 3),
('9876XYZ', 'Revisión pre-ITV', 50.00, '2023-11-20', 'Pendiente', 3),
('1234ABC', 'Cambio de neumáticos', 300.00, '2023-11-22', 'Pendiente', 2),
('5555DEF', 'Alineación de dirección', 45.00, '2023-11-25', 'Terminado', 2),
('5555DEF', 'Sustitución batería', 90.00, '2023-11-26', 'Pendiente', 2);

INSERT INTO catalogo_reparaciones (nombre, precio_total, mano_obra) VALUES 
('Cambio de aceite + filtro', 80.00, 25.00),
('Cambio filtro aire', 35.00, 15.00),
('Cambio filtro habitáculo', 30.00, 15.00),
('Cambio pastillas de freno', 120.00, 30.00),
('Cambio discos + pastillas', 220.00, 50.00),
('Cambio batería', 120.00, 20.00),
('Cambio neumático', 70.00, 15.00),
('Alineación dirección', 50.00, 40.00),
('Equilibrado ruedas', 25.00, 20.00),
('Cambio líquido de frenos', 70.00, 30.00),
('Cambio anticongelante', 60.00, 25.00),
('Pulido de faros', 50.00, 40.00),
('Diagnóstico electrónico', 40.00, 40.00),
('Cambio kit embrague', 450.00, 180.00),
('Cambio distribución + bomba agua', 500.00, 200.00),
('Cambio amortiguadores', 220.00, 80.00),
('Cambio alternador', 350.00, 100.00),
('Cambio motor arranque', 300.00, 100.00),
('Limpieza inyectores', 120.00, 60.00),
('Cambio radiador', 250.00, 90.00),
('Recarga aire acondicionado', 80.00, 40.00),
('Cambio turbo', 900.00, 250.00),
('Cambio junta culata', 1200.00, 400.00),
('Reparación caja de cambios', 1500.00, 500.00),
('Cambio compresor aire acondicionado', 600.00, 200.00),
('Reparación sistema eléctrico', 200.00, 100.00),
('Sustitución centralita', 500.00, 120.00),
('Limpieza filtro partículas (FAP)', 250.00, 100.00),
('Cambio catalizador', 600.00, 150.00);
INSERT INTO Cliente (dni, nombre, telefono, contrasenya, rol) VALUES 
('12345678Z', 'MANOLO', '666666666', '...', 'Cliente');