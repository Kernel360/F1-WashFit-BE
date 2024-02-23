CREATE TABLE IF NOT EXISTS admin
(
    admin_no    BIGSERIAL PRIMARY KEY,
    id          VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    created_at  DATE NOT NULL,
    created_by  VARCHAR NOT NULL,
    modified_at DATE,
    modified_by VARCHAR,
    CONSTRAINT admin_id_unique UNIQUE (id),
    CONSTRAINT admin_email_unique UNIQUE (email)
    );


alter sequence admin_admin_no_seq increment by 50;
