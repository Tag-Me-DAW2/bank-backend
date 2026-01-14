INSERT INTO tb_clients (username, password, name, last_name1, last_name2, dni, api_key)
VALUES
    ('javier', '$argon2id$v=19$m=15360,t=2,p=1$IWkQZkJkfAWYmn4hvwAQUe61Z3C6+9PzHr5Uu9Qf2VqPHFu480qXQE9fyUhxGXEWrI8qNbchVbBi8D9XErCt8A$XjJrwUROra2ZyuYg5RLgVmTs1FpnvdtKcn6iD+5HyFw','Javier', 'Lopez', 'Gomez', '45904029K', 'gurt'),
    ('pepe', '$argon2id$v=19$m=15360,t=2,p=1$IWkQZkJkfAWYmn4hvwAQUe61Z3C6+9PzHr5Uu9Qf2VqPHFu480qXQE9fyUhxGXEWrI8qNbchVbBi8D9XErCt8A$XjJrwUROra2ZyuYg5RLgVmTs1FpnvdtKcn6iD+5HyFw','Javier', 'Lopez', 'Gomez', '45904029K', 'gurt');

INSERT INTO tb_sessions (client_id, token)
VALUES
    (1, 'gurt');

INSERT INTO tb_bank_accounts (client_id, iban, balance)
VALUES
    (1, 'ES7620770024003102575766', 1500.00),
    (2, 'ES9820385778983000760236', 3200.50);

INSERT INTO tb_credit_cards
(account_id, number, expiration_date, cvv, full_name)
VALUES
    (1, '4111111111111111', '12/27', '123', 'JOHN DOE'),
    (2, '5500000000000004', '06/26', '456', 'JOHN DOE');


INSERT INTO tb_movements
(account_id, type, origin, origin_credit_card_id, amount, concept)
VALUES
-- Movimientos por tarjeta
(1, 'WITHDRAWAL', 'CARD', 1, 50.00, 'Compra supermercado'),
(1, 'WITHDRAWAL', 'CARD', 2, 120.00, 'Gasolina'),

-- Transferencias
(1, 'DEPOSIT', 'TRANSFER', NULL, 500.00, 'Nómina'),
(1, 'WITHDRAWAL', 'TRANSFER', NULL, 200.00, 'Alquiler'),

-- Otra cuenta
(2, 'DEPOSIT', 'TRANSFER', NULL, 1000.00, 'Ingreso inicial');

