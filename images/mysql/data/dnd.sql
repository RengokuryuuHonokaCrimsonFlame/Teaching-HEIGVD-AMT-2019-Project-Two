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
CREATE TABLE classe (     
	id VARCHAR(30),        
	CONSTRAINT ID_Classe PRIMARY KEY (id)
);

CREATE TABLE race (     
	id VARCHAR(30),        
	CONSTRAINT ID_race PRIMARY KEY (id)
);


CREATE TABLE player (        
	id VARCHAR(100) NOT NULL, 
	password VARCHAR(100) NOT NULL, 	
	strength SMALLINT UNSIGNED NOT NULL,
	dexterity SMALLINT UNSIGNED NOT NULL,
	constitution SMALLINT UNSIGNED NOT NULL,
	intelligence SMALLINT UNSIGNED NOT NULL,
	wisdom SMALLINT UNSIGNED NOT NULL,
	charisma SMALLINT UNSIGNED NOT NULL,
	fkRace VARCHAR(30) NOT NULL,     
	fkClasse VARCHAR(30) NOT NULL,
	CONSTRAINT ID_player PRIMARY KEY (id)
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

-- Constraints Section
-- ___________________ 
ALTER TABLE player 
	ADD CONSTRAINT FKplayerClasse
	FOREIGN KEY (fkClasse)     
	REFERENCES classe (id) 
	ON UPDATE CASCADE;
	
ALTER TABLE player 
	ADD CONSTRAINT FKplayerRace
	FOREIGN KEY (fkRace)     
	REFERENCES race (id) 
	ON UPDATE CASCADE;
	
ALTER TABLE playerParty
	ADD CONSTRAINT FKplayerPartyPlayer
	FOREIGN KEY (fkPlayer)     
	REFERENCES player (id) 
	ON UPDATE CASCADE;
	
ALTER TABLE playerParty
	ADD CONSTRAINT FKplayerPartyParty
	FOREIGN KEY (fkParty)     
	REFERENCES party (id) 
	ON UPDATE CASCADE;	
	
INSERT INTO classe (id) VALUE ('Barbarian');
INSERT INTO classe (id) VALUE ('Bard');
INSERT INTO classe (id) VALUE ('Cleric');
INSERT INTO classe (id) VALUE ('Druid');
INSERT INTO classe (id) VALUE ('Fighter');
INSERT INTO classe (id) VALUE ('Monk');
INSERT INTO classe (id) VALUE ('Paladin');
INSERT INTO classe (id) VALUE ('Ranger');
INSERT INTO classe (id) VALUE ('Rogue');
INSERT INTO classe (id) VALUE ('Sorcerer');
INSERT INTO classe (id) VALUE ('Warlock');
INSERT INTO classe (id) VALUE ('Wizard');
	
INSERT INTO race (id) VALUE ('Dragonborn');
INSERT INTO race (id) VALUE ('Dwarf');
INSERT INTO race (id) VALUE ('Elf');
INSERT INTO race (id) VALUE ('Gnome');
INSERT INTO race (id) VALUE ('Halfling');
INSERT INTO race (id) VALUE ('Half-Elf');
INSERT INTO race (id) VALUE ('Half-Orc');
INSERT INTO race (id) VALUE ('Human');
INSERT INTO race (id) VALUE ('Tiefling');