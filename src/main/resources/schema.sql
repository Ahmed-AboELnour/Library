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

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       roles VARCHAR(255)
);