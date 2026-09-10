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

INSERT INTO public.booking (id, check_in, check_out, room_id, user_id)
VALUES ('91066216-5633-4c76-ab68-a7d35a5c2d01', '2026-01-01', '2026-01-10', '74ecd4f6-7a8b-4e0b-92be-fbc516f5b6fa',
        'c083ce11-b83f-44cf-9a15-ab1df7f39814');
INSERT INTO public.booking (id, check_in, check_out, room_id, user_id)
VALUES ('01066216-5633-4c76-ab68-a7d35a5c2d02', '2026-01-20', '2026-01-30', '74ecd4f6-7a8b-4e0b-92be-fbc516f5b6fa',
        'c083ce11-b83f-44cf-9a15-ab1df7f39814');
INSERT INTO public.booking (id, check_in, check_out, room_id, user_id)
VALUES ('01066216-5633-4c79-ab69-a7d35a5c2d02', '2026-02-01', '2026-02-13', '91066216-5633-4c76-ab67-a7d35a5c2d0b',
        'c083ce11-b83f-44cf-9a15-ab1df7f39814');
INSERT INTO public.booking (id, check_in, check_out, room_id, user_id)
VALUES ('08866216-5633-4c79-ab69-a7d35a5c2d08', '2026-02-10', '2026-02-20', '2a8a23bf-f7cf-4b19-9f2e-d19fe2efb41b',
        'c083ce11-b83f-44cf-9a15-ab1df7f39814');
