insert into usr (id, username, password, active)
values (1, 'admin', 'a', true);

insert into user_role (user_id, roles)
VALUES (1, 'USER'), (1, 'ADMIN');