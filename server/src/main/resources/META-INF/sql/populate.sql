DROP TABLE IF EXISTS utilisateur CASCADE;
DROP TABLE IF EXISTS evenement CASCADE;

CREATE TABLE utilisateur (id BIGINT IDENTITY NOT NULL, login VARCHAR  UNIQUE  NOT NULL, password VARCHAR NOT NULL, email VARCHAR UNIQUE NOT NULL, role VARCHAR NOT NULL, PRIMARY KEY(id));
ALTER TABLE utilisateur ADD CONSTRAINT CHK_User CHECK (role='admin' OR role='user');

INSERT INTO utilisateur (id, login, password, email, role) VALUES (1, 'axel', 'axel', 'axel@mail.com', 'admin');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (2, 'mervo', 'mervo', 'mervo@mail.com', 'user');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (3, 'jonas', 'jonas', 'jonas@mail.com', 'user');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (4, 'toto', 'toto', 'toto@mail.com', 'user');

CREATE TABLE evenement (id BIGINT IDENTITY NOT NULL, nom VARCHAR NOT NULL, date DATE, heure TIME, PRIMARY KEY(id));

INSERT INTO evenement (id, nom, date, heure) VALUES (1, 'Premier Cours', '2019-03-20', '15:00:00');
INSERT INTO evenement (id, nom, date, heure) VALUES (2, 'Deuxieme Cours', '2019-03-21', '10:30:00');
INSERT INTO evenement (id, nom, date, heure) VALUES (3, 'Troisieme Cours', '2019-03-22', '09:30:00');
INSERT INTO evenement (id, nom, date, heure) VALUES (4, 'Quatrieme Cours', '2019-03-23', '10:00:00');