CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL
);

CREATE TABLE messages
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    text      TEXT,
    timestamp DATETIME(6),
    user_id   BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id) on delete CASCADE
);

CREATE TABLE comment
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    text      TEXT,
    timestamp DATETIME(6),
    user_id   BIGINT NOT NULL,
    message_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (message_id) REFERENCES messages (id) ON DELETE CASCADE
);

CREATE TABLE profile
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    profile_picture VARCHAR(250),
    biography VARCHAR(250),
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE tag
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

CREATE TABLE message_tags
(
    message_id BIGINT NOT NULL,
    tag_id     BIGINT NOT NULL,
    PRIMARY KEY (message_id, tag_id),
    FOREIGN KEY (message_id) REFERENCES messages (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);