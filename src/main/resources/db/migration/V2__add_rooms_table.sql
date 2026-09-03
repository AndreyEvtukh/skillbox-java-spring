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