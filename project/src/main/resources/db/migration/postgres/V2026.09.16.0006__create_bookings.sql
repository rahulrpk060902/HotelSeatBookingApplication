
CREATE TABLE IF NOT EXISTS public.bookings (
	booked_at timestamp(6) NULL,
	end_time timestamp(6) NULL,
	start_time timestamp(6) NULL,
	id uuid NOT NULL,
	seat_schedule_id uuid NULL,
	user_id uuid NOT NULL,
	CONSTRAINT bookings_pkey PRIMARY KEY (id)
);
ALTER TABLE public.bookings
DROP CONSTRAINT IF EXISTS
fkeyog2oic85xg7hsu2je2lx3s6;

ALTER TABLE public.bookings ADD CONSTRAINT
fkeyog2oic85xg7hsu2je2lx3s6
FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE public.bookings
DROP CONSTRAINT IF EXISTS fkfsiviftp4kfurkw62dra33y47;

ALTER TABLE public.bookings
ADD CONSTRAINT fkfsiviftp4kfurkw62dra33y47
FOREIGN KEY (seat_schedule_id) REFERENCES public.seat_schedule(id);