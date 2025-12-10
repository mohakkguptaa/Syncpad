-- Table: public.content_tracker

-- DROP TABLE IF EXISTS public.content_tracker;

CREATE TABLE IF NOT EXISTS public.content_tracker
(
    tracking_id bigint NOT NULL DEFAULT nextval('content_tracker_tracking_id_seq'::regclass),
    location_id bigint,
    content_id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT content_tracker_pkey PRIMARY KEY (tracking_id),
    CONSTRAINT fk_content FOREIGN KEY (content_id)
        REFERENCES public.content (content_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.content_tracker
    OWNER to postgres;
-- Index: tracking_index

-- DROP INDEX IF EXISTS public.tracking_index;

CREATE INDEX IF NOT EXISTS tracking_index
    ON public.content_tracker USING btree
    (location_id ASC NULLS LAST, content_id ASC NULLS LAST)
    TABLESPACE pg_default;