CREATE TABLE IF NOT EXISTS hotels
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(64)  NOT NULL,
    title           VARCHAR(200) NOT NULL,
    city            VARCHAR(100) NOT NULL,
    address         VARCHAR(200) NOT NULL,
    distance        NUMERIC(2,1) NOT NULL,
    rating          NUMERIC(2,1) NOT NULL,
    rating_count    INTEGER NOT NULL DEFAULT 0,

    CONSTRAINT chk_hotel_rating
        CHECK (rating >= 1 AND rating <= 5),

    CONSTRAINT chk_hotel_distance
        CHECK (distance >= 0),

    CONSTRAINT chk_hotel_rating_count
        CHECK (rating_count >= 0)
);