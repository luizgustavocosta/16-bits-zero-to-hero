drop table if exists genres CASCADE;
drop table if exists movies CASCADE;
drop table if exists movies_genre CASCADE;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;

create table genres (id bigint not null, name varchar(255), primary key (id));
create table movies (id bigint not null, country varchar(255), created_at timestamp, duration integer not null, language varchar(255), name varchar(255), original_title varchar(255), classification varchar(5), title varchar(255), year integer not null, primary key (id));
create table movies_genre (movie_id bigint not null, genre_id bigint not null)