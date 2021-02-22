DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals(user_id, datetime, calories, description)
VALUES (100000, '2020-02-04 07:00', 2000, 'breakfast'),
       (100000, '2020-02-04 12:00', 1000, 'lunch'),
       (100000, '2020-02-04 17:00', 300, 'five-oclock'),
       (100000, '2020-02-04 20:00', 1200, 'dinner'),
       (100000, '2020-02-03 06:00', 500, 'morning coffee'),
       (100000, '2020-02-03 12:00', 800, 'light lunch'),
       (100001, '2020-02-03 17:00', 200, 'five-oclock'),
       (100001, '2020-02-03 20:00', 1700, 'dinner'),
       (100001, '2020-02-05 10:00', 2700, 'brunch'),
       (100001, '2020-02-05 15:00', 200, 'snack'),
       (100001, '2020-02-05 21:00', 1200, 'bar'),
       (100001, '2012-03-04 13:00', 2000, 'dinner'),
       (100000, '2012-01-01 13:00', 300, 'new year brunch');