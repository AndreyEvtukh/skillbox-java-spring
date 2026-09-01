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

CREATE TABLE IF NOT EXISTS room_closed_dates
(
    room_id     UUID NOT NULL,
    closed_date DATE NOT NULL,

    CONSTRAINT fk_room_closed_dates_room
        FOREIGN KEY (room_id)
            REFERENCES rooms (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_room_closed_date
        UNIQUE (room_id, closed_date)
);