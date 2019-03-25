CREATE TABLE utilisateur (id BIGINT IDENTITY NOT NULL, login VARCHAR  UNIQUE  NOT NULL, password VARCHAR NOT NULL, email VARCHAR UNIQUE NOT NULL, role VARCHAR NOT NULL, PRIMARY KEY(id));
CREATE TABLE evenement (id BIGINT IDENTITY NOT NULL, nom VARCHAR NOT NULL, date DATE, heure TIME, heureFin TIME , description VARCHAR, prix INT, categorie VARCHAR NOT NULL, place INT NOT NULL, PRIMARY KEY(id));
CREATE TABLE reservation(idevent INT NOT NULL, iduser INT NOT NULL, PRIMARY KEY (idevent, iduser));
ALTER TABLE utilisateur ADD CONSTRAINT CHK_User CHECK (role='admin' OR role='user');
ALTER TABLE reservation ADD CONSTRAINT FK_reservation_idevent FOREIGN KEY (idevent) REFERENCES evenement (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE reservation ADD CONSTRAINT FK_reservation_iduser FOREIGN KEY (iduser) REFERENCES utilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE;