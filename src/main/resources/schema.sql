CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion TEXT,
    precio NUMERIC(10,2),
    stock INTEGER,
    activo BOOLEAN,
    fecha_creacion TIMESTAMP
);