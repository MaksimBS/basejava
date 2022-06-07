CREATE TABLE resume (
                        uuid      CHAR(36) PRIMARY KEY NOT NULL,
                        full_name TEXT                 NOT NULL
);

CREATE TABLE contact (
                         id          SERIAL,
                         resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
                         type        TEXT     NOT NULL,
                         value       TEXT     NOT NULL
);


CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);

create table section
(
    id          serial not null
        constraint section_pk
            primary key,
    type        text   not null,
    value       text   not null,
    resume_uuid char(36)
        constraint section_resume_uuid_fk
            references resume
            on delete cascade
);

alter table section
    owner to postgres;

create unique index section_id_uindex
    on section (id);

