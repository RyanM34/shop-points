DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction (
                             transaction_id bigint AUTO_INCREMENT PRIMARY KEY,
                             customer_id bigint NOT NULL,
                             amount decimal(10, 2) NOT NULL,
                             issued_at datetime DEFAULT NULL
);

INSERT INTO transaction (customer_id, amount, issued_at)
VALUES (1, 40.00, '2024-01-05 12:35:22'),
       (1, 60.00, '2024-02-05 19:43:32'),
       (1, 120.00, '2024-03-05 19:48:30');
