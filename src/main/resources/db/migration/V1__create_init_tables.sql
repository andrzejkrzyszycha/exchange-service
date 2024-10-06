CREATE TABLE users (
    user_id bigint PRIMARY KEY NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

CREATE SEQUENCE users_seq INCREMENT BY 1;

CREATE TABLE accounts (
  account_id bigint PRIMARY KEY NOT NULL,
  user_id bigint NOT NULL,
  currency VARCHAR(255) NOT NULL,
  amount DECIMAL NOT NULL,
  foreign key (user_id) references users(user_id)
);

CREATE SEQUENCE account_seq INCREMENT BY 1;






