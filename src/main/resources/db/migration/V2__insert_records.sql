INSERT INTO tb_clients (username, password, name, last_name1, last_name2, dni, api_key)
VALUES
    ('javier', '$argon2id$v=19$m=15360,t=2,p=1$IWkQZkJkfAWYmn4hvwAQUe61Z3C6+9PzHr5Uu9Qf2VqPHFu480qXQE9fyUhxGXEWrI8qNbchVbBi8D9XErCt8A$XjJrwUROra2ZyuYg5RLgVmTs1FpnvdtKcn6iD+5HyFw','Javier', 'Lopez', 'Gomez', '45904029K', 'gurt');

INSERT INTO tb_sessions (client_id, token)
VALUES
    (1, 'gurt');

