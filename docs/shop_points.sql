DROP TABLE IF EXISTS customer;

CREATE TABLE customer
(
    customer_id bigint AUTO_INCREMENT PRIMARY KEY,
    name        varchar(64) DEFAULT NULL
);

INSERT INTO customer (customer_id, name)
VALUES (1, 'test');

DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction (
                             transaction_id bigint AUTO_INCREMENT PRIMARY KEY,
                             customer_id bigint NOT NULL,
                             amount decimal(10, 2) NOT NULL,
                             created_at datetime DEFAULT NULL
);

INSERT INTO transaction (customer_id, amount, created_at)
VALUES (1, 120.00, '2024-04-05 12:35:22'),
       (1, 120.00, '2024-04-05 19:43:32'),
       (1, 120.00, '2024-04-05 19:48:30');
