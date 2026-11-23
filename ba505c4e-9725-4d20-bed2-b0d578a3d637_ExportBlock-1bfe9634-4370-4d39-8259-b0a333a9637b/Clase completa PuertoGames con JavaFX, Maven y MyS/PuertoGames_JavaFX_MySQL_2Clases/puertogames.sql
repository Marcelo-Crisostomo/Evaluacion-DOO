-- 1) Crea la base de datos si todavía no existe.
CREATE DATABASE IF NOT EXISTS puertogames CHARACTER SET utf8mb4;

-- 2) Selecciona esa base de datos para ejecutar las sentencias siguientes.
USE puertogames;

-- 3) Crea la tabla que utilizará el DAO de Java.
CREATE TABLE IF NOT EXISTS videojuegos (
    id INT AUTO_INCREMENT PRIMARY KEY,       -- Identificador automático.
    codigo VARCHAR(20) NOT NULL UNIQUE,      -- No permite códigos repetidos.
    nombre VARCHAR(100) NOT NULL,            -- Nombre del videojuego.
    precio_base INT NOT NULL,                 -- Precio ingresado en el formulario.
    tipo ENUM('FISICO', 'DIGITAL') NOT NULL,  -- Solo admite dos tipos.
    CHECK (precio_base > 0)                  -- Rechaza precios no positivos.
);

-- 4) Consulta la tabla (estará vacía al crearla por primera vez).
SELECT * FROM videojuegos;
