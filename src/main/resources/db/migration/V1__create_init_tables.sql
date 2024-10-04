CREATE TABLE accounts (
  account_id bigint PRIMARY KEY NOT NULL,
  user_name VARCHAR(100) NOT NULL,
  balance_id bigint NOT NULL
);

CREATE TABLE balances (
    balance_id bigint PRIMARY KEY NOT NULL,
    amount DECIMAL NOT NULL,
    currency VARCHAR(255),
    exchange_date DATE
);

ALTER table balances
ADD CONSTRAINT fk_balances
FOREIGN KEY (balance_id)
REFERENCES accounts (account_id);

CREATE TABLE users (
    user_id bigint PRIMARY KEY NOT NULL,
    first_name VARCHAR(255),
    surname VARCHAR(255)
);