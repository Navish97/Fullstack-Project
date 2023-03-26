CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36),
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),

    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT,
    type VARCHAR(255),
    icon_url VARCHAR(255),

    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS items (
    id BIGINT AUTO_INCREMENT,
    user_id VARCHAR(36),
    category_id BIGINT,
    title VARCHAR(255),
    description TEXT,
    price DECIMAL,
    longitude DOUBLE,
    latitude DOUBLE,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
    );

CREATE TABLE IF NOT EXISTS bookmarks (
    id BIGINT AUTO_INCREMENT,
    user_id VARCHAR(36),
    item_id BIGINT,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
    );

CREATE TABLE IF NOT EXISTS item_images (
    id BIGINT AUTO_INCREMENT,
    item_id BIGINT,
    data LONGBLOB,
    content_type VARCHAR(255),

    PRIMARY KEY (id),
    FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS chats (
    id BIGINT AUTO_INCREMENT,
    user_one VARCHAR(36),
    user_two VARCHAR(36),
    item_id BIGINT,

    PRIMARY KEY(id),
    FOREIGN KEY (user_one) REFERENCES users(id),
    FOREIGN KEY (user_two) REFERENCES users(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
    );
CREATE TABLE IF NOT EXISTS messages (
    id BIGINT AUTO_INCREMENT,
    sender_id VARCHAR(36),
    receiver_id VARCHAR(36),
    message TEXT,
    chat_id BIGINT,

    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id),
    FOREIGN KEY (chat_id) REFERENCES chats(id)
    );