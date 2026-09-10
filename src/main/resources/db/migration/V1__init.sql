CREATE TABLE IF NOT EXISTS hotels
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(64)   NOT NULL,
    title         VARCHAR(200)  NOT NULL,
    city          VARCHAR(100)  NOT NULL,
    address       VARCHAR(200)  NOT NULL,
    distance      NUMERIC(2, 1) NOT NULL,
    rating        NUMERIC(2, 1) NOT NULL,
    num_of_rating INTEGER       NOT NULL DEFAULT 0,

    CONSTRAINT chk_hotel_rating
        CHECK (rating >= 0 AND rating <= 5),

    CONSTRAINT chk_hotel_distance
        CHECK (distance >= 0),

    CONSTRAINT chk_hotel_num_of_rating
        CHECK (num_of_rating >= 0)
);

INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('46724b08-5b92-455e-b184-77678c3c60b6', 'Grand Hotel', 'Comfortable hotel in the city center', 'Paris', '10 Rue de Rivoli', 2.6, 0.0, 0);
INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('fbdbb82f-8e14-4309-b4f3-0f83451c300f', 'Grand Hotel', 'Comfortable hotel in the city center', 'Berlin', '10 Rue de Rivoli', 2.6, 0.0, 0);
INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('bc10b30c-6130-4c35-9e57-c532219f20ea', 'Grand Hotel', 'Comfortable hotel in the city center', 'Vena', '10 Rue de Rivoli', 5.6, 0.0, 0);
INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('385c28b8-316f-4415-b330-003dbc74f1a6', 'Grand Hotel', 'Comfortable hotel in the city center', 'Rome', '10 Rue de Rivoli', 3.0, 0.0, 0);
INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('39fc8c3a-c12b-4a02-9ded-9f925a7efa93', 'Grand Hotel', 'Comfortable hotel in the city center', 'Warsaw', '10 Rue de Rivoli', 1.8, 0.0, 0);
INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('2222f2bc-7bfc-4847-9fab-d3bb3d4d6d0e', 'Grand Hotel', 'Comfortable hotel in the city center', 'London', '10 Rue de Rivoli', 5.1, 0.0, 0);
INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('5b1c3328-8340-401e-b98e-2fe8109495fa', 'Grand Hotel', 'Comfortable hotel in the city center', 'Prague', '10 Rue de Rivoli', 3.3, 0.0, 0);
INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('8f1addc7-4bfe-4157-8a9b-eb78524fa052', 'Grand Hotel', 'Comfortable hotel in the city center', 'Madrid', '10 Rue de Rivoli', 2.8, 0.0, 0);
INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('51b58f28-0ffe-4228-92df-b5ae11876424', 'Grand Hotel', 'Comfortable hotel in the city center', 'Kiev', '10 Rue de Rivoli', 1.9, 2.8, 5);
INSERT INTO public.hotels (id, name, title, city, address, distance, rating, num_of_rating) VALUES ('c6d8c1ca-ab2e-46f4-b31b-31220d429470', 'Grand Hotel', 'Comfortable hotel in the city center', 'Minsk', '10 Rue de Rivoli', 7.1, 0.0, 0);
