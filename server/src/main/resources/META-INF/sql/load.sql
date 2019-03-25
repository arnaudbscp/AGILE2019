INSERT INTO utilisateur (id, login, password, email, role) VALUES (1, 'axel', 'axel', 'axel@mail.com', 'admin');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (2, 'mervo', 'mervo', 'mervo@mail.com', 'user');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (3, 'jonas', 'jonas', 'jonas@mail.com', 'user');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (4, 'toto', 'toto', 'toto@mail.com', 'user');

INSERT INTO evenement (id, nom, date, place, heure, heureFin, description, categorie) VALUES (1, 'PremierCours', '2019-03-20', 10, '15:00:00', '16:30:00', 'Le premier cours', 'Location d`outils');
INSERT INTO evenement (id, nom, date, place, heure, heureFin, description, categorie) VALUES (2, 'DeuxiemeCours', '2019-03-21', 11, '10:30:00', '12:00:00', 'Le deuxieme cours', 'Cours');
INSERT INTO evenement (id, nom, date, place, heure, heureFin, description, categorie) VALUES (3, 'TroisiemeCours', '2019-03-22', 12, '09:30:00', '11:30:00', 'Le troisieme cours', 'Stage');
INSERT INTO evenement (id, nom, date, place, heure, heureFin, description, categorie) VALUES (4, 'QuatriemeCours', '2019-03-23', 13, '10:00:00', '13:00:00', 'Le quatrieme cours', 'Atelier thematique');

INSERT INTO reservation(idevent, iduser) VALUES (1, 1);
INSERT INTO reservation(idevent, iduser) VALUES (1, 2);
INSERT INTO reservation(idevent, iduser) VALUES (2, 3);
INSERT INTO reservation(idevent, iduser) VALUES (2, 4);
INSERT INTO reservation(idevent, iduser) VALUES (3, 1);
INSERT INTO reservation(idevent, iduser) VALUES (3, 3);
INSERT INTO reservation(idevent, iduser) VALUES (3, 4);
INSERT INTO reservation(idevent, iduser) VALUES (4, 3);