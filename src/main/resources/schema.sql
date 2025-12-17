CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE ,
    password VARCHAR(20) NOT NULL
);

CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          text TEXT,
                          timestamp DATETIME (6),
                          user_id BIGINT,
                          FOREIGN KEY (user_id) REFERENCES users(id) on delete CASCADE
);

ALTER TABLE messages (
    timestamp DATETIME(4)
    );