-- Brand 에 company_name 컬럼 추가
drop table if exists product;
drop TABLE if exists Brand;

CREATE TABLE if not exists Brand
(
    brand_no     BIGSERIAL PRIMARY KEY,
    brand_name   VARCHAR NOT NULL,
    company_name VARCHAR NOT NULL,
    description  VARCHAR,
    nation_name  VARCHAR,
    created_at   DATE    NOT NULL,
    created_by   VARCHAR NOT NULL,
    modified_at  DATE,
    modified_by  VARCHAR
);


Insert Into brand (brand_no, brand_name, company_name, nation_name, created_at, created_by)
VALUES (1, '더클래스', '코스메디슨(CosMedicine)', '대한민국', current_date, 'admin'),
       (51, '크리스탈 코트', '(주) 불스원', '대한민국', current_date, 'admin'),
       (101, '불스원', '(주) 불스원', '대한민국', current_date, 'admin'),
       (151, '글로스브로', '주식회사 제이씨웍스', '대한민국', current_date, 'admin'),
       (201, '파이어볼', '(주)파이어볼', '대한민국', current_date, 'admin'),
       (251, '티에이씨시스템', '티에이씨시스템', '대한민국', current_date, 'admin'),
       (301, '기온테크놀로지', '주식회사 기온테크놀로지(Gyeon Technology)', '대한민국', current_date, 'admin'),
       (351, '루나틱폴리시', '(주) 불스원', '대한민국', current_date, 'admin'),
       (401, '젠틀맨', '주식회사 에스피', '대한민국', current_date, 'admin'),
       (451, '디아만테', '현일', '대한민국', current_date, 'admin'),
       (501, '(주)오토왁스', '(주)오토왁스', '대한민국', current_date, 'admin'),

       (551, '라보코스메디카', '주식회사 대흥아이앤씨(INC)', '이탈리아', current_date, 'admin'),
       (601, '마프라', '주식회사 대흥아이앤씨(INC)', '이탈리아', current_date, 'admin'),
       (651, '매니악', '주식회사 대흥아이앤씨(INC)', '이탈리아', current_date, 'admin'),
       (701, 'EXQ', '이엑스큐(EXQ)', '대한민국', current_date, 'admin'),
       (751, 'EXQ', '이엑스큐(EXQ)', '중국', current_date, 'admin'),

       (801, '터틀왁스', '(주)에스씨에이', '미국', current_date, 'admin'),
       (851, '잭스왁스', '주식회사 잭스왁스코리아', '미국', current_date, 'admin'),
       (901, '오토브라이트', '주식회사 오토브라이트다이렉트코리아(AUTOBRITE DIRECT KOREA CO.,LTD)', '영국', current_date, 'admin'),

       (951, '블루믹스', '제이웍스 (J-works)', '대한민국', current_date, 'admin'),
       (1001, '림피오', '림피오(LIMPIO)', '대한민국', current_date, 'admin'),
       (1051, '좀비', '한국씨앤에스', '대한민국', current_date, 'admin'),
       (1101, '카프로', '(주)카프로코리아', '대한민국', current_date, 'admin'),

       (1151, '이지카케어', '발스코리아(주)', '영국', current_date, 'admin'),
       (1201, '니그린', '(주)다스모터스', '영국', current_date, 'admin'),
       (1251, '도도쥬스', '주식회사투왁스', '영국', current_date, 'admin'),
--        (1301, '루미너스', '주식회사 엘엠', '대한민국', current_date, 'admin'),

       (1351, '소낙스', '(주)알레스', '독일', current_date, 'admin'),
       (1401, '스마트왁스', '에이큐에이 주식회사', '미국', current_date, 'admin'),
       (1451, '케미컬가이', '에이큐에이 주식회사', '미국', current_date, 'admin'),
       (1501, '리바이브', '주식회사 에스피', '영국', current_date, 'admin'),
       (1551, '보닉스', '대흥아이앤씨', '브라질', current_date, 'admin'),
       --        (1601, '더클래스', '더클래스', '대한민국', current_date, 'admin'),
       (1651, '루미너스', '루미너스코리아', '대한민국', current_date, 'admin');

CREATE SEQUENCE IF NOT EXISTS product_product_no_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE product
(
    product_no                 BIGSERIAL    NOT NULL,
    created_at                 date         NOT NULL,
    created_by                 VARCHAR(255) NOT NULL,
    modified_at                date,
    modified_by                VARCHAR(255),
    product_name               VARCHAR(255) NOT NULL,
    report_no                  VARCHAR(255),
    product_type               VARCHAR(255),
    manufacture_nation         VARCHAR(255),
    company_name               VARCHAR(255),
    safety_status              INTEGER      NOT NULL,
    issued_date                date         NOT NULL,
    barcode                    VARCHAR(255),
    img_src                    VARCHAR(255),
    view_count                 INTEGER      NOT NULL,
    safety_inspection_standard TEXT,
    upper_item                 VARCHAR(255),
    item                       VARCHAR(255),
    propose                    TEXT,
    weight                     VARCHAR(255),
    usage                      TEXT,
    usage_precaution           TEXT,
    first_aid                  TEXT,
    main_substance             TEXT,
    allergic_substance         TEXT,
    other_substance            TEXT,
    preservative               TEXT,
    surfactant                 TEXT,
    fluorescent_whitening      TEXT,
    manufacture_type           VARCHAR(255),
    manufacture_method         VARCHAR(255),
    brand_no                   BIGSERIAL,
    CONSTRAINT pk_product PRIMARY KEY (product_no)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_BRAND_NO FOREIGN KEY (brand_no) REFERENCES brand (brand_no);

alter sequence product_product_no_seq increment by 50;
alter sequence brand_brand_no_seq increment by 50;