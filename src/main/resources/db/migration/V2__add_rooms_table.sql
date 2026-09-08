CREATE TABLE IF NOT EXISTS rooms
(
    id           UUID PRIMARY KEY,
    name         VARCHAR(250)   NOT NULL,
    number       INTEGER        NOT NULL,
    price        NUMERIC(10, 2) NOT NULL,
    max_capacity INTEGER        NOT NULL,
    description  VARCHAR(250)   NOT NULL,
    hotel_id     UUID           NOT NULL,

    CONSTRAINT fk_room_hotel
        FOREIGN KEY (hotel_id)
            REFERENCES hotels (id)
            ON DELETE CASCADE
);

INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('91066216-5633-4c76-ab67-a7d35a5c2d0b', 'Lux', 8, 50.00, 2, 'Comfortable room with a double bed', '39fc8c3a-c12b-4a02-9ded-9f925a7efa93');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('5cf95c13-a8ad-4276-a35a-ae9cbade14be', 'Lux', 6, 300.00, 3, 'Comfortable room with a double bed', '39fc8c3a-c12b-4a02-9ded-9f925a7efa93');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('2a8a23bf-f7cf-4b19-9f2e-d19fe2efb41b', 'Studio', 1, 500.00, 1, 'Comfortable room with a double bed', '39fc8c3a-c12b-4a02-9ded-9f925a7efa93');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('2a8a23bf-f7cf-4b19-9f2e-d19fe2efb42b', 'Standard', 15, 450.00, 1, 'Comfortable room with a double bed', '39fc8c3a-c12b-4a02-9ded-9f925a7efa93');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('1897ee57-86fd-4cf9-be4a-0fba205b7cd9', 'Studio', 8, 650.00, 4, 'Comfortable room with a double bed', '2222f2bc-7bfc-4847-9fab-d3bb3d4d6d0e');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('1bf2b624-4872-4266-952c-771753a9a5a8', 'Lux', 4, 1280.00, 4, 'Comfortable room with a double bed', '2222f2bc-7bfc-4847-9fab-d3bb3d4d6d0e');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('7ad48b9d-a8fe-47af-986a-ddb8aca63453', 'Lux', 12, 100.00, 2, 'Comfortable room with a double bed', '46724b08-5b92-455e-b184-77678c3c60b6');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('74ecd4f6-7a8b-4e0b-92be-fbc516f5b6fa', 'Standard', 13, 80.00, 4, 'Comfortable room with a double bed', '2222f2bc-7bfc-4847-9fab-d3bb3d4d6d0e');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('d0ff846f-8ba1-4f56-8fcf-b1e4c4b92bfd', 'Studio', 11, 800.00, 2, 'Comfortable room with a double bed', '46724b08-5b92-455e-b184-77678c3c60b6');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('45f67ab5-bf3f-458a-8b47-d2510fada540', 'Lux', 17, 300.00, 4, 'Comfortable room with a double bed', 'bc10b30c-6130-4c35-9e57-c532219f20ea');
INSERT INTO public.rooms (id, name, number, price, max_capacity, description, hotel_id) VALUES ('45f67ab5-bf3f-458a-8b47-d2510fada541', 'Lux', 18, 320.00, 6, 'Comfortable room with a double bed', 'bc10b30c-6130-4c35-9e57-c532219f20ea');