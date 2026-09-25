CREATE SCHEMA IF NOT EXISTS public;

DROP FUNCTION IF EXISTS uuid_generate_v1() CASCADE;

CREATE OR REPLACE FUNCTION uuid_generate_v1(
	)
    RETURNS uuid
    LANGUAGE 'c'
    COST 1
    VOLATILE STRICT PARALLEL SAFE
AS 'uuid-ossp', 'uuid_generate_v1'
;