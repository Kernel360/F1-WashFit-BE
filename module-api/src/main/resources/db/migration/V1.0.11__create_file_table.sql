CREATE TABLE IF NOT EXISTS file
(
    file_no        BIGSERIAL PRIMARY KEY,
    file_name      VARCHAR(255) NOT NULL,
    file_key       VARCHAR(255) NOT NULL UNIQUE,
    file_url       VARCHAR(500) NOT NULL UNIQUE,
    reference_type VARCHAR(50),
    reference_no   BIGINT,
    created_at     DATE         NOT NULL,
    created_by     VARCHAR      NOT NULL,
    modified_at    DATE,
    modified_by    VARCHAR
);

alter sequence file_file_no_seq increment by 50;