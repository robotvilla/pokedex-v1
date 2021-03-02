USE PokedexDB;

INSERT INTO Pokemon (name, cardNumber, isRare, quantity, setId)
VALUES ('Jirachi', '119/185', 1, 2, 1);
INSERT INTO Pokemon (name, cardNumber, isRare, quantity, setId)
VALUES ('Houndoom V', '21/189', 0, 1, 2);

INSERT INTO Pokemon_Set (setName) VALUES ('Vivid Voltage');
INSERT INTO Pokemon_Set (setName) VALUES ('Darkness Ablaze');
INSERT INTO Pokemon_Set (setName) VALUES ('Unified Minds');
INSERT INTO Pokemon_Set (setName) VALUES ('Sword and Shield');
INSERT INTO Pokemon_Set (setName) VALUES ('Crimson Invasion');
INSERT INTO Pokemon_Set (setName) VALUES ('Rebel Clash');
INSERT INTO Pokemon_Set (setName) VALUES ('Cosmic Eclipse');
INSERT INTO Pokemon_Set (setName) VALUES ('Unbroken Bonds');
INSERT INTO Pokemon_Set (setName) VALUES ('Team Up');
INSERT INTO Pokemon_Set (setName) VALUES ('Burning Shadows');
INSERT INTO Pokemon_Set (setName) VALUES ('Sun and Moon');
INSERT INTO Pokemon_Set (setName) VALUES ('XY Evolutions');


-- select p.cardId, p.name, ps.setName, p.cardNumber, p.isRare, p.quantity, ps.setId from pokemon p
-- LEFT JOIN Pokemon_Set ps ON p.setId = ps.setId;

-- select * from Pokemon_Set ORDER BY setName;

-- delete from pokemon where cardId >= 4;

-- select p.cardId, p.name, ps.setName, p.cardNumber, p.isRare, p.quantity, ps.setId from pokemon p
-- LEFT JOIN Pokemon_Set ps ON p.setId = ps.setId
-- WHERE p.cardId = 2;

-- UPDATE Pokemon 
-- SET name = 'Simisage', cardNumber = '7/189', isRare = 0, setId = 1, quantity = 5
-- WHERE cardId = 74;
