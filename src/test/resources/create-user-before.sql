delete
from user_role;
delete
from usr;

insert into usr(id, active, password, username)
values (1, true, '$2a$08$blqlc8PunaICx528p4i5je5935Sa9kdS9x.jfTZz3fINWu/sCeSxO', 'admin'),
       (4, true, '$2a$08$xBLUCk4/IYNZHv6YyvDSZeFxKRoF5fMAKMWeqSKHGIlkUN.cW0Mhu', 'a');

insert into user_role(user_id, roles)
VALUES (1, 'USER'),
       (1, 'ADMIN'),
       (4, 'USER');