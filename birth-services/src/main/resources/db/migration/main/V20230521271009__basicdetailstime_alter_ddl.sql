ALTER TABLE eg_birth_details ADD COLUMN IF NOT EXISTS  birthtime character varying(64) COLLATE pg_catalog."default";
ALTER TABLE eg_birth_details_audit ADD COLUMN IF NOT EXISTS  birthtime character varying(64) COLLATE pg_catalog."default";

ALTER TABLE eg_register_birth_details ADD COLUMN IF NOT EXISTS  birthtime character varying(64) COLLATE pg_catalog."default";
ALTER TABLE eg_register_birth_details_audit ADD COLUMN IF NOT EXISTS  birthtime character varying(64) COLLATE pg_catalog."default";

ALTER TABLE eg_birth_details ADD COLUMN IF NOT EXISTS  birthdate_string character varying(64) COLLATE pg_catalog."default";
ALTER TABLE eg_birth_details_audit ADD COLUMN IF NOT EXISTS  birthdate_string character varying(64) COLLATE pg_catalog."default";

ALTER TABLE eg_register_birth_details ADD COLUMN IF NOT EXISTS  birthdate_string character varying(64) COLLATE pg_catalog."default";
ALTER TABLE eg_register_birth_details_audit ADD COLUMN IF NOT EXISTS  birthdate_string character varying(64) COLLATE pg_catalog."default";