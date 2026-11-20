INSERT INTO duenos (nombre, telefono, correo, direccion) VALUES
                                                             ('Carlos Perez', '3001234567', 'carlos@mail.com', 'Calle 1 #2-3'),
                                                             ('Maria Gomez', '3109876543', 'maria@mail.com', 'Carrera 4 #5-6');

INSERT INTO mascotas (nombre, especie, raza, edad, dueno_id) VALUES
                                                                 ('Firulais', 'Perro', 'Labrador', 3, 1),
                                                                 ('Michi', 'Gato', 'Siames', 2, 2);

INSERT INTO citas (fecha, motivo, veterinario, mascota_id) VALUES
    ('2023-10-25', 'Vacunación', 'Dr. Juan', 1);

INSERT INTO tratamientos (descripcion, dosis, cita_id) VALUES
    ('Vacuna Rabia', '1 dosis', 1);