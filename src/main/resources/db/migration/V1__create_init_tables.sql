CREATE TABLE account (
  account_id bigint PRIMARY KEY NOT NULL,
  name VARCHAR(100) NOT NULL,
  balance_id bigint NOT NULL
);

CREATE TABLE balance (
    balance_id bigint PRIMARY KEY NOT NULL,
    balance DECIMAL NOT NULL,
    currency VARCHAR(255),
    exchange_date DATE
);

ALTER table balance
ADD CONSTRAINT fk_balance
FOREIGN KEY (balance_id)
REFERENCES account (account_id);