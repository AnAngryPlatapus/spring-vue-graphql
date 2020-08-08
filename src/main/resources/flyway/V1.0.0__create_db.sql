drop table if exists blog_user cascade;
drop table if exists comment cascade;
drop table if exists post cascade;

drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;

create table blog_user
(
    blog_user_id int8 not null,
    email        varchar(255),
    password     varchar(255),
    role         varchar(255),
    username     varchar(255),
    primary key (blog_user_id)
);
alter table blog_user
    add constraint uk_username unique (username);
alter table blog_user
    add constraint uk_email unique (email);

create table comment
(
    comment_id   int8 not null,
    content      varchar(255),
    blog_user_id int8 not null,
    post_id      int8 not null,
    primary key (comment_id)
);
alter table comment
    add constraint fk_blog_user foreign key (blog_user_id) references blog_user;
alter table comment
    add constraint fk_post foreign key (post_id) references post;


create table post
(
    post_id      int8 not null,
    content      varchar(255),
    slug         varchar(255),
    title        varchar(255),
    blog_user_id int8 not null,
    primary key (post_id)
);
alter table post
    add constraint uk_slug unique (slug);
alter table post
    add constraint fk_blog_user foreign key (blog_user_id) references blog_user;