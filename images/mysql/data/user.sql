-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------             
-- * Generation date: Wed Oct 16 08:52:25 2019 
-- ********************************************* 
-- Database Section
-- ________________ 
DROP DATABASE IF EXISTS gestion;
CREATE DATABASE gestion;
USE gestion;
-- DBSpace Section
-- _______________
-- Tables Section


CREATE TABLE utilisateur (   
	email VARCHAR(100) NOT NULL, 
	password VARCHAR(100) NOT NULL, 
	administrator BOOLEAN NOT NULL,
	blocked BOOLEAN NOT NULL,
	CONSTRAINT ID_player PRIMARY KEY (email)
);

-- Tests Users
-- ___________
INSERT INTO utilisateur VALUES ('adminOk@heig-vd.ch','admin1234',true,false);
INSERT INTO utilisateur VALUES ('normalOk@heig-vd.ch','admin1234',false,false);
INSERT INTO utilisateur VALUES ('adminLocked@heig-vd.ch','admin1234',true,true);
INSERT INTO utilisateur VALUES ('normalLocked@heig-vd.ch','admin1234',false,true);