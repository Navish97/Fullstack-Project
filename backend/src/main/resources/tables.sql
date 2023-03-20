CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),

    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT,
    type VARCHAR(255),

    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS items (
    id BIGINT AUTO_INCREMENT,
    user_id BIGINT,
    category_id BIGINT,
    title VARCHAR(255),
    brief_description VARCHAR(255),
    description TEXT,
    price DECIMAL,
    longitude VARCHAR(255),
    latitude VARCHAR(255),
    image_urls TEXT,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
    );

CREATE TABLE IF NOT EXISTS bookmark (
    id BIGINT AUTO_INCREMENT,
    user_id BIGINT,
    item_id BIGINT,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
    );

CREATE TABLE IF NOT EXISTS messages (
    id BIGINT AUTO_INCREMENT,
    sender_id BIGINT,
    receiver_id BIGINT,
    item_id BIGINT,
    message TEXT,

    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
    );