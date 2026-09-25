
CREATE TABLE IF NOT EXISTS public.seat_schedule (
	booked bool NULL,
	end_time timestamp(6) NULL,
	start_time timestamp(6) NULL,
	id uuid NOT NULL,
	seat_id uuid NULL,
	CONSTRAINT seat_schedule_pkey PRIMARY KEY (id)
);
ALTER TABLE public.seat_schedule
DROP CONSTRAINT IF EXISTS fk2pvpr9po5sgbyabi8y07vqqy1;

ALTER TABLE public.seat_schedule
ADD CONSTRAINT fk2pvpr9po5sgbyabi8y07vqqy1
FOREIGN KEY (seat_id) REFERENCES public.seats(id);