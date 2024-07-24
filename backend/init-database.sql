create table "user" (
        id uuid not null,
        account_creation timestamp(6) not null,
        bio varchar(255),
        birthday date not null,
        display_name varchar(255) not null,
        email varchar(255) unique,
        first_name varchar(255),
        gender varchar(255),
        is_verified boolean not null,
        last_name varchar(255),
        location varchar(255),
        middle_name varchar(255),
        phone_number varchar(255) unique,
        username varchar(255) not null unique,
        website_url varchar(255),
        primary key (id)
    );

create table post (
        id uuid not null,
        content varchar(280) not null,
        created_at timestamp(6) not null,
        is_qoute_post boolean not null,
        author_id uuid not null,
        parent_post_id uuid,
        primary key (id),
        constraint fk_author
            foreign key(author_id) 
            references "user",
        constraint fk_parent
            foreign key(parent_post_id) 
            references post
    );

create table auth (
        id uuid not null,
        password varchar(255),
        user_id uuid not null unique,
        primary key (id),
        constraint fk_user
            foreign key(user_id) 
            references "user"
    );

create table blocked_users (
    user_id uuid not null,
    blocked_user_id uuid not null,
    primary key (user_id, blocked_user_id),
    constraint fk_blocked_user
            foreign key(blocked_user_id) 
            references "user",
    constraint fk_user
            foreign key(user_id) 
            references "user"
    );

create table bookmarks (
        user_id uuid not null,
        post_id uuid not null,
        primary key (user_id, post_id),
        constraint fk_user
            foreign key(user_id) 
            references "user",
        constraint fk_post
            foreign key(post_id) 
            references post
    );

create table followers (
        user_id uuid not null,
        follower_id uuid not null,
        primary key (user_id, follower_id),
        constraint fk_user
            foreign key(user_id) 
            references "user",
        constraint fk_follower
            foreign key(follower_id) 
            references "user"
    );

 create table following (
        user_id uuid not null,
        following_id uuid not null,
        primary key (user_id, following_id),
        constraint fk_user
            foreign key(user_id) 
            references "user",
        constraint fk_following
            foreign key(following_id) 
            references "user"
    );

create table hash_tag (
        hashtag varchar(255) not null,
        primary key (hashtag)
    );

create table hashtag_posts (
        hashtag varchar(255) not null,
        post_id uuid not null,
        primary key (hashtag, post_id),
        constraint fk_hashtag
            foreign key(hashtag) 
            references hash_tag,
        constraint fk_post
            foreign key(post_id) 
            references post
    );

create table likes (
        id uuid not null,
        created timestamp(6) not null,
        user_id uuid not null,
        post_id uuid not null,
        primary key (id),
        constraint fk_user
            foreign key(user_id) 
            references "user",
        constraint fk_post
            foreign key(post_id) 
            references post
    );

create table session (
        id uuid not null,
        expiration timestamp(6) not null,
        user_id uuid not null,
        primary key (id),
        constraint fk_user
            foreign key(user_id) 
            references "user"
    );