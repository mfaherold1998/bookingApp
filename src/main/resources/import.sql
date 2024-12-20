INSERT INTO public.agendas (id, work_day, start_hour, end_hour) values (1, '2024-11-18', '09:30:00', '13:30:00');
INSERT INTO public.agendas (id, work_day, start_hour, end_hour) values (2, '2024-11-19', '09:30:00', '13:30:00');
INSERT INTO public.agendas (id, work_day, start_hour, end_hour) values (3, '2024-11-20', '09:30:00', '13:30:00');
INSERT INTO public.agendas (id, work_day, start_hour, end_hour) values (4, '2024-11-21', '09:30:00', '13:30:00');
INSERT INTO public.agendas (id, work_day, start_hour, end_hour) values (5, '2024-11-22', '09:30:00', '13:30:00');

INSERT INTO public.clients (id, first_name , last_name, email) values (1,'nanda', 'artigas','nanda@gmail.com');
INSERT INTO public.clients (id, first_name , last_name, email) values (2,'fede', 'artigas','nanda@gmail.com');
INSERT INTO public.clients (id, first_name, last_name, email) values (3,'karla', 'artigas','nanda@gmail.com');
INSERT INTO public.clients (id, first_name, last_name, email) values (4,'marco', 'artigas','nanda@gmail.com');
INSERT INTO public.clients (id, first_name, last_name, email) values (5,'daniel', 'artigas','nanda@gmail.com');

INSERT INTO public.corporations (id, "name") values (1, 'corp1');
INSERT INTO public.corporations (id, "name") values (2, 'corp2');
INSERT INTO public.corporations (id, "name") values (3, 'corp3');
INSERT INTO public.corporations (id, "name") values (4, 'corp4');
INSERT INTO public.corporations (id, "name") values (5, 'corp5');

INSERT INTO public.subdivisions (id, corporation_id, "name", adress, contact_info) values (1, 1, 'sub11', 'adress1', 'sub11@gmail.com');
INSERT INTO public.subdivisions (id, corporation_id, "name", adress, contact_info) values (2, 1, 'sub12', 'adress1', 'sub12@gmail.com');
INSERT INTO public.subdivisions (id, corporation_id, "name", adress, contact_info) values (3, 1, 'sub13', 'adress1', 'sub13@gmail.com');
INSERT INTO public.subdivisions (id, corporation_id, "name", adress, contact_info) values (4, 2, 'sub21', 'adress1', 'sub21@gmail.com');
INSERT INTO public.subdivisions (id, corporation_id, "name", adress, contact_info) values (5, 2, 'sub22', 'adress1', 'sub22@gmail.com');

INSERT INTO public.employees (id, agenda_id, subdivision_id, first_name, last_name, email) values (1, 1, 1,'staff1','staff1','staff1@gamil.com');
INSERT INTO public.employees (id, agenda_id, subdivision_id, first_name, last_name, email) values (2, 2, 1,'staff2','staff2','staff2@gamil.com');
INSERT INTO public.employees (id, agenda_id, subdivision_id, first_name, last_name, email) values (3, 3, 1,'staff3','staff3','staff3@gamil.com');
INSERT INTO public.employees (id, agenda_id, subdivision_id, first_name, last_name, email) values (4, 4, 1,'staff4','staff4','staff4@gamil.com');
INSERT INTO public.employees (id, agenda_id, subdivision_id, first_name, last_name, email) values (5, 5, 1,'staff5','staff5','staff5@gamil.com');

INSERT INTO public."procedures" (id,"name", price, estimated_time_in_minutes) values (1, 'lavado', 5.00, 30);
INSERT INTO public."procedures" (id,"name", price, estimated_time_in_minutes) values (2, 'secado', 5.00, 10);
INSERT INTO public."procedures" (id,"name", price, estimated_time_in_minutes) values (3, 'planchado', 10.00, 20);
INSERT INTO public."procedures" (id,"name", price, estimated_time_in_minutes) values (4, 'color', 45.00, 45);
INSERT INTO public."procedures" (id,"name", price, estimated_time_in_minutes) values (5, 'corte', 20.00, 20);

INSERT INTO public.proprietors (id, first_name, last_name, email) values (1, 'owner1', 'owner1', 'owner1@gmail.com');
INSERT INTO public.proprietors (id, first_name, last_name, email) values (2, 'owner2', 'owner2', 'owner1@gmail.com');
INSERT INTO public.proprietors (id, first_name, last_name, email) values (3, 'owner3', 'owner3', 'owner1@gmail.com');
INSERT INTO public.proprietors (id, first_name, last_name, email) values (4, 'owner4', 'owner4', 'owner1@gmail.com');
INSERT INTO public.proprietors (id, first_name, last_name, email) values (5, 'owner5', 'owner5', 'owner1@gmail.com');

INSERT INTO public.bookings (id, employee_id, client_id, procedure_id, booking_day, start_time, end_time, status) values (1, 1, 1, 1, '2024-11-22', '09:30:00', '13:30:00', 'PENDING');
INSERT INTO public.bookings (id, employee_id, client_id, procedure_id, booking_day, start_time, end_time, status) values (2, 2, 2, 1, '2024-11-22', '09:30:00', '13:30:00', 'PENDING');
INSERT INTO public.bookings (id, employee_id, client_id, procedure_id, booking_day, start_time, end_time, status) values (3, 3, 3, 1, '2024-11-22', '09:30:00', '13:30:00', 'PENDING');
INSERT INTO public.bookings (id, employee_id, client_id, procedure_id, booking_day, start_time, end_time, status) values (4, 4, 4, 1, '2024-11-22', '09:30:00', '13:30:00', 'PENDING');
INSERT INTO public.bookings (id, employee_id, client_id, procedure_id, booking_day, start_time, end_time, status) values (5, 5, 5, 1, '2024-11-22', '09:30:00', '13:30:00', 'PENDING');

--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (1,1);
--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (1,2);
--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (1,3);
--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (1,4);
--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (1,5);
--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (2,1);
--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (2,2);
--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (2,3);
--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (2,4);
--INSERT INTO public.booking_procedure (booking_id, procedure_id) values (2,5);

INSERT INTO public.corporation_proprietor (corporation_id, proprietor_id) values (1,1);
INSERT INTO public.corporation_proprietor (corporation_id, proprietor_id) values (1,2);
INSERT INTO public.corporation_proprietor (corporation_id, proprietor_id) values (1,3);
INSERT INTO public.corporation_proprietor (corporation_id, proprietor_id) values (2,4);
INSERT INTO public.corporation_proprietor (corporation_id, proprietor_id) values (2,5);

INSERT INTO public.employee_procedure (employee_id , procedure_id) values (1,1);
INSERT INTO public.employee_procedure (employee_id , procedure_id) values (1,2);
INSERT INTO public.employee_procedure (employee_id , procedure_id) values (1,3);
INSERT INTO public.employee_procedure (employee_id , procedure_id) values (1,4);
INSERT INTO public.employee_procedure (employee_id , procedure_id) values (1,5);
INSERT INTO public.employee_procedure (employee_id , procedure_id) values (2,1);
INSERT INTO public.employee_procedure (employee_id , procedure_id) values (2,2);
INSERT INTO public.employee_procedure (employee_id , procedure_id) values (2,3);
INSERT INTO public.employee_procedure (employee_id , procedure_id) values (2,4);
INSERT INTO public.employee_procedure (employee_id , procedure_id) values (2,5);

INSERT INTO public.subdivision_procedure (subdivision_id , procedure_id) values (1,1);
INSERT INTO public.subdivision_procedure (subdivision_id , procedure_id) values (1,2);
INSERT INTO public.subdivision_procedure (subdivision_id , procedure_id) values (1,3);
INSERT INTO public.subdivision_procedure (subdivision_id , procedure_id) values (2,4);
INSERT INTO public.subdivision_procedure (subdivision_id , procedure_id) values (2,5);

ALTER SEQUENCE public.agendas_id_seq INCREMENT BY 1 START 5 RESTART 5;
ALTER SEQUENCE public.bookings_id_seq INCREMENT BY 1 START 5	RESTART 5;
ALTER SEQUENCE public.clients_id_seq INCREMENT BY 1 START 5 RESTART 5;
ALTER SEQUENCE public.corporations_id_seq INCREMENT BY 1 START 5 RESTART 5;
ALTER SEQUENCE public.employees_id_seq INCREMENT BY 1 START 5 RESTART 5;
ALTER SEQUENCE public.procedures_id_seq INCREMENT BY 1 START 5	RESTART 5;
ALTER SEQUENCE public.proprietors_id_seq INCREMENT BY 1 START 5 RESTART 5;
ALTER SEQUENCE public.subdivisions_id_seq INCREMENT BY 1 START 5 RESTART 5;