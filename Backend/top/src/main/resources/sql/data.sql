insert into users (nickname, login_id, screen_share)
values ('Timo1', 'Timo1@gmail.com', true),
       ('Timo2', 'Timo2@gmail.com', false);

insert into one_days (date_data, focus_time, target_time, user_id)
values ('2024-07-22', 100, 5, 1),
       ('2024-07-21', 200, 5, 1),
       ('2024-07-20', 300, 5, 1),
       ('2024-07-01', 400, 5, 1),
       ('2024-07-05', 500, 5, 1),
       ('2024-07-14', 1000, 5, 1),
       ('2024-07-23', 0, 5, 1);

insert into hour_focus_times (focus_time, time_unit, one_day_id)
values (1800, 1, 7),
       (2000, 2, 7);