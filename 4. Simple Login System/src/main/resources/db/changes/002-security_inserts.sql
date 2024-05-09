--liquibase formatted sql

--changeset RStepniewski:002_1
delete from user_role_junction;
delete from users;
delete from roles;

--changeset RStepniewski:002_2
insert into roles (role_id, authority) values
    (gen_random_uuid(), 'ROLE_ADMIN'),
    (gen_random_uuid(), 'ROLE_USER');

--changeset RStepniewski:002_3
insert into users (user_id, user_name, email, password, enabled) values
    (gen_random_uuid(), 'testadmin', 'testadmin@gmail.com', '$2a$10$m5c3PzyM6aJB1YopUXhyJexmj0BpZrOnsc2lRDqVq5JBcL8SFdjCi', TRUE),
    (gen_random_uuid(), 'testuser', 'testuser@gmail.com', '$2a$10$zFyJwBg.AKCFuVfTRTaGieFPIzf37UKxcVTh9n7METtl4X.no1JHm', TRUE);

/*
testadmin - testadmin@gmail.com - 1qaz@WSX
testuser  - testuser@gmail.com - 3edc$RFV
 */

--changeset RStepniewski:002_4
insert into user_role_junction (role_id, user_id) values
    ((select role_id from roles where authority = 'ROLE_ADMIN'), (select user_id from users where email = 'testadmin@gmail.com')),
    ((select role_id from roles where authority = 'ROLE_USER'), (select user_id from users where email = 'testuser@gmail.com'));
