DROP TABLE IF EXISTS listas_videojuegos CASCADE;
DROP TABLE IF EXISTS listas CASCADE;
DROP TABLE IF EXISTS jugadores CASCADE;
DROP TABLE IF EXISTS videojuegos CASCADE;

CREATE TABLE videojuegos (
                             id      SERIAL PRIMARY KEY,
                             titulo  VARCHAR(150) NOT NULL,
                             genero  VARCHAR(60),
                             anio    INTEGER,
                             nota    REAL
);

CREATE TABLE jugadores (
                           id_jugador  SERIAL PRIMARY KEY,
                           nombre      VARCHAR(100) NOT NULL,
                           email       VARCHAR(150) UNIQUE
);

CREATE TABLE listas (
                        id_lista    SERIAL PRIMARY KEY,
                        nombre      VARCHAR(100) NOT NULL,
                        id_jugador  INTEGER NOT NULL,
                        CONSTRAINT fk_listas_jugador
                            FOREIGN KEY (id_jugador) REFERENCES jugadores (id_jugador)
                                ON DELETE CASCADE
);

CREATE TABLE listas_videojuegos (
                                    id_lista        INTEGER NOT NULL,
                                    id_videojuego   INTEGER NOT NULL,
                                    fecha_añadido   VARCHAR(20),
                                    PRIMARY KEY (id_lista, id_videojuego),
                                    CONSTRAINT fk_lv_lista
                                        FOREIGN KEY (id_lista) REFERENCES listas (id_lista)
                                            ON DELETE CASCADE,
                                    CONSTRAINT fk_lv_videojuego
                                        FOREIGN KEY (id_videojuego) REFERENCES videojuegos (id)
                                            ON DELETE CASCADE
);

INSERT INTO videojuegos (titulo, genero, anio, nota) VALUES
                                                         ('The Legend of Zelda: Breath of the Wild', 'Aventura',    2017, 9.5),
                                                         ('Red Dead Redemption 2',                   'Acción',      2018, 9.7),
                                                         ('Hades',                                   'Roguelike',   2020, 9.3),
                                                         ('Hollow Knight',                           'Plataformas', 2017, 9.1),
                                                         ('Celeste',                                 'Plataformas', 2018, 8.9);

INSERT INTO jugadores (nombre, email) VALUES
                                          ('Laura Martínez', 'laura@example.com'),
                                          ('Carlos Gómez',   'carlos@example.com'),
                                          ('Marta Ruiz',     'marta@example.com');

INSERT INTO listas (nombre, id_jugador) VALUES
                                            ('Favoritos de Laura',   1),
                                            ('Pendientes de Carlos', 2),
                                            ('Plataformas de Marta', 3);

INSERT INTO listas_videojuegos (id_lista, id_videojuego, fecha_añadido) VALUES
                                                                            (1, 1, '2025-01-10'),
                                                                            (1, 3, '2025-01-12'),
                                                                            (2, 2, '2025-02-03'),
                                                                            (2, 5, '2025-02-05'),
                                                                            (3, 4, '2025-03-01'),
                                                                            (3, 5, '2025-03-02');