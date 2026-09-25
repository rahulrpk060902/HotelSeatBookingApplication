
CREATE TABLE IF NOT EXISTS public.users (
	id uuid NOT NULL,
	email varchar(255) NULL,
	"name" varchar(255) NULL,
	"password" varchar(255) NULL,
	phone varchar(255) NULL,
	place varchar(255) NULL,
	"role" varchar(255) NULL,
	CONSTRAINT users_email_key UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'HOTEL'::character varying])::text[])))
);