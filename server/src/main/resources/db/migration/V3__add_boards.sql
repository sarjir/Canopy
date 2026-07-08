
create table if not exists boards (
    id serial primary key,
    name varchar(255) not null,
    user_id integer not null references users(id),
    created_at timestamp default current_timestamp,
    unique(user_id, name)
);

create table if not exists lists (
    id serial primary key,
    name varchar(255) not null,
    board_id integer not null references boards(id),
    created_at timestamp default current_timestamp,
    archived boolean default false,
    unique(board_id, name)
);


