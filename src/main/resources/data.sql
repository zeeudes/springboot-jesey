INSERT INTO user (id, first_name, last_name, email, username, password, active, created_at, last_login) VALUES
  (NEXTVAL('HIBERNATE_SEQUENCE'), 'Goku', 'Son', 'goku@capsule.corp.com', 'goku@capsule.corp.com', '$2a$10$32Hw5zY7uvx0EgsfE/yQNuuL.Nrt5srepNP.vlmES2VKbYAnOBqC6',
   TRUE, '2018-12-31 00:00:00', null);
INSERT INTO user (id, first_name, last_name, email, username, password, active, created_at, last_login) VALUES
  (NEXTVAL('HIBERNATE_SEQUENCE'), 'Naruto', 'Uzumaki', 'nanadaime@konoha.com', 'nanadaime@konoha.com', '$2a$10$32Hw5zY7uvx0EgsfE/yQNuuL.Nrt5srepNP.vlmES2VKbYAnOBqC6',
  TRUE, '2018-12-31 00:00:00', null);
INSERT INTO user (id, first_name, last_name, email, username, password, active, created_at, last_login) VALUES
  (NEXTVAL('HIBERNATE_SEQUENCE'), 'Edward', 'Elric', 'fullmetal@amestris.com', 'fullmetal@amestris.com', '$2a$10$32Hw5zY7uvx0EgsfE/yQNuuL.Nrt5srepNP.vlmES2VKbYAnOBqC6',
  FALSE, '2018-12-31 00:00:00', null);

INSERT INTO user_roles (user_id, role) VALUES (1, 'ADMIN');
INSERT INTO user_roles (user_id, role) VALUES (1, 'USER');
INSERT INTO user_roles (user_id, role) VALUES (2, 'USER');
INSERT INTO user_roles (user_id, role) VALUES (3, 'USER');

INSERT INTO user_phones (user_id, area_code, country_code, number) VALUES (1, 81, '+55', 988887888);
INSERT INTO user_phones (user_id, area_code, country_code, number) VALUES (2, 81, '+55', 988887888);
INSERT INTO user_phones (user_id, area_code, country_code, number) VALUES (3, 81, '+55', 988887888);

