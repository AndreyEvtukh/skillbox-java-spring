CREATE TABLE IF NOT EXISTS users
(
    id            UUID PRIMARY KEY,
    email         VARCHAR(100) NOT NULL,
    username      VARCHAR(100) NOT NULL,
    role          VARCHAR(10)  NOT NULL,
    password_hash VARCHAR(200) NOT NULL,

    CONSTRAINT chk_user_role
        CHECK (role IN ('USER', 'ADMIN'))
);