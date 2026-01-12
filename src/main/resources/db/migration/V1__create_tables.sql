CREATE TABLE `tb_clients` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `username` varchar(50) NOT NULL,
                            `password` varchar(255) NOT NULL,
                            `name` varchar(50) NOT NULL,
                            `last_name1` varchar(50) DEFAULT NULL,
                            `last_name2` varchar(50) DEFAULT NULL,
                            `dni` varchar(9) DEFAULT NULL,
                            `api_key` varchar(100) DEFAULT NULL,
                            `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE `tb_sessions` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `client_id` int(11) NOT NULL,
                               `token` varchar(255) NOT NULL,
                               `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               KEY `tb_sessions_FK` (`client_id`),
                               CONSTRAINT `tb_sessions_FK` FOREIGN KEY (`client_id`) REFERENCES `tb_clients` (`id`)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

CREATE TABLE  `tb_credit_cards` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                               `bank_account_id` int(11) NOT NULL,
                               `number` varchar(16) NOT NULL,
                               `expiration_date` varchar(7) NOT NULL,
                               `cvv` varchar(4) NOT NULL,
                               `full_name` varchar(100) NOT NULL,
                               `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               KEY `tb_bank_accounts_FK` (`bank_account_id`),
                               CONSTRAINT `tb_bank_accounts_FK` FOREIGN KEY (`bank_account_id`) REFERENCES `tb_bank_accounts` (`id`)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;