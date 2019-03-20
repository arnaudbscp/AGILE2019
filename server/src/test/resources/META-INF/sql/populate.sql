DROP TABLE IF EXISTS utilisateur CASCADE;

CREATE TABLE utilisateur (id BIGINT NOT NULL, login VARCHAR  UNIQUE  NOT NULL, password VARCHAR NOT NULL, PRIMARY KEY(id), role VARCHAR NOT NULL);
ALTER TABLE utilisateur ADD CONSTRAINT CHK_User CHECK (role='admin' OR role='user');

INSERT INTO utilisateur (id, login, password, role) VALUES (1, 'axel', 'axel', 'admin');
INSERT INTO utilisateur (id, login, password, role) VALUES (2, 'mervo', 'mervo', 'user');
INSERT INTO utilisateur (id, login, password, role) VALUES (3, 'jonas', 'jonas', 'user');
INSERT INTO utilisateur (id, login, password, role) VALUES (4, 'toto', 'toto', 'user');
