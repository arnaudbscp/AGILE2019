INSERT INTO utilisateur (id, login, password, email, role) VALUES (1, 'axel', 'axel', 'axel@mail.com', 'admin');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (2, 'mervo', 'mervo', 'mervo@mail.com', 'user');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (3, 'jonas', 'jonas', 'jonas@mail.com', 'user');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (4, 'toto', 'toto', 'toto@mail.com', 'user');

INSERT INTO evenement (id, nom, date, place, heure) VALUES (1, 'Premier Cours', '2019-03-20', 10, '15:00:00');
INSERT INTO evenement (id, nom, date, place, heure) VALUES (2, 'Deuxieme Cours', '2019-03-21', 11, '10:30:00');
INSERT INTO evenement (id, nom, date, place, heure) VALUES (3, 'Troisieme Cours', '2019-03-22', 12, '09:30:00');
INSERT INTO evenement (id, nom, date, place, heure) VALUES (4, 'Quatrieme Cours', '2019-03-23', 13, '10:00:00');

INSERT INTO evenementInscrits(idevent, iduser) VALUES (1, 1);
INSERT INTO evenementInscrits(idevent, iduser) VALUES (1, 2);
INSERT INTO evenementInscrits(idevent, iduser) VALUES (2, 3);
INSERT INTO evenementInscrits(idevent, iduser) VALUES (2, 4);
INSERT INTO evenementInscrits(idevent, iduser) VALUES (3, 1);
INSERT INTO evenementInscrits(idevent, iduser) VALUES (3, 3);
INSERT INTO evenementInscrits(idevent, iduser) VALUES (3, 4);
INSERT INTO evenementInscrits(idevent, iduser) VALUES (4, 3);