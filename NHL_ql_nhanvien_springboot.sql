-- Drop the database if it already exists
DROP DATABASE IF EXISTS NHL_ql_nhanvien_springboot;
-- Create database
CREATE DATABASE IF NOT EXISTS NHL_ql_nhanvien_springboot;
USE NHL_ql_nhanvien_springboot;

-- Create table Department
DROP TABLE IF EXISTS 	Department;
CREATE TABLE IF NOT EXISTS Department (
	id 						INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`name` 					VARCHAR(50) NOT NULL UNIQUE KEY,
    total_member			INT	UNSIGNED DEFAULT(0),
    `type`					ENUM('Dev','Test','ScrumMaster','PM') NOT NULL,
    created_date			DATETIME DEFAULT NOW()
);

-- create table: Account
DROP TABLE IF EXISTS `Account`;
CREATE TABLE `Account`(
	id						INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username				VARCHAR(50) NOT NULL UNIQUE KEY,
	`email`					VARCHAR(50) NOT NULL UNIQUE KEY,
	`password` 				VARCHAR(800) NOT NULL,
    `role` 					ENUM('ADMIN','EMPLOYEE','MANAGER') NOT NULL DEFAULT('EMPLOYEE'),
    `status`				TINYINT DEFAULT(0), -- 0: chưa kích hoạt, 1 đã kích hoạt
    department_id 			INT UNSIGNED,
    FOREIGN KEY(department_id) REFERENCES Department(id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS Registration_Account_Token;
CREATE TABLE IF NOT EXISTS Registration_Account_Token(
	id						INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    token					VARCHAR(50) NOT NULL UNIQUE,
	account_id				INT UNSIGNED NOT NULL,
    expiry_date				DATETIME NOT NULL
);

DROP TABLE IF EXISTS Reser_Password_Token;
CREATE TABLE IF NOT EXISTS Reser_Password_Token(
	id						INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    token					VARCHAR(50) NOT NULL UNIQUE,
	account_id				INT UNSIGNED NOT NULL,
    expiry_date				DATETIME NOT NULL
    );

-- =============================================
-- INSERT DATA 
-- =============================================
-- Add data Department
INSERT INTO Department(`name`,`type`,created_date) 
VALUES
						('department1','Dev','2022-09-01'),
						('department2','Test','2022-03-07'),
						('department3','ScrumMaster',NOW()),
						('department4','PM','2022-12-08'),
						('department5','Dev','2023-03-10'),
						('department6','ScrumMaster','2023-03-10'),
						('department7','PM',NOW()),
						('department8','Test','2022-09-01');
INSERT INTO Department(`name`,`type`,total_member,created_date) 
VALUES 					('department9','PM',1,'2021-04-07'),
						('department10','Dev',3,'2022-09-01');
-- Add data Account
-- Password: 123456
INSERT INTO `Account`(username,`email`,`password`,`role`,`status`)
VALUES 				('admin1','email1@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','ADMIN',1),
					('manager1','email2@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','MANAGER',1),
                    ('admin2','email3@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','ADMIN',1),
                    ('username3','email4@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','EMPLOYEE',1),
                    ('admin3','email5@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','ADMIN',1),
                    ('username5','email6@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','EMPLOYEE',1);
                    
INSERT INTO `Account`(username,`email`,`password`,`role`,`status`,department_id)
VALUES 
                    ('manager2','email7@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','MANAGER',1,10),
                    ('username1','email8@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','EMPLOYEE',1,10),
                    ('username8','email9@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','EMPLOYEE',1,10),
                    ('username9','email10@gmail.com','$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi','EMPLOYEE',1,9);
                    
SELECT * FROM Department;
SELECT * FROM `Account`;
SELECT * FROM Registration_Account_Token;

-- $2a$10$TsTMXD/vJjvCUrZSyXgB4.4SNpVmExM5G/t0r4Mw5tV1NXPHnZDnm


-- SELECT * FROM `Account` WHERE department_id IS NULL OR department_id = 10;
-- SELECT COUNT(id) FROM BaiBao WHERE danhmuc_id = 1;
-- UPDATE `Account` SET username = 'demoupdsasdaate', department_id = 3 WHERE id = 9;