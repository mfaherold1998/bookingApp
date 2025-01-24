
INSERT INTO public.roles (id, "name") values (1, 'SUPERADMIN');
INSERT INTO public.roles (id, "name") values (2, 'PROPRIETOR');
INSERT INTO public.roles (id, "name") values (3, 'CLIENT');
INSERT INTO public.roles (id, "name") values (4, 'EMPLOYEE');

INSERT INTO public.proprietors (id, first_name, last_name) values (1, 'Francesca', 'Poliza');

INSERT INTO public.corporations (id, "name") values (1, 'Hair Color');

INSERT INTO public.subdivisions (id, corporation_id, "name", adress, contact_info) values (1, 1, 'Hair Color Paola', 'Via San Francesco', 'mfaherold1998@gmail.com');

INSERT INTO public.corporation_proprietor (corporation_id, proprietor_id) values (1,1);

ALTER SEQUENCE public.roles_id_seq INCREMENT BY 1 START 5 RESTART 5;
ALTER SEQUENCE public.corporations_id_seq INCREMENT BY 1 START 2 RESTART 2;
ALTER SEQUENCE public.subdivisions_id_seq INCREMENT BY 1 START 2 RESTART 2;
