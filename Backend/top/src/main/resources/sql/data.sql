insert into users (nickname, email, is_share, is_active)
values ('Timo1', 'Timo1@gmail.com', true, true),
       ('Timo2', 'Timo2@gmail.com', false, true),
       ('Timo3', 'Timo3@gmail.com', true, true),
       ('Timo4', 'omiT4@gmail.com', true, true),
       ('omiT5', 'omiT5@gmail.com', false, true),
       ('omiT6', 'omiT6@gmail.com', false, true);

insert into one_days (date_data, target_time, user_id)
values ('2023-12-25', 2550, 1),
       ('2024-01-23', 1230, 1),
       ('2024-01-24', 1240, 1),
       ('2024-01-25', 1250, 1),
       ('2024-02-01', 2010, 1),
       ('2024-03-25', 3250, 1),
       ('2024-04-25', 4250, 1),
       ('2024-05-25', 5250, 1),
       ('2024-06-23', 6230, 1),
       ('2024-06-24', 6240, 1),
       ('2024-06-30', 6250, 1),
       ('2024-07-01', 400, 1),
       ('2024-07-05', 500, 1),
       ('2024-07-14', 1000, 1),
       ('2024-07-16', 1000, 1),
       ('2024-07-17', 1000, 1),
       ('2024-07-18', 1000, 1),
       ('2024-07-20', 300, 1),
       ('2024-07-21', 200, 1),
       ('2024-07-28', 100, 1),
       ('2024-07-29', 300, 1),
       ('2024-08-06', 400, 1),
       ('2024-08-13', 0, 1),
       ('2024-08-04', 1000000, 2),
       ('2024-08-04', 0, 3),
       ('2024-08-01', 4500, 4),
       ('2024-08-02', 500, 5),
       ('2024-08-03', 0, 6),
       ('2024-08-08', 0, 6),
       ('2024-08-13', 0, 6);

insert into app_focus_times (app, start_time, focus_time, time_unit, one_day_id)
values ('Youtube', 3800, 3600, 1, 23),
       ('Mattermost', 3580, 3800, 1, 23),
       ('YoutubeMusic', 3600, 5000, 2, 23),
       ('SSAFY', 3620, 2400, 2, 23),
       ('IntelliJ', 3640, 2000, 5, 23),
       ('YoutubeMusic', 3600, 5000, 4, 21),
       ('YoutubeMusic', 3600, 5000, 2, 22),
       ('Mattermost', 3600, 5000, 6, 21),
       ('Mattermost', 3600, 5000, 7, 22),
       ('leagueoflegends', 3640, 1234, 17, 23),
       ('maplestory', 3640, 2351, 18, 23),
       ('battleground', 3640, 752, 19, 23)
       ;

insert into friends (user_id, friend_id, relation)
values (1, 2, 'ACCEPTED'),
       (2, 1, 'ACCEPTED'),
       (1, 3, 'ACCEPTED'),
       (3, 1, 'ACCEPTED'),
       (1, 5, 'WAITING'),
       (2, 4, 'WAITING'),
       (4, 1, 'WAITING');

insert into bans (name, is_ban, user_id)
values ('youtube.com', true, 1),
       ('leagueoflegends', true, 1),
       ('SSAFY', false, 1),
       ('YoutubeMusic', true, 1),
       ('Mattermost', true, 1),
       ('maplestory', true, 2);
