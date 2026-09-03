CREATE TABLE IF NOT EXISTS booking
(
    id        UUID PRIMARY KEY,
    check_in  DATE NOT NULL,
    check_out DATE NOT NULL,
    room_id   UUID NOT NULL,
    user_id   UUID NOT NULL,

    CONSTRAINT fk_booking_room
        FOREIGN KEY (room_id)
            REFERENCES rooms (id),

    CONSTRAINT fk_booking_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),

    CONSTRAINT chk_booking_dates
        CHECK (check_out > check_in)
);

CREATE INDEX idx_booking_room_id
    ON booking (room_id);

CREATE INDEX idx_booking_user_id
    ON booking (user_id);