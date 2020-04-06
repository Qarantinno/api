create schema if not exists qarantinno;

set schema 'qarantinno';

create function update_modified_at() returns trigger
    language plpgsql
    as $$
begin
    new.modified_at := current_timestamp;
    RETURN new;
end;
$$;


create table if not exists coordinates (
    id serial,
    coordinate point not null,
    created_at timestamp not null default current_timestamp,
    modified_at timestamp not null default current_timestamp,
    primary key (id)
);
create trigger trigger_update_coordinates_modified_at
    before update on coordinates
    for each row
execute procedure update_modified_at();

create table if not exists places (
    id serial,
    name varchar(50) null,
    type varchar(20) not null,
    modifier varchar(20) not null,
    address varchar(255) null,
    coordinate_id int null,
    modified_at timestamp not null default current_timestamp,
    created_at timestamp not null default current_timestamp,
    primary key (id),
    foreign key (coordinate_id) references coordinates (id)
);
create unique index places_name_type_modifier_unnamed on places (name, type, modifier) where name = 'unnamed';
create index places_name_idx on places (type);
create index places_modifier_idx on places (modifier);
create trigger trigger_update_places_modified_at
    before update on places
    for each row
execute procedure update_modified_at();

create table if not exists shots (
    id serial,
    week_day varchar(3) not null,
    people int not null,
    source varchar(20) not null,
    tracking_data jsonb null,
    shot_at timestamp not null,
    place_id int not null,
    modified_at timestamp not null default current_timestamp,
    created_at timestamp not null default current_timestamp,
    primary key (id),
    foreign key (place_id) references places (id)
);
create index shots_week_day_idx on shots (week_day);
create trigger trigger_update_shots_modified_at
    before update on shots
    for each row
execute procedure update_modified_at();