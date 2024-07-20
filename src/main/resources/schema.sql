DROP TABLE IF EXISTS loan;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
--DROP TABLE IF EXISTS users;
CREATE TABLE author (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         biography TEXT
);

CREATE TABLE book (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author_id BIGINT NOT NULL,
                       isbn VARCHAR(13),
                       published_date DATE,
                       available BOOLEAN,
                       FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE loan (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       book_id BIGINT NOT NULL,
                       borrower VARCHAR(255) NOT NULL,
                       loan_date DATE,
                       return_date DATE,
                       FOREIGN KEY (book_id) REFERENCES book(id)
);

-- Drop existing tables
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id)
);



