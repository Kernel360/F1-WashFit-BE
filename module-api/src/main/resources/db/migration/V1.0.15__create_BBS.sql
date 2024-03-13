CREATE TABLE IF NOT EXISTS bbs (
    bbs_no      BIGSERIAL PRIMARY KEY,
    upper_no    BIGINT,
    member_no   BIGINT NOT NULL,
    type        VARCHAR NOT NULL,
    title       VARCHAR NOT NULL,
    contents    VARCHAR NOT NULL,
    is_visible  BOOL NOT NULL DEFAULT TRUE,
    view_count  BIGINT NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    created_by  VARCHAR NOT NULL,
    modified_at TIMESTAMP,
    modified_by VARCHAR,
    FOREIGN KEY (member_no) REFERENCES Member (member_no)
    );

AlTER SEQUENCE bbs_bbs_no_seq increment by 50;
