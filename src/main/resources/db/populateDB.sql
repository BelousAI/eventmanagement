DELETE FROM user_roles;
DELETE FROM events;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@mail.ru', 'user'),
('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
('USER', 100000),
('ADMIN', 100001);

INSERT INTO events (start_date_time, description, current_status, user_id)
VALUES ('2020-04-17 18:00:00', 'Начало отпуска', 'CANCELED', 100000),
       ('2020-04-20 13:30:00', 'Отгрузка товара', 'FINISHED', 100000),
       ('2020-04-23 09:30:00', 'Позвонить заведующему клиники', 'FINISHED', 100000),
       ('2020-04-27 15:00:00', 'Презентация нового продукта', 'IN_PROGRESS', 100000),
       ('2020-04-28 09:00:00', 'Позвонить заведующей аптеки', 'PLANNED', 100000),
       ('2020-04-29 11:00:00', 'Встреча с доктором Петровой В.В.', 'PLANNED', 100000),
       ('2020-04-29 14:00:00', 'Подписание договора', 'CANCELED', 100000),
       ('2020-04-18 14:00:00', 'Админ: подготовить отчет', 'PLANNED', 100001),
       ('2020-04-27 09:00:00', 'Админ: предоставить доступ', 'IN_PROGRESS', 100001);