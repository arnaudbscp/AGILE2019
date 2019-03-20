DROP TABLE IF EXISTS utilisateur CASCADE;

CREATE TABLE utilisateur (id BIGINT IDENTITY NOT NULL, login VARCHAR  UNIQUE  NOT NULL, password VARCHAR NOT NULL, email VARCHAR UNIQUE NOT NULL, role VARCHAR NOT NULL, PRIMARY KEY(id));
ALTER TABLE utilisateur ADD CONSTRAINT CHK_User CHECK (role='admin' OR role='user');

INSERT INTO utilisateur (id, login, password, email, role) VALUES (1, 'axel', 'axel', 'axel@mail.com', 'admin');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (2, 'mervo', 'mervo', 'mervo@mail.com', 'user');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (3, 'jonas', 'jonas', 'jonas@mail.com', 'user');
INSERT INTO utilisateur (id, login, password, email, role) VALUES (4, 'toto', 'toto', 'toto@mail.com', 'user');
