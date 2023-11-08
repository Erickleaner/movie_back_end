create table user
(
    user_id   int auto_increment
        primary key,
    user_name varchar(255) null,
    pass_word varchar(255) null,
    role      varchar(255) null
);

INSERT INTO spring_db.user (user_id, user_name, pass_word, role) VALUES (1, 'admin', '111111', 'admin');
INSERT INTO spring_db.user (user_id, user_name, pass_word, role) VALUES (2, 'user', '111111', 'user');
