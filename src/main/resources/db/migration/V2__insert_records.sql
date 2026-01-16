INSERT INTO tb_clients (username, password, name, last_name1, last_name2, dni, api_key)
VALUES ('javier',
        '$argon2id$v=19$m=15360,t=2,p=1$IWkQZkJkfAWYmn4hvwAQUe61Z3C6+9PzHr5Uu9Qf2VqPHFu480qXQE9fyUhxGXEWrI8qNbchVbBi8D9XErCt8A$XjJrwUROra2ZyuYg5RLgVmTs1FpnvdtKcn6iD+5HyFw',
        'Javier', 'Lopez', 'Gomez', '45904029K', 'gurt'),
       ('pepe',
        '$argon2id$v=19$m=15360,t=2,p=1$IWkQZkJkfAWYmn4hvwAQUe61Z3C6+9PzHr5Uu9Qf2VqPHFu480qXQE9fyUhxGXEWrI8qNbchVbBi8D9XErCt8A$XjJrwUROra2ZyuYg5RLgVmTs1FpnvdtKcn6iD+5HyFw',
        'Javier', 'Lopez', 'Gomez', '45904029K', 'gurt'),
       ('luis', '$argon2id$v=19$m=15360,t=2,p=1$EXAMPLEHASHLUIS', 'Luis', 'Martinez', 'Lopez', '87654321B', 'luis456');


INSERT INTO tb_sessions (client_id, token)
VALUES (1, 'gurt'),
       (3, 'token_luis');

INSERT INTO tb_bank_accounts (client_id, iban, balance)
VALUES
    -- Javier
    (1, 'ES9121000418450200051332', 2500.00),
    -- Pepe
    (2, 'ES7620770024003102575766', 1800.00),
    (2, 'ES9820385778983000760236', 750.00),
    -- Luis
    (3, 'ES7921000813610123456789', 3200.00);


INSERT INTO tb_credit_cards (account_id, number, expiration_date, cvv, full_name)
VALUES
    -- Javier
    (1, '4000000000000010', '11/25', '321', 'JAVIER LOPEZ GOMEZ'),
    -- Pepe
    (2, '4000000000000020', '09/26', '654', 'PEPE LOPEZ GOMEZ'),
    (2, '312300000000020', '09/26', '654', 'PEPE LOPEZ GOMEZ'),
    (2, '312300000000070', '09/26', '654', 'PEPE LOPEZ GOMEZ'),
    (2, '312300000000080', '09/26', '654', 'PEPE LOPEZ GOMEZ'),
    (3, '4000000000000030', '01/27', '987', 'PEPE LOPEZ GOMEZ'),
    (3, '4000000000000040', '01/27', '987', 'PEPE LOPEZ GOMEZ'),
    (3, '4000000000000050', '01/27', '987', 'PEPE LOPEZ GOMEZ'),
    -- Luis
    (4, '5100000000000040', '03/27', '111', 'LUIS MARTINEZ LOPEZ');


INSERT INTO tb_movements
(account_id, type, origin, origin_credit_card_id, date, amount, concept)
VALUES
-- =========================
-- JAVIER: cuenta 1
-- =========================
-- NOVIEMBRE 2025
(1, 'DEPOSIT', 'TRANSFER', NULL, '2025-11-01 09:00:00', 1450.00, 'Patas realistas de furro'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-02 08:00:00', 450.00, 'Nuggets soft porn'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-04 13:15:00', 72.00, 'Skibidi toilet'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-05 08:00:00', 90.00, 'Gurt premium'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-07 08:00:00', 45.00, 'Viaje a Tailandia con lady boys'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-09 15:30:00', 58.00, 'Femboys'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-11 12:00:00', 68.00, 'Supermercado'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-13 20:00:00', 85.00, 'Restaurante'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-16 14:30:00', 125.00, 'Ropa Zara'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-18 11:15:00', 75.00, 'Unicornio'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-20 08:00:00', 15.00, 'Semental del culo'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-22 19:45:00', 62.00, 'Gasolina'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-25 13:00:00', 88.00, 'Supermercado'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-27 21:30:00', 45.00, 'Bar'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-11-29 15:00:00', 150.00, 'Electrodomésticos'),

