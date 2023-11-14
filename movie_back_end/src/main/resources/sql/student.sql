create table student
(
    student_id int auto_increment,
    no         varchar(255) not null,
    name       varchar(255) null,
    primary key (student_id, no)
);

INSERT INTO spring_db.student (student_id, no, name) VALUES (1, '20201619', 'wyf2');
