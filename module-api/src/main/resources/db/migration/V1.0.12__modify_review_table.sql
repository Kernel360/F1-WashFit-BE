alter table review
    alter column title type varchar(255) using title::varchar(255);

alter table review
    alter column contents type varchar(4000) using contents::varchar(4000);

alter table review
    add is_visible boolean;