CREATE TABLE IF NOT EXISTS users
(
    id            UUID PRIMARY KEY,
    email         VARCHAR(100) NOT NULL,
    username      VARCHAR(100) NOT NULL,
    role          VARCHAR(10)  NOT NULL,
    active        BOOLEAN      NOT NULL,
    password_hash VARCHAR(200) NOT NULL,

    CONSTRAINT chk_user_role
        CHECK (role IN ('USER', 'ADMIN')),

    CONSTRAINT uk_users_email
        UNIQUE (email)
);

INSERT INTO users (id,
                   email,
                   username,
                   role,
                   active,
                   password_hash)
VALUES (gen_random_uuid(),
        'admin@example.com',
        'Administrator',

        'ADMIN',
        false,
        '$2y$10$iiyysNTMIMk9onnZTykeiuZgNnAKT/1aBYW0G1fR80qQn/yhqzjVm')
ON CONFLICT (email) DO NOTHING;