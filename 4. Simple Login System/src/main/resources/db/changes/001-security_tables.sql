--liquibase formatted sql

--changeset RStepniewski:001_1
create table if not exists users (
   user_id uuid not null
        primary key,
   user_name varchar(30),
   email varchar(40),
   password varchar(255),
   enabled boolean
);

--changeset RStepniewski:001_2
create table if not exists roles (
    role_id   uuid not null
        primary key,
    authority varchar(20)
);

--changeset RStepniewski:001_3
create table if not exists user_role_junction (
    role_id uuid not null
        constraint fkhybpcwvq8snjhbxawo25hxous
            references roles,
    user_id uuid not null
        constraint fk5aqfsa7i8mxrr51gtbpcvp0v1
            references users,
    primary key (role_id, user_id)
);
