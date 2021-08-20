DROP TABLE IF EXISTS order_room;
DROP TABLE IF EXISTS order_invoice;
DROP TABLE IF EXISTS invoice;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS usr;
DROP TABLE IF EXISTS room;

DROP SEQUENCE IF EXISTS id_seq;
CREATE SEQUENCE id_seq START WITH 1;


CREATE TABLE usr
(
    id               BIGINT     NOT NULL DEFAULT nextval('id_seq'),
    login            VARCHAR    NOT NULL,
    name             VARCHAR    NOT NULL,
    surname          VARCHAR    NOT NULL,
    password         VARCHAR    NOT NULL,
    registered       TIMESTAMP  NOT NULL DEFAULT now(),
    active           BOOL       NOT NULL DEFAULT TRUE,
    bill             NUMERIC(12,2) NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
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
    id               BIGINT       NOT NULL UNIQUE DEFAULT nextval('id_seq'),
    number           INTEGER      NOT NULL UNIQUE,
    category         VARCHAR(32)  NOT NULL,
    guests           INT          NOT NULL,
    description      VARCHAR(255) NOT NULL,
    img_name         VARCHAR(255) NOT NULL,
    price            NUMERIC(12,2) NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX room_number ON room (number);


CREATE TABLE orders (
    id            BIGINT      NOT NULL DEFAULT nextval('id_seq'),
    arrival     DATE,
    departure   DATE,
    category    VARCHAR(64),
    guests      INTEGER NOT NULL,
    registered    TIMESTAMP   NOT NULL DEFAULT now(),
    correct_price BIGINT,
    period INTEGER NOT NULL,
    status        VARCHAR(32) NOT NULL,
    user_id       BIGINT      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES usr (id) ON DELETE CASCADE
);

CREATE TABLE invoice (
    id          BIGINT NOT NULL DEFAULT nextval('id_seq'),
    cost        BIGINT,
    registered  TIMESTAMP  NOT NULL DEFAULT now(),
    status      VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE order_invoice (
    order_id BIGINT NOT NULL,
    invoice_id BIGINT,
    PRIMARY KEY (order_id),
    FOREIGN KEY (invoice_id) REFERENCES invoice (id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

CREATE TABLE order_room (
    order_id BIGINT NOT NULL,
    room_id BIGINT,
    PRIMARY KEY (order_id),
    FOREIGN KEY (order_id) REFERENCES orders (id)  ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES room (id)  ON DELETE CASCADE
);