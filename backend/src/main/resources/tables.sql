-- Drop tables if they exist
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS chats;
DROP TABLE IF EXISTS item_images;
DROP TABLE IF EXISTS bookmarks;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) DEFAULT uuid(),
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

--user
--have to insert id manually to use in other tables
--admin password is admin
--user password is user
INSERT INTO users (id, name, email, password, role) VALUES ('26033564-1b1b-4b41-a61d-21d3bfbbbf65', 'admin', 'admin@localhost.com', '$2a$12$oQRQsS8Kq8y1y1bdeyyZTeMZKcrFbEqj2aGq79puw42/BTihbA/r2', 'ADMIN');
INSERT INTO users (id, name, email, password, role) VALUES ('69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', 'user', 'user@localhost.com', '$2a$12$/cCPUTL8kaPTdH6dxvSrVO2vLD0aWTprTilMH0eXdXtYkhFEXshri', 'USER');

--category
INSERT INTO category (id, type, icon_url) VALUES
    (1, 'Clothing', 'fs-regular fa-shirt'),
    (2, 'Books', 'fs-regular fa-book'),
    (3, 'Electronics', 'fs-regular fa-bolt'),
    (4, 'Furniture', 'fs-regular fa-couch'),
    (5, 'Sports Equipment', 'fs-regular fa-volleyball'),
    (6, 'Toys', 'fs-regular fa-puzzle-piece'),
    (7, 'Tools', 'fs-regular fa-wrench'),
    (8, 'Vehicles', 'fs-regular fa-car'),
    (9, 'Other', 'fs-regular fa-tag');

--items

INSERT INTO items (user_id, category_id, title, description, price, longitude, latitude) VALUES
    ('26033564-1b1b-4b41-a61d-21d3bfbbbf65', 1, 'Red T-Shirt', 'Brand new red t-shirt, never been worn.', 9.99, 10.0, 20.0),
    ('69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', 2, 'Harry Potter and the Philosophers Stone', 'The first book in the Harry Potter series.', 19.99, 15.0, 25.0),
    ('26033564-1b1b-4b41-a61d-21d3bfbbbf65', 3, 'Smartphone', 'Used smartphone in good condition.', 299.99, 30.0, 40.0),
    ('69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', 4, 'Sofa', 'Comfortable sofa in good condition.', 499.99, 35.0, 45.0),
    ('26033564-1b1b-4b41-a61d-21d3bfbbbf65', 5, 'Tennis Racket', 'Used tennis racket in good condition.', 49.99, 40.0, 50.0),
    ('69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', 6, 'Lego Set', 'Brand new Lego set, unopened.', 29.99, 45.0, 55.0);

--bookmarks

INSERT INTO bookmarks (user_id, item_id) VALUES
    ('26033564-1b1b-4b41-a61d-21d3bfbbbf65', 2),
    ('69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', 4),
    ('26033564-1b1b-4b41-a61d-21d3bfbbbf65', 6),
    ('69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', 1);


--chats
INSERT INTO chats (user_one, user_two, item_id) VALUES
    ('26033564-1b1b-4b41-a61d-21d3bfbbbf65', '69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', 1),
    ('26033564-1b1b-4b41-a61d-21d3bfbbbf65', '69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', 2),
    ('69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', '26033564-1b1b-4b41-a61d-21d3bfbbbf65', 1),
    ('69b1f16a-b0c3-4efb-b0f6-a78f500cf3bb', '26033564-1b1b-4b41-a61d-21d3bfbbbf65', 2);
