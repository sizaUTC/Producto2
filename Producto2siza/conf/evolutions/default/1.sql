# --- First database schema

# --- !Ups

create table platotip (
  id                        bigint not null,
  nombre                    varchar(255),
  descripcion               varchar(255),
  peso		                varchar(255),
  cantidad                  varchar(255),
  company_id                bigint,
  constraint pk_platotip primary key (id))
;

create sequence platotip_seq start with 1000;


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists platotip;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists platotip_seq;

