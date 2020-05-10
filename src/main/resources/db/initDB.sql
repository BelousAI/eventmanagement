DROP TABLE IF EXISTS user_roles;
DROP TYPE IF EXISTS status CASCADE;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                 NOT NULL,
    email            VARCHAR                 NOT NULL,
    password         VARCHAR                 NOT NULL,
    registered       TIMESTAMP DEFAULT now() NOT NULL,
    enabled          BOOL DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE TYPE status AS ENUM ('PLANNED', 'IN_PROGRESS', 'FINISHED', 'CANCELED');
CREATE TABLE events (
                       id                INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
                       user_id           INTEGER   NOT NULL,
                       start_date_time   TIMESTAMP NOT NULL,
                       description       TEXT      NOT NULL,
                       current_status    status,
                       FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX events_unique_user_datetime_idx
    ON events (user_id, start_date_time);