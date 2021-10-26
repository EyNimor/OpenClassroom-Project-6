CREATE TABLE IF NOT EXISTS users (
    email VARCHAR(250) PRIMARY KEY NOT NULL,
    first_Name VARCHAR(250) NOT NULL,
    last_Name VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    iban VARCHAR(250),
    wallet FLOAT NOT NULL DEFAULT '0'
);

CREATE TABLE IF NOT EXISTS usernetwork (
    user_email VARCHAR(250) NOT NULL,
    friend_email VARCHAR(250) NOT NULL,
    CONSTRAINT fk_user_email FOREIGN KEY (user_email) REFERENCES users(email),
    CONSTRAINT fk_friend_email FOREIGN KEY (friend_email) REFERENCES users(email),
    PRIMARY KEY (user_email, friend_email)
);

CREATE TABLE IF NOT EXISTS transactions (
    giver_email VARCHAR(250) NOT NULL,
    receiver_email VARCHAR(250) NOT NULL,
    transaction_date DATETIME NOT NULL,
    amount FLOAT,
    description VARCHAR(500),
    CONSTRAINT fk_giver_email FOREIGN KEY (giver_email) REFERENCES users(email),
    CONSTRAINT fk_receiver_email FOREIGN KEY (receiver_email) REFERENCES users(email),
    PRIMARY KEY (giver_email, receiver_email, transaction_date)
);