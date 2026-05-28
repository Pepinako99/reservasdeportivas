-- ============================================================
--  SCRIPT SQL – PistaYa / ReservasDeportivas
--  Base de datos PostgreSQL
--  Ejecutar en pgAdmin o con psql:
--    psql -U postgres -f script_reservas_deportivas.sql
-- ============================================================

-- ============================================================
-- TABLA: usuarios
-- ============================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario   SERIAL PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL,
    correo       VARCHAR(150) NOT NULL UNIQUE,
    contrasena   VARCHAR(100) NOT NULL,
    conf         VARCHAR(100)
);

-- ============================================================
-- TABLA: instalaciones
-- ============================================================
CREATE TABLE IF NOT EXISTS instalaciones (
    id_instalacion  SERIAL PRIMARY KEY,
    nombre          VARCHAR(150) NOT NULL,
    tipo            VARCHAR(20)  NOT NULL,
    descripcion     TEXT,
    precio_hora     NUMERIC(6,2) NOT NULL
);

-- ============================================================
-- TABLA: reservas
-- ============================================================
CREATE TABLE IF NOT EXISTS reservas (
    id_reserva      SERIAL PRIMARY KEY,
    id_usuario      INTEGER NOT NULL REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    id_instalacion  INTEGER NOT NULL REFERENCES instalaciones(id_instalacion) ON DELETE CASCADE,
    fecha           VARCHAR(10) NOT NULL,
    hora_inicio     VARCHAR(5)  NOT NULL,
    hora_fin        VARCHAR(5)  NOT NULL,
    importe         NUMERIC(8,2) NOT NULL,
    nombre_ins      VARCHAR(150),
    estado          VARCHAR(20) DEFAULT 'activa'
);

-- ============================================================
-- DATOS INICIALES – Instalaciones deportivas
-- ============================================================
INSERT INTO instalaciones (nombre, tipo, descripcion, precio_hora) VALUES
    ('Pista de Pádel 1',  'padel',      'Pista de pádel cubierta con iluminación LED.',         15.00),
    ('Pista de Pádel 2',  'padel',      'Pista de pádel cubierta con iluminación LED.',         15.00),
	('Pista de Pádel 3',  'padel',      'Pista de pádel cubierta con iluminación LED.',         15.00),
    ('Pista Futsal A',    'futsal',     'Pabellón cubierto homologado para futsal.',            20.00),
    ('Campo Fútbol 11',   'futbol11',   'Campo de césped artificial para fútbol 11.',           25.00),
    ('Campo Fútbol 7',    'futbol7',    'Campo de césped artificial para fútbol 7.',            25.00),
    ('Pabellón Basket',   'baloncesto', 'Pabellón con 2 canchas de baloncesto.',                13.00);

-- ============================================================
-- VERIFICACIÓN
-- ============================================================
SELECT * FROM instalaciones;