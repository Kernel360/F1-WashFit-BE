CREATE TABLE if not exists Review (
    review_no   BIGSERIAL PRIMARY KEY,
    product_no  BIGINT NOT NULL,
    member_no   BIGINT NOT NULL,
    star_rating NUMERIC(3,1) NOT NULL,
    title       VARCHAR NOT NULL,
    contents    VARCHAR NOT NULL,
    created_at  DATE NOT NULL,
    created_by  VARCHAR NOT NULL,
    modified_at DATE NULL,
    modified_by VARCHAR NULL,
    FOREIGN KEY (product_no) REFERENCES Product (product_no),
    FOREIGN KEY (member_no) REFERENCES Member (member_no)
);

AlTER SEQUENCE review_review_no_seq increment by 50;
