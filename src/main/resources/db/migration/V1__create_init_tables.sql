CREATE TABLE accounts (
  account_id bigint PRIMARY KEY NOT NULL,
  user_id bigint NOT NULL,
  currency bigint NOT NULL,
  amount DECIMAL NOT NULL DEFAULT 0.00
);

CREATE TABLE users (
    user_id bigint PRIMARY KEY NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

ALTER table accounts
ADD CONSTRAINT fk_accounts
FOREIGN KEY (user_id)
REFERENCES users (user_id);




