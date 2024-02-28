CREATE TABLE IF NOT EXISTS likes (
    like_no BIGSERIAL PRIMARY KEY,
    member_no BIGINT NOT NULL,
    product_no BIGINT NOT NULL,
    FOREIGN KEY (member_no) REFERENCES member(member_no) ,
    FOREIGN KEY (product_no) REFERENCES product(product_no) ,
    created_at  date      NOT NULL,
    created_by  varchar   NOT NULL,
    modified_at date      NULL,
    modified_by varchar   NULL
);

alter sequence likes_like_no_seq increment by 50;
