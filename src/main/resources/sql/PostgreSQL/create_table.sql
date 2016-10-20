DROP TABLE IF EXISTS PLAYERS;
CREATE TABLE PLAYERS(
   PLAYER_ID SERIAL PRIMARY KEY
  ,NAME VARCHAR(100) UNIQUE
  ,BIRTH_DATE DATE
  ,VERSION INTEGER
  );
  
DROP TABLE IF EXISTS TEAMS;
CREATE TABLE TEAMS(
   TEAM_ID SERIAL PRIMARY KEY
  ,NAME VARCHAR(100)
  );

DROP TABLE IF EXISTS EQUIPMENTS;
CREATE TABLE EQUIPMENTS(
   EQUIPMENT_ID SERIAL PRIMARY KEY
   ,EQUIPMENT_TYPE VARCHAR
   ,JSONEQ_DETAILS JSON
--   ,JSONEQ_DETAILS JSONB
  );