-- DICIEMBRE 2025
(1, 'DEPOSIT', 'TRANSFER', NULL, '2025-12-01 09:00:00', 1500.00, 'Nómina diciembre'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-02 08:00:00', 450.00, 'Alquiler'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-12-04 12:30:00', 95.00, 'Supermercado'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-05 08:00:00', 85.00, 'Luz'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-07 08:00:00', 45.00, 'Internet y móvil'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-12-09 16:00:00', 60.00, 'Gasolina'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-12-11 13:45:00', 82.00, 'Supermercado'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-12-14 20:15:00', 110.00, 'Cena de Navidad'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-12-18 11:30:00', 250.00, 'Regalos Navidad'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-20 08:00:00', 15.00, 'Netflix'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-12-22 18:30:00', 75.00, 'Supermercado'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-12-24 10:00:00', 180.00, 'Compra Navidad'),
(1, 'WITHDRAWAL', 'TRANSFER', NULL, '2025-12-25 12:00:00', 500.00, 'Transferencia familiar'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-12-28 14:00:00', 60.00, 'Gasolina'),
(1, 'WITHDRAWAL', 'CARD', 1, '2025-12-30 19:00:00', 150.00, 'Cena Fin de Año'),

-- ENERO 2026
(1, 'DEPOSIT', 'TRANSFER', NULL, '2026-01-02 09:00:00', 1600.00, 'Nómina enero'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2026-01-03 08:00:00', 450.00, 'Alquiler'),
(1, 'WITHDRAWAL', 'CARD', 1, '2026-01-05 13:00:00', 70.00, 'Supermercado'),
(1, 'WITHDRAWAL', 'DOMICILATION', NULL, '2026-01-07 08:00:00', 45.00, 'Internet y móvil'),
(1, 'WITHDRAWAL', 'CARD', 1, '2026-01-09 16:30:00', 58.00, 'Gasolina'),
(1, 'WITHDRAWAL', 'CARD', 1, '2026-01-11 12:15:00', 85.00, 'Supermercado'),
(1, 'WITHDRAWAL', 'CARD', 1, '2026-01-13 20:00:00', 95.00, 'Restaurante'),

-- =========================
-- PEPE: cuenta 2
-- =========================
-- OCTUBRE 2025
(2, 'DEPOSIT', 'TRANSFER', NULL, '2025-10-01 09:00:00', 1400.00, 'dinero para consolador de caballo'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-02 08:00:00', 450.00, 'Suscripción Brazzers'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-03 12:30:00', 65.00, 'Supermercado Mercadona'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-05 19:45:00', 35.00, 'Hytale'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-07 08:00:00', 45.00, 'Condones + yuca + lubricante'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-10 14:20:00', 85.00, 'Grimpadora'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-12 20:15:00', 28.00, 'Cine'),
(2, 'WITHDRAWAL', 'TRANSFER', NULL, '2025-10-15 10:00:00', 200.00, 'Fenta'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-17 13:00:00', 70.00, 'Foto pies'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-19 18:30:00', 120.00, 'Muñeca hinchable'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-20 08:00:00', 15.00, 'Netflix'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-22 16:45:00', 55.00, 'Onlyfans de pies'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-24 11:30:00', 95.00, 'Feet finder'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-27 21:00:00', 42.00, 'Suscripción grinder'),
(2, 'WITHDRAWAL', 'CARD', 1, '2025-10-30 12:00:00', 78.00, 'Belle Delphine feet pics'),
(2, 'DEPOSIT', 'TRANSFER', NULL, '2025-10-01 09:00:00', 1200.00, 'Nómina octubre'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-02 08:00:00', 380.00, 'Alquiler'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-10-04 11:00:00', 55.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-10-06 19:30:00', 40.00, 'Cena'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-08 08:00:00', 50.00, 'Seguro'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-10-11 14:00:00', 65.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-10-15 17:45:00', 45.00, 'Gasolina'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-10-18 12:30:00', 72.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-10-21 21:00:00', 38.00, 'Bar'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-10-25 13:15:00', 68.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-10-28 16:00:00', 50.00, 'Gasolina'),

-- NOVIEMBRE 2025
(2, 'DEPOSIT', 'TRANSFER', NULL, '2025-11-01 09:00:00', 1200.00, 'Nómina noviembre'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-02 08:00:00', 380.00, 'Alquiler'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-11-05 12:00:00', 60.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-08 08:00:00', 50.00, 'Seguro'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-11-10 18:30:00', 75.00, 'Restaurante'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-11-13 14:45:00', 48.00, 'Gasolina'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-11-16 11:00:00', 70.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-11-19 20:15:00', 42.00, 'Cena'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-11-23 13:30:00', 78.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-11-27 16:45:00', 52.00, 'Gasolina'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-11-30 19:00:00', 85.00, 'Compra online'),

-- DICIEMBRE 2025
(2, 'DEPOSIT', 'TRANSFER', NULL, '2025-12-01 09:00:00', 1300.00, 'Nómina diciembre'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-02 08:00:00', 380.00, 'Alquiler'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-12-05 12:30:00', 68.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-08 08:00:00', 50.00, 'Seguro'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-12-11 15:00:00', 55.00, 'Gasolina'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-12-14 13:15:00', 75.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-12-17 20:30:00', 95.00, 'Cena Navidad'),
(2, 'WITHDRAWAL', 'TRANSFER', NULL, '2025-12-21 15:00:00', 200.00, 'Transferencia'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-12-23 19:00:00', 45.00, 'Cena restaurante'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-12-26 12:00:00', 120.00, 'Regalos'),
(2, 'WITHDRAWAL', 'CARD', 2, '2025-12-29 18:45:00', 60.00, 'Gasolina'),

-- ENERO 2026
(2, 'DEPOSIT', 'TRANSFER', NULL, '2026-01-02 09:00:00', 1350.00, 'Nómina enero'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2026-01-03 08:00:00', 380.00, 'Alquiler'),
(2, 'WITHDRAWAL', 'CARD', 2, '2026-01-06 13:00:00', 65.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'DOMICILATION', NULL, '2026-01-08 08:00:00', 50.00, 'Seguro'),
(2, 'WITHDRAWAL', 'CARD', 2, '2026-01-10 17:30:00', 52.00, 'Gasolina'),
(2, 'WITHDRAWAL', 'CARD', 2, '2026-01-12 14:15:00', 72.00, 'Supermercado'),
(2, 'WITHDRAWAL', 'CARD', 2, '2026-01-14 20:00:00', 88.00, 'Restaurante'),

-- =========================
-- PEPE: cuenta 3
-- =========================
-- OCTUBRE 2025
(3, 'DEPOSIT', 'TRANSFER', NULL, '2025-10-05 10:00:00', 500.00, 'Transferencia ahorros'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-10-08 15:30:00', 35.00, 'Compra online'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-10-12 18:00:00', 80.00, 'Ropa'),
(3, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-15 08:00:00', 12.00, 'Spotify'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-10-20 14:45:00', 45.00, 'Libros'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-10-25 11:30:00', 95.00, 'Electrónica'),

-- NOVIEMBRE 2025
(3, 'DEPOSIT', 'TRANSFER', NULL, '2025-11-03 10:00:00', 600.00, 'Transferencia ahorros'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-11-07 16:00:00', 55.00, 'Compra online'),
(3, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-15 08:00:00', 12.00, 'Spotify'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-11-18 13:30:00', 120.00, 'Zapatos'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-11-23 19:15:00', 68.00, 'Perfumería'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-11-28 14:00:00', 42.00, 'Decoración'),

-- DICIEMBRE 2025
(3, 'DEPOSIT', 'TRANSFER', NULL, '2025-12-02 10:00:00', 800.00, 'Transferencia extra'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-12-06 17:30:00', 85.00, 'Compra online'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-12-10 15:00:00', 150.00, 'Regalos Navidad'),
(3, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-15 08:00:00', 12.00, 'Spotify'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-12-19 12:30:00', 95.00, 'Juguetes'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-12-24 18:00:00', 180.00, 'Compra Navidad'),
(3, 'WITHDRAWAL', 'CARD', 3, '2025-12-27 12:00:00', 120.00, 'Compra ropa'),
(3, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-29 09:00:00', 60.00, 'Seguro adicional'),

-- ENERO 2026
(3, 'DEPOSIT', 'TRANSFER', NULL, '2026-01-03 10:00:00', 700.00, 'Transferencia enero'),
(3, 'WITHDRAWAL', 'CARD', 3, '2026-01-07 16:30:00', 75.00, 'Compra online'),
(3, 'WITHDRAWAL', 'CARD', 3, '2026-01-10 13:45:00', 110.00, 'Accesorios'),
(3, 'WITHDRAWAL', 'DOMICILATION', NULL, '2026-01-14 08:00:00', 12.00, 'Spotify'),

-- =========================
-- LUIS: cuenta 4
-- =========================
-- OCTUBRE 2025
(4, 'DEPOSIT', 'TRANSFER', NULL, '2025-10-01 09:00:00', 2800.00, 'Nómina octubre'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-02 08:00:00', 650.00, 'Hipoteca'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-03 13:00:00', 120.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-05 08:00:00', 55.00, 'Gimnasio'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-07 17:30:00', 85.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-08 08:00:00', 45.00, 'Seguro coche'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-10 12:15:00', 110.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-13 20:00:00', 145.00, 'Restaurante'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-16 15:45:00', 95.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-18 11:30:00', 125.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-10-20 08:00:00', 25.00, 'Prime Video'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-22 19:15:00', 180.00, 'Ropa'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-25 14:00:00', 135.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-28 16:30:00', 90.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-10-30 21:00:00', 75.00, 'Bar'),

-- NOVIEMBRE 2025
(4, 'DEPOSIT', 'TRANSFER', NULL, '2025-11-01 09:00:00', 2900.00, 'Nómina noviembre'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-02 08:00:00', 650.00, 'Hipoteca'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-04 12:30:00', 128.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-05 08:00:00', 55.00, 'Gimnasio'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-07 08:00:00', 120.00, 'Luz'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-09 16:00:00', 88.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-08 08:00:00', 45.00, 'Seguro coche'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-11 13:15:00', 115.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-14 20:30:00', 165.00, 'Restaurante'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-17 15:00:00', 92.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-19 11:45:00', 132.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-11-20 08:00:00', 25.00, 'Prime Video'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-22 18:00:00', 250.00, 'Electrónica'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-25 13:30:00', 140.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-28 16:45:00', 95.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-11-30 19:30:00', 85.00, 'Cena'),

-- DICIEMBRE 2025
(4, 'DEPOSIT', 'TRANSFER', NULL, '2025-12-01 09:00:00', 3000.00, 'Nómina diciembre'),
(4, 'DEPOSIT', 'TRANSFER', NULL, '2025-12-05 10:00:00', 500.00, 'Extra Navidad'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-02 08:00:00', 650.00, 'Hipoteca'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-04 13:00:00', 145.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-05 08:00:00', 55.00, 'Gimnasio'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-07 08:00:00', 110.00, 'Luz'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-09 17:00:00', 98.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-08 08:00:00', 45.00, 'Seguro coche'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-11 12:00:00', 135.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-14 21:00:00', 185.00, 'Cena empresa'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-17 16:30:00', 105.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-19 11:15:00', 320.00, 'Regalos Navidad'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-20 08:00:00', 25.00, 'Prime Video'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-21 16:00:00', 150.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2025-12-23 08:00:00', 50.00, 'Gimnasio extra'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-24 10:30:00', 280.00, 'Compra Nochebuena'),
(4, 'DEPOSIT', 'TRANSFER', NULL, '2025-12-25 09:00:00', 3200.00, 'Bonus Navidad'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-26 18:00:00', 200.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-28 14:00:00', 300.00, 'Electrónica'),
(4, 'WITHDRAWAL', 'CARD', 4, '2025-12-30 20:00:00', 220.00, 'Cena Fin de Año'),

-- ENERO 2026
(4, 'DEPOSIT', 'TRANSFER', NULL, '2026-01-02 09:00:00', 3100.00, 'Nómina enero'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2026-01-03 08:00:00', 650.00, 'Hipoteca'),
(4, 'DEPOSIT', 'TRANSFER', NULL, '2026-01-05 09:00:00', 300.00, 'Devolución gastos'),
(4, 'WITHDRAWAL', 'CARD', 4, '2026-01-06 13:30:00', 130.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2026-01-07 08:00:00', 55.00, 'Gimnasio'),
(4, 'WITHDRAWAL', 'DOMICILATION', NULL, '2026-01-08 08:00:00', 60.00, 'Seguro coche'),
(4, 'WITHDRAWAL', 'CARD', 4, '2026-01-09 16:00:00', 92.00, 'Gasolina'),
(4, 'WITHDRAWAL', 'CARD', 4, '2026-01-11 12:45:00', 125.00, 'Supermercado'),
(4, 'WITHDRAWAL', 'CARD', 4, '2026-01-12 18:00:00', 250.00, 'Ropa'),
(4, 'WITHDRAWAL', 'CARD', 4, '2026-01-14 20:30:00', 175.00, 'Restaurante');
