insert into blog_user (blog_user_id, email, password, role, username)
values (nextval('hibernate_sequence'),
        'smiller797@gmail.com',
        '$2a$10$.NEeupeV5W9HjUE27Xu6jenzUxmDnCvDhMxPrIo2n2ea1PiXhPtRi',
        'ADMIN',
        'smiller');