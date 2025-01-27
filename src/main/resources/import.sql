INSERT INTO public.roles (id, "name") values (1, 'SUPERADMIN');
INSERT INTO public.roles (id, "name") values (2, 'PROPRIETOR');
INSERT INTO public.roles (id, "name") values (3, 'CLIENT');
INSERT INTO public.roles (id, "name") values (4, 'EMPLOYEE');

INSERT INTO public.corporations (id, "name") values (1, 'corp1');
INSERT INTO public.corporations (id, "name") values (2, 'corp2');

INSERT INTO public.subdivisions (id, corporation_id, "name", adress, contact_info) values (1, 1, 'sub11', 'adress1', 'sub11@gmail.com');
INSERT INTO public.subdivisions (id, corporation_id, "name", adress, contact_info) values (2, 2, 'sub12', 'adress1', 'sub12@gmail.com');

INSERT INTO public.proprietors (id, first_name, last_name) values (1, 'owner1', 'owner1');
INSERT INTO public.proprietors (id, first_name, last_name) values (2, 'owner2', 'owner2');

INSERT INTO public.corporation_proprietor (corporation_id, proprietor_id) values (1,1);
INSERT INTO public.corporation_proprietor (corporation_id, proprietor_id) values (2,2);

ALTER SEQUENCE public.corporations_id_seq INCREMENT BY 1 START 3 RESTART 3;
ALTER SEQUENCE public.proprietors_id_seq INCREMENT BY 1 START 3 RESTART 3;
ALTER SEQUENCE public.subdivisions_id_seq INCREMENT BY 1 START 3 RESTART 3;
ALTER SEQUENCE public.roles_id_seq INCREMENT BY 1 START 5 RESTART 5;