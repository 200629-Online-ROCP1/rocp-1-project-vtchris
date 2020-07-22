DROP TABLE IF EXISTS archive_account_transactions;
DROP TABLE IF EXISTS archive_accounts;
DROP TABLE IF EXISTS account_transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS account_status;
DROP TABLE IF EXISTS account_types;

CREATE TABLE account_types(
	acct_typ_id 	SERIAL PRIMARY KEY,
	acct_typ_name	VARCHAR(30) NOT NULL UNIQUE,
	acct_rate		NUMERIC(8,3) DEFAULT 0,
	acct_last_nbr	INTEGER,
	created_at		TIMESTAMP DEFAULT current_timestamp 
);

CREATE TABLE account_status(
	acct_status_id 	SERIAL PRIMARY KEY,
	acct_status		VARCHAR(30) NOT NULL UNIQUE,
	created_at		TIMESTAMP DEFAULT current_timestamp 
);

CREATE TABLE accounts (
	account_id			SERIAL PRIMARY KEY,
	user_id_fk			INTEGER REFERENCES users(user_id),
	acct_nbr 			INTEGER,
	balance				NUMERIC(19,2) DEFAULT 0,
	acct_status_id_fk	INTEGER REFERENCES account_status(acct_status_id),	
	acct_typ_id_fk		INTEGER REFERENCES account_types(acct_typ_id),	
	created_at			TIMESTAMP DEFAULT current_timestamp 
);

CREATE TABLE archive_accounts (
	record_id			SERIAL PRIMARY KEY,
	account_id			INTEGER,
	user_id_fk			INTEGER,
	acct_nbr 			INTEGER,
	balance				NUMERIC(19,2),
	acct_status_id_fk	INTEGER REFERENCES account_status(acct_status_id),	
	acct_typ_id_fk		INTEGER REFERENCES account_types(acct_typ_id),	
	created_at			TIMESTAMP,
	deleted_by			INTEGER,
	deleted_at			TIMESTAMP DEFAULT current_timestamp 
);


CREATE TABLE account_transactions (
	transaction_id		SERIAL PRIMARY KEY,
	account_id_fk		INTEGER REFERENCES accounts(account_id),
	trans_type			VARCHAR(1),
	debit				NUMERIC(19,2),
	credit				NUMERIC(19,2),
	signed_amount		NUMERIC(19,2),
	running_balance		NUMERIC(19,2),
	status				VARCHAR(1),
	memo				VARCHAR(40),
	user_id_fk			INTEGER REFERENCES users(user_id),
	transaction_dt		TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE archive_account_transactions (
	record_id			SERIAL PRIMARY KEY,
	transaction_id		INTEGER,
	account_id_fk		INTEGER,
	trans_type			VARCHAR(1),
	debit				NUMERIC(19,2),
	credit				NUMERIC(19,2),
	signed_amount		NUMERIC(19,2),
	running_balance		NUMERIC(19,2),
	status				VARCHAR(1),
	memo				VARCHAR(40),
	user_id_fk			INTEGER,
	transaction_dt		TIMESTAMP,
	deleted_by			INTEGER,
	deleted_at			TIMESTAMP DEFAULT current_timestamp
);

INSERT INTO account_types(acct_typ_name,acct_rate,acct_last_nbr)
VALUES		('CHECKING',0.0,1000005),
			('SAVINGS',0.05,2000003);
			
INSERT INTO account_status(acct_status)
VALUES		('PENDING'),
			('OPEN'),
			('FROZEN'),
			('CLOSED'),
			('DENIED');
			
INSERT INTO accounts (user_id_fk,acct_nbr,balance,acct_status_id_fk,acct_typ_id_fk)
VALUES 		(1,1000001,0,2,1),
			(1,2000001,0,2,2),
			(2,1000002,0,1,1),
			(3,1000003,0,1,1),
			(3,2000002,0,2,2),
			(4,1000004,0,1,1),
			(5,2000003,0,1,2),
			(6,1000005,0,5,1);