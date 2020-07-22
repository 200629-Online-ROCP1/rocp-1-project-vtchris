DROP TABLE IF EXISTS archive_users;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles(
	role_id 		SERIAL PRIMARY KEY,
	role_name		VARCHAR(30) NOT NULL UNIQUE,
	created_at		TIMESTAMP DEFAULT current_timestamp 
);

CREATE TABLE users(
	user_id 		SERIAL PRIMARY KEY,
	first_name		VARCHAR(30) NOT NULL,
	last_name		VARCHAR(30) NOT NULL,
	username		VARCHAR(30) NOT NULL UNIQUE,	
	pword			VARCHAR(150),
	email			VARCHAR(100) NOT NULL UNIQUE,
	role_id_fk		INTEGER REFERENCES roles(role_id),	
	created_at		TIMESTAMP DEFAULT current_timestamp 
);

CREATE TABLE archive_users(
	record_id		SERIAL PRIMARY KEY,
	user_id 		INTEGER,
	first_name		VARCHAR(30) NOT NULL,
	last_name		VARCHAR(30) NOT NULL,
	username		VARCHAR(30) NOT NULL UNIQUE,	
	pword			VARCHAR(150),
	email			VARCHAR(100) NOT NULL UNIQUE,
	role_id_fk		INTEGER REFERENCES roles(role_id),	
	created_at		TIMESTAMP,
	deleted_by		INTEGER,
	deleted_at		TIMESTAMP DEFAULT current_timestamp 
);

INSERT INTO roles (role_name)
VALUES ('admin'),('employee'),('customer');

INSERT INTO users (first_name,last_name,username,pword,email,role_id_fk)
VALUES 	('CHRIS','ROSS','cross','99FB2F48C6AF4761F904FC85F95EB56190E5D40B1F44EC3A9C1FA319','cross@changebank.com',1),
		('MICHAEL','SCOTT','mscott','99FB2F48C6AF4761F904FC85F95EB56190E5D40B1F44EC3A9C1FA319','mscott@changebank.com',1),
		('JIM','HALPERT','jhalpert','99FB2F48C6AF4761F904FC85F95EB56190E5D40B1F44EC3A9C1FA319','jhalpert@changebank.com',2),
		('DWIGHT','SCHRUTE','dschrute','99FB2F48C6AF4761F904FC85F95EB56190E5D40B1F44EC3A9C1FA319','dschrute@changebank.com',2),
		('PAM','BEESLY','pbeesly','99FB2F48C6AF4761F904FC85F95EB56190E5D40B1F44EC3A9C1FA319','pbeesly@dundermifflin.com',3),
		('PHYLLIS','VANCE','pvance','99FB2F48C6AF4761F904FC85F95EB56190E5D40B1F44EC3A9C1FA319','pvance@dundermifflin.com',3);
		