CREATE TABLE IF NOT EXISTS merchant
(
    id SERIAL,
    birthdate timestamp without time zone,
    create_date timestamp without time zone,
    lastname character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT merchant_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product
(
    id SERIAL,
    create_date timestamp without time zone,
    height double precision,
    label character varying(255) COLLATE pg_catalog."default",
    unit_price double precision,
    weight double precision,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS address
(
    id SERIAL,
    "number" integer NOT NULL,
    street character varying(255) COLLATE pg_catalog."default",
    zipcode character varying(255) COLLATE pg_catalog."default",
    merchant_id integer,
    CONSTRAINT address_pkey PRIMARY KEY (id),
    CONSTRAINT address_merchant_fkey FOREIGN KEY (merchant_id)
        REFERENCES merchant (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS merchant_product
(
    associated_date timestamp without time zone,
    merchant_id integer NOT NULL,
    product_id integer NOT NULL,
    CONSTRAINT merchant_product_pkey PRIMARY KEY (merchant_id, product_id),
    CONSTRAINT merchant_product_product_fkey FOREIGN KEY (product_id)
        REFERENCES product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT merchant_product_merchant_fkey FOREIGN KEY (merchant_id)
        REFERENCES merchant (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
