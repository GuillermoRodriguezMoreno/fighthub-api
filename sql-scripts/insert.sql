INSERT INTO "_user" (id, username, email, password, is_account_locked, is_account_enabled, created_at)
VALUES (1, 'johndoe', 'johndoe@example.com', 'hashedpassword1', false, true, NOW()),
       (2, 'mikesmith', 'mikesmith@example.com', 'hashedpassword2', false, true, NOW()),
       (3, 'carlossanchez', 'carlossanchez@example.com', 'hashedpassword3', false, true, NOW()),
       (4, 'luisgomez', 'luisgomez@example.com', 'hashedpassword4', false, true, NOW()),
       (5, 'emilybrown', 'emilybrown@example.com', 'hashedpassword5', false, true, NOW()),
       (6, 'peterwhite', 'peterwhite@example.com', 'hashedpassword6', false, true, NOW());

INSERT INTO category (id, name, created_at, created_by)
VALUES (1, 'Amateur', NOW(), 'admin'),
       (2, 'Professional', NOW(), 'admin'),
       (3, 'Semi-Pro', NOW(), 'admin');


INSERT INTO style (id, name, created_at, created_by)
VALUES (1, 'Boxeo', NOW(), 'admin'),
       (2, 'Kickboxing', NOW(), 'admin'),
       (3, 'BJJ', NOW(), 'admin'),
       (4, 'MMA', NOW(), 'admin');

INSERT INTO fighter_profile (user_id, created_at, created_by, firstname, lastname, date_of_birth, weight, height, gender,
                             biography, wins, losses, draws, kos, wins_in_a_row, category_id, club_id, latitude, longitude, timestamp)
VALUES (1, NOW(), 'admin', 'John', 'Doe', '1990-05-15', 70.5, 175, 'M',
        'Peleador profesional con gran experiencia en boxeo.', 10, 2, 1, 5, 3, 1, NULL, 36.8634, -4.1878, NOW()),
       (2, NOW(), 'admin', 'Mike', 'Smith', '1988-08-20', 85.0, 180, 'M',
        'Especialista en kickboxing y striking rápido.', 15, 3, 2, 8, 5, 2, NULL, 36.7191, -4.4213, NOW()),
       (3, NOW(), 'admin', 'Carlos', 'Sanchez', '1995-02-10', 78.5, 178, 'M',
        'Luchador con gran dominio del Jiu-Jitsu.', 12, 4, 1, 6, 4, 3, NULL, 36.8754, -4.6920, NOW()),
       (4, NOW(), 'admin', 'Luis', 'Gomez', '1992-07-08', 72.0, 176, 'M',
        'Peleador versátil con base en MMA.', 9, 2, 0, 4, 2, 2, NULL, 36.5475, -4.2558, NOW()),
       (5, NOW(), 'admin', 'Emily', 'Brown', '1998-12-22', 60.5, 165, 'F',
        'Experta en striking y grappling.', 6, 1, 0, 3, 3, 1, NULL, 36.4847, -4.6986, NOW()),
       (6, NOW(), 'admin', 'Peter', 'White', '1993-04-30', 80.0, 182, 'M',
        'Dominio total en Jiu-Jitsu y wrestling.', 14, 3, 2, 7, 5, 2, NULL, 36.6772, -4.6726, NOW());

INSERT INTO club (id, name, address, email, description, phone, owner_id, created_at, created_by)
VALUES (1, 'Fight Club NYC', '123 Main St, New York', 'info@fightclubnyc.com', 'Gimnasio de peleas de alto nivel',
        '555-1234', 1, NOW(), 'admin'),
       (2, 'Warriors MMA', '456 Elm St, Los Angeles', 'contact@warriorsmma.com',
        'Academia de MMA para todos los niveles', '555-5678', 2, NOW(), 'admin'),
       (3, 'Gracie Jiu-Jitsu', '789 Oak St, Miami', 'gracie@bjj.com', 'Especialistas en Jiu-Jitsu Brasileño',
        '555-8765', 3, NOW(), 'admin'),
       (4, 'Iron Warriors', '102 Maple St, Houston', 'ironwarriors@example.com',
        'Entrenamiento de combate mixto avanzado', '555-9123', 4, NOW(), 'admin'),
       (5, 'Elite Boxing', '89 Pine St, Chicago', 'eliteboxing@example.com', 'Boxeo para profesionales y amateurs',
        '555-8234', 5, NOW(), 'admin'),
       (6, 'Grappling Masters', '77 Willow St, San Diego', 'grapplingmasters@example.com',
        'Escuela de grappling y Jiu-Jitsu', '555-7654', 6, NOW(), 'admin');



UPDATE fighter_profile SET club_id = 1 WHERE user_id = 1;
UPDATE fighter_profile SET club_id = 2 WHERE user_id = 2;
UPDATE fighter_profile SET club_id = 3 WHERE user_id = 3;
UPDATE fighter_profile SET club_id = 4 WHERE user_id = 4;
UPDATE fighter_profile SET club_id = 1 WHERE user_id = 5;
UPDATE fighter_profile SET club_id = 1 WHERE user_id = 6;


INSERT INTO fighter_profile_styles (fighters_id, styles_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 4),
       (3, 3),
       (4, 4),
       (5, 1),
       (5, 3),
       (6, 3),
       (6, 4);

INSERT INTO event (id, name, description, address, start_date, end_date, likes, organizer_id, created_at, created_by)
VALUES (1, 'UFC Fight Night', 'Evento de MMA con los mejores luchadores.', 'MGM Grand, Las Vegas', '2025-04-10',
        '2025-04-10', 100, 1, NOW(), 'admin'),
       (2, 'Grappling Challenge', 'Torneo de Jiu-Jitsu de alto nivel.', 'Gracie Academy, Miami', '2025-05-15',
        '2025-05-15', 50, 3, NOW(), 'admin'),
       (3, 'MMA Grand Prix', 'Torneo de MMA con los mejores peleadores del país.', 'Staples Center, Los Angeles',
        '2025-06-20', '2025-06-20', 75, 4, NOW(), 'admin'),
       (4, 'Elite Boxing Night', 'Combates de boxeo de alto nivel.', 'Madison Square Garden, New York', '2025-07-15',
        '2025-07-15', 90, 5, NOW(), 'admin'),
       (5, 'Grappling Open', 'Competencia de Jiu-Jitsu y lucha libre.', 'San Diego Arena, San Diego', '2025-08-10',
        '2025-08-10', 60, 6, NOW(), 'admin');

INSERT INTO fight (id, event_id, blue_corner_fighter_id, red_corner_fighter_id, style_id, category_id,
                   fight_order, is_title_fight, is_closed, is_ko, is_draw, weight, rounds, minutes_per_round, likes,
                   winner_id, created_at, created_by)
VALUES (1, 1, 1, NULL, 2, 2, NULL, false, false, false, false, 70.5, 3, 5, NULL, NULL, NOW(), 'admin'),
       (2, 2, 3, 1, 3, 1, 4, false, true, false, false, 78.5, 3, 3, NULL, NULL, NOW(), 'admin'),
       (3, 3, 4, 6, 4, 2, 1, true, true, false, false, 72.0, 5, 3, 120, 4, NOW(), 'admin'),
       (4, 4, 5, 1, 1, 1, 2, false, true, true, false, 60.5, 4, 3, 95, 5, NOW(), 'admin'),
       (5, 5, 6, 3, 3, 1, 1, false, true, false, true, 80.0, 3, 5, 70, NULL, NOW(), 'admin'),
       (6, 3, 4, 5, 4, 2, 3, false, true, false, false, 72.0, 3, 3, 50, 4, NOW(), 'admin'),
       (7, 4, 6, 2, 1, 1, 4, true, true, false, false, 80.0, 5, 3, 80, 6, NOW(), 'admin');

INSERT INTO role (id, name, created_at)
VALUES (2, 'ROLE_ADMIN', NOW());

INSERT INTO _user_roles (users_id, roles_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 1),
       (5, 1),
       (6, 1);


SELECT setval(pg_get_serial_sequence('_user', 'id'), (SELECT MAX(id) FROM "_user"));
SELECT setval(pg_get_serial_sequence('category', 'id'), (SELECT MAX(id) FROM "category"));
SELECT setval(pg_get_serial_sequence('style', 'id'), (SELECT MAX(id) FROM "style"));
SELECT setval(pg_get_serial_sequence('fighter_profile', 'user_id'), (SELECT MAX(user_id) FROM "fighter_profile"));
SELECT setval(pg_get_serial_sequence('club', 'id'), (SELECT MAX(id) FROM "club"));
SELECT setval(pg_get_serial_sequence('event', 'id'), (SELECT MAX(id) FROM "event"));
SELECT setval(pg_get_serial_sequence('fight', 'id'), (SELECT MAX(id) FROM "fight"));
SELECT setval(pg_get_serial_sequence('role', 'id'), (SELECT MAX(id) FROM "role"));