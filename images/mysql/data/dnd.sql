-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------             
-- * Generation date: Wed Oct 16 08:52:25 2019 
-- ********************************************* 
-- Database Section
-- ________________ 
DROP DATABASE IF EXISTS dnd;
CREATE DATABASE dnd;
USE dnd;
-- DBSpace Section
-- _______________
-- Tables Section


CREATE TABLE player (        
	email VARCHAR(100) NOT NULL, 
	pseudo VARCHAR(100) NOT NULL, 
	password VARCHAR(100) NOT NULL, 	
	strength SMALLINT UNSIGNED NOT NULL,
	dexterity SMALLINT UNSIGNED NOT NULL,
	constitution SMALLINT UNSIGNED NOT NULL,
	intelligence SMALLINT UNSIGNED NOT NULL,
	wisdom SMALLINT UNSIGNED NOT NULL,
	charisma SMALLINT UNSIGNED NOT NULL,
	race VARCHAR(30) NOT NULL,     
	classe VARCHAR(30) NOT NULL,
	administrator BOOLEAN NOT NULL,
	blocked BOOLEAN NOT NULL,
	CONSTRAINT ID_player PRIMARY KEY (email)
);

CREATE TABLE party(
	id VARCHAR(100) NOT NULL,
	reputation INT NOT NULL,
	CONSTRAINT ID_party PRIMARY KEY (id)
);

CREATE TABLE playerParty(
	fkPlayer VARCHAR(100) NOT NULL,     
	fkParty VARCHAR(100) NOT NULL,     
	CONSTRAINT ID_partyPlayer PRIMARY KEY (fkPlayer, fkParty)
);

CREATE TABLE jwttoken(
	id VARCHAR(400) NOT NULL,
	temps DATETIME NOT NULL,
	CONSTRAINT ID_token PRIMARY KEY (id)
);

-- Constraints Section
-- ___________________ 

ALTER TABLE playerParty
	ADD CONSTRAINT FKplayerPartyPlayer
	FOREIGN KEY (fkPlayer)     
	REFERENCES player (email) 
	ON UPDATE CASCADE;
	
ALTER TABLE playerParty
	ADD CONSTRAINT FKplayerPartyParty
	FOREIGN KEY (fkParty)     
	REFERENCES party (id) 
	ON UPDATE CASCADE;	