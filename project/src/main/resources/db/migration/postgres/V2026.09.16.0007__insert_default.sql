INSERT INTO public.hotels
(id, email, hotel_name, "password", phone, place, "role")
VALUES('e0becebe-f5c7-4960-a640-b29d1571e8db'::uuid, 'rahulrpk02@gmail.com', 'Rahuls Hotel', '$2a$10$25ewWcvsQDDPuf2ub1TpO.OsZ104TrBH2UyUcxF8lDjdgD8QY6weG', '9400622054', 'Mathara', 'HOTEL')
ON CONFLICT(id) DO NOTHING;

INSERT INTO public.users
(id, email, "name", "password", phone, place, "role")
VALUES('c9b86b67-31cc-4c90-a02c-358861147d9b'::uuid, 'rahulrameshpk0609@gmail.com', 'Rahul PK', '$2a$10$OoePJRLgiTIOr/cnzvwaQ.pjreogw.hLxFkj7OAWwiNyKoJQh13CG', '9400622054', 'Mathara', 'USER')
ON CONFLICT(id) DO NOTHING;
