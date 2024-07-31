insert into users (nickname, login_id, is_share, is_active)
values ('Timo1', 'Timo1@gmail.com', true, true),
       ('Timo2', 'Timo2@gmail.com', false, true),
       ('Timo3', 'Timo3@gmail.com', true, true),
       ('Timo4', 'omiT4@gmail.com', true, true),
       ('omiT5', 'omiT5@gmail.com', false, true),
       ('omiT6', 'omiT6@gmail.com', false, true);

insert into one_days (date_data, focus_time, target_time, user_id)
values ('2023-12-25', 2550, 5, 1),
       ('2024-01-23', 1230, 5, 1),
       ('2024-01-24', 1240, 5, 1),
       ('2024-01-25', 1250, 5, 1),
       ('2024-02-01', 2010, 5, 1),
       ('2024-03-25', 3250, 5, 1),
       ('2024-04-25', 4250, 5, 1),
       ('2024-05-25', 5250, 5, 1),
       ('2024-06-23', 6230, 5, 1),
       ('2024-06-24', 6240, 5, 1),
       ('2024-06-25', 6250, 5, 1),
       ('2024-07-01', 400, 5, 1),
       ('2024-07-05', 500, 5, 1),
       ('2024-07-14', 1000, 5, 1),
       ('2024-07-16', 1000, 5, 1),
       ('2024-07-17', 1000, 5, 1),
       ('2024-07-18', 1000, 5, 1),
       ('2024-07-20', 300, 5, 1),
       ('2024-07-21', 200, 5, 1),
       ('2024-07-22', 100, 5, 1),
       ('2024-07-23', 300, 5, 1),
       ('2024-07-24', 300, 5, 1),
       ('2024-07-30', 0, 5, 1),
       ('2024-07-30', 0, 5, 3),
       ('2024-07-30', 0, 5, 2),
       ('2024-07-30', 0, 5, 4),
       ('2024-07-30', 0, 5, 6),
       ('2024-07-30', 0, 5, 5)
       ;

insert into hour_focus_times (focus_time, time_unit, one_day_id)
values (1800, 1, 23),
       (2000, 2, 23),
       (2000, 2, 24),
       (1800, 4, 26),
       (21231, 4, 26),
       (52345, 6, 28)
       ;

insert into app_focus_times (app, start_time, focus_time, one_day_id)
values ('Youtube', 3800, 3600, 23),
       ('Mattermost', 3580, 3800, 23),
       ('YoutubeMusic', 3600, 5000, 23),
       ('SSAFY', 3620, 2400, 23),
       ('IntelliJ', 3640, 2000, 23);

insert into friends (user_id, friend_id, relation)
values (1, 2, 'ACCEPTED'),
       (2, 1, 'ACCEPTED'),
       (1, 3, 'ACCEPTED'),
       (3, 1, 'ACCEPTED'),
       (1, 5, 'WAITING'),
       (2, 4, 'WAITING'),
       (4, 1, 'WAITING');

insert into bans (name, user_id)
values ('youtube.com', 1),
       ('leagueoflegends', 1),
       ('maplestory', 2);
