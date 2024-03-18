CREATE TABLE if not exists Common_Code
(
    code_no         BIGINT  NOT NULL,
    code_name       varchar NOT NULL,
    upper_no        BIGINT  NULL,
    upper_name      varchar NULL,
    sort_order      int4    NOT NULL,
    is_used         bool    NOT NULL DEFAULT true,
    description     varchar NULL,
    sub_description varchar null,
    created_at      date    NOT NULL,
    created_by      varchar NOT NULL,
    modified_at     date    NULL,
    modified_by     varchar NULL
);


CREATE TABLE if not exists Auth
(
    auth_no     BIGSERIAL NOT NULL,
    member_no   BIGINT    NULL,
    jwt_token   varchar   NULL,
    sns_token   varchar   NULL,
    created_at  date      NOT NULL,
    created_by  varchar   NOT NULL,
    modified_at date      NULL,
    modified_by varchar   NULL

);


CREATE TABLE if not exists Member
(
    member_no   BIGSERIAL PRIMARY KEY,
    id          VARCHAR NOT NULL UNIQUE,
    email       BYTEA   NOT NULL UNIQUE,
    password    BYTEA   NOT NULL,
    gender      int,
    age         int,
    created_at  DATE    NOT NULL,
    created_by  VARCHAR NOT NULL,
    modified_at DATE,
    modified_by VARCHAR,
    account_type varchar
);


CREATE TABLE if not exists Wash_Info
(
    wash_no         BIGSERIAL PRIMARY KEY,
    member_no       BIGINT  NOT NULL,
    wash_count      INT,
    monthly_expense INT,
    interest        INT,
    created_at      DATE    NOT NULL,
    created_by      VARCHAR NOT NULL,
    modified_at     DATE,
    modified_by     VARCHAR,
    FOREIGN KEY (member_no) REFERENCES Member (member_no)
);


CREATE TABLE if not exists Car_Info
(
    car_no      BIGSERIAL PRIMARY KEY,
    member_no   BIGINT  NOT NULL,
    car_type    INT,
    car_size    INT,
    car_color   INT,
    driving_env INT,
    parking_env INT,
    created_at  DATE    NOT NULL,
    created_by  VARCHAR NOT NULL,
    modified_at DATE,
    modified_by VARCHAR,
    FOREIGN KEY (member_no) REFERENCES Member (member_no)
);


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


CREATE TABLE if not exists product
(
    product_no                 BIGSERIAL    PRIMARY KEY,
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
    violation_info             VARCHAR(255) -- 위반 여부 정보 별첨, 이 값이 있으면 주의해야 할 제품 --
);


alter sequence auth_auth_no_seq increment by 50;
alter sequence member_member_no_seq increment by 50;
alter sequence wash_info_wash_no_seq increment by 50;
alter sequence car_info_car_no_seq increment by 50;
alter sequence brand_brand_no_seq increment by 50;
alter sequence product_product_no_seq increment by 50;