
CREATE TABLE IF NOT EXISTS public.seats (
	available bool NOT NULL,
	seat_number int4 NOT NULL,
	hotel_id uuid NOT NULL,
	id uuid NOT NULL,
	table_name varchar(255) NOT NULL,
	CONSTRAINT seats_pkey PRIMARY KEY (id)
);

ALTER TABLE public.seats
DROP CONSTRAINT IF EXISTS fk3o8odsc5xgoluwjk018xn3k2k;

ALTER TABLE public.seats
ADD CONSTRAINT fk3o8odsc5xgoluwjk018xn3k2k
FOREIGN KEY (hotel_id) REFERENCES public.hotels(id);