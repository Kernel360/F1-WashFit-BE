CREATE TABLE if not exists Washzone_Review
(
    washzone_review_no BIGSERIAL PRIMARY KEY,
    washzone_no        BIGINT        NOT NULL,
    member_no          BIGINT        NOT NULL,
    star_rating        NUMERIC(3, 1) NOT NULL,
    title              VARCHAR(255)  NOT NULL,
    contents           VARCHAR(4000) NOT NULL,
    is_visible         BOOL          NOT NULL DEFAULT TRUE,
    created_at         TIMESTAMP     NOT NULL,
    created_by         VARCHAR       NOT NULL,
    modified_at        TIMESTAMP     NULL,
    modified_by        VARCHAR       NULL,
    FOREIGN KEY (washzone_no) REFERENCES Wash_zone (washzone_no),
    FOREIGN KEY (member_no) REFERENCES Member (member_no),
    CONSTRAINT washzone_review_ukey UNIQUE (member_no, washzone_no)
);

AlTER SEQUENCE washzone_review_washzone_review_no_seq increment by 50;
