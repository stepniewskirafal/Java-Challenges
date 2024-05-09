--liquibase formatted sql

--changeset RStepniewski:003_1
create table if not exists email_body
(
    email_id       uuid not null
        primary key,
    message        varchar(255),
    receiver_email varchar(255),
    sender_email   varchar(255),
    subject        varchar(255)
);

--changeset RStepniewski:003_2
create table if not exists activation_token
(
    token_id         uuid not null
        primary key,
    token            uuid,
    email_address    varchar(255),
    expiration_date   timestamp
);