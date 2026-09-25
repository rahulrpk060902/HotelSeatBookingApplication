
CREATE TABLE IF NOT EXISTS public.hotels (
	id uuid NOT NULL,
	email varchar(255) NULL,
	hotel_name varchar(255) NULL,
	"password" varchar(255) NULL,
	phone varchar(255) NULL,
	place varchar(255) NULL,
	"role" varchar(255) NULL,
	CONSTRAINT hotels_email_key UNIQUE (email),
	CONSTRAINT hotels_phone_key UNIQUE (phone),
	CONSTRAINT hotels_pkey PRIMARY KEY (id),
	CONSTRAINT hotels_role_check CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'HOTEL'::character varying])::text[])))
);