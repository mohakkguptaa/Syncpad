-- Table: public.content

-- DROP TABLE IF EXISTS public.content;

CREATE TABLE IF NOT EXISTS public.content
(
    content_id bigint NOT NULL DEFAULT nextval('content_content_id_seq'::regclass),
    content_body text COLLATE pg_catalog."default",
    version integer DEFAULT 1,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT content_pkey PRIMARY KEY (content_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.content
    OWNER to postgres;