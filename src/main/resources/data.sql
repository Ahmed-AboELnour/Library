INSERT INTO author (name, biography) VALUES ('Author 1', 'Biography 1');
INSERT INTO author (name, biography) VALUES ('Author 2', 'Biography 2');

INSERT INTO book (title, author_id, isbn, published_date, available) VALUES ('Book 1', 1, '10', '2024-07-18', true);
INSERT INTO book (title, author_id, isbn, published_date, available) VALUES ('Book 2', 2, '11', '2024-07-20', true);


INSERT INTO Loan (book_id, borrower, loan_Date, return_Date) VALUES (1, 'Ahmed', '2024-07-18', '2024-07-20');

--INSERT INTO users (username, password, roles) VALUES ('admin', 'admin', 'ROLE_ADMIN');
--INSERT INTO users (username, password, roles) VALUES ('user', 'password', 'ROLE_USER');
INSERT INTO users (username, password, enabled) VALUES ('testuser', '$2a$10$yJ/L8S7D/xL6s2iBN7DpB.IoohbDj.YS.Dlu9eY5G3qGtRpPTAkWS', true);
INSERT INTO roles (role_name) VALUES ('ADMIN');
SET @user_id = (SELECT id FROM users WHERE username = 'testuser');
SET @role_id = (SELECT id FROM roles WHERE role_name = 'ADMIN');

-- Insert into user_roles table
INSERT INTO user_roles (user_id, role_id) VALUES (@user_id, @role_id);