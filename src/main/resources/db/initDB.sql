DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS vacancies;
DROP TABLE IF EXISTS proposal;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS usr;
DROP TABLE IF EXISTS room;

DROP SEQUENCE IF EXISTS id_seq;
DROP SEQUENCE IF EXISTS order_id_seq;
-- DROP SEQUENCE IF EXISTS room_id_seq;
-- DROP SEQUENCE IF EXISTS vacancies_id_seq;

CREATE SEQUENCE id_seq START WITH 1;
CREATE SEQUENCE order_id_seq START WITH 1000001;
-- CREATE SEQUENCE room_id_seq START WITH 101;
-- CREATE SEQUENCE vacancies_id_seq START WITH 1;

CREATE TABLE usr
(
  id               BIGINT     NOT NULL PRIMARY KEY DEFAULT nextval('id_seq'),
  login            VARCHAR    NOT NULL,
  name             VARCHAR    NOT NULL,
  surname          VARCHAR    NOT NULL,
  password         VARCHAR    NOT NULL,
  registered       TIMESTAMP  NOT NULL DEFAULT now(),
  enabled          BOOL       NOT NULL DEFAULT TRUE,
  bill             DECIMAL    NOT NULL DEFAULT 0
);
CREATE UNIQUE INDEX usr_login ON usr (login);

CREATE TABLE user_roles
(
  user_id BIGINT NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES usr (id) ON DELETE CASCADE
);

CREATE TABLE room (
  id               BIGINT       NOT NULL PRIMARY KEY UNIQUE DEFAULT nextval('id_seq'),
  number           INTEGER      NOT NULL UNIQUE,
  category         VARCHAR(32)  NOT NULL,
  guests           INT          NOT NULL,
  description      VARCHAR      NOT NULL,
  prise            DECIMAL      NOT NULL
);

CREATE TABLE orders (
  id            BIGINT      NOT NULL PRIMARY KEY DEFAULT nextval('order_id_seq'),
  registered    TIMESTAMP   NOT NULL DEFAULT now(),
  arrival       DATE        NOT NULL,
  departure     DATE        NOT NULL,
  correct_price DECIMAL     NOT NULL,
  status        VARCHAR(32) NOT NULL,
  user_id       INTEGER     NOT NULL,
  room_id       INTEGER     NOT NULL,
  FOREIGN KEY (user_id) REFERENCES usr (id) ON DELETE CASCADE,
  FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE CASCADE
);

CREATE TABLE proposal (
  id          BIGINT      NOT NULL PRIMARY KEY DEFAULT nextval('order_id_seq'),
  registered  TIMESTAMP   NOT NULL DEFAULT now(),
  arrival     DATE        NOT NULL,
  departure   DATE        NOT NULL,
  guests      INT         NOT NULL,
  category    VARCHAR     NOT NULL,
  status      VARCHAR(32) NOT NULL DEFAULT 'NEW',
  user_id     INTEGER     NOT NULL,
  FOREIGN KEY (user_id) REFERENCES usr (id) ON DELETE CASCADE
);

CREATE TABLE vacancies (
  id          BIGINT      NOT NULL PRIMARY KEY DEFAULT nextval('id_seq'),
  day         DATE        NOT NULL,
  status      VARCHAR(32) NOT NULL,
  room_id     INTEGER     NOT NULL,
  user_id     INTEGER     NOT NULL,
  FOREIGN KEY (user_id) REFERENCES usr (id) ON DELETE CASCADE,
  FOREIGN KEY (room_id) REFERENCES room (number) ON DELETE CASCADE
);
