
CREATE TABLE if not exists Member
(
    member_no   SERIAL PRIMARY KEY,
    id          VARCHAR NOT NULL,
    email       VARCHAR NOT NULL,
    password    VARCHAR NOT NULL,
    gender      CHAR,
    birthdate   DATE,
    created_at  DATE    NOT NULL,
    created_by  VARCHAR NOT NULL,
    modified_at DATE,
    modified_by VARCHAR
);

CREATE TABLE if not exists Brand
(
    brand_no    SERIAL PRIMARY KEY,
    brand_name  VARCHAR NOT NULL,
    description VARCHAR,
    nation_name VARCHAR,
    created_at  DATE    NOT NULL,
    created_by  VARCHAR NOT NULL,
    modified_at DATE,
    modified_by VARCHAR
);

CREATE TABLE if not exists Wash_Info
(
    wash_no         SERIAL PRIMARY KEY,
    member_no       INT     NOT NULL,
    wash_count      INT,
    monthly_expense INT,
    interest        CHAR,
    driving_env     CHAR,
    parking_env     CHAR,
    created_at      DATE    NOT NULL,
    created_by      VARCHAR NOT NULL,
    modified_at     DATE,
    modified_by     VARCHAR,
    FOREIGN KEY (member_no) REFERENCES Member (member_no)
);

CREATE TABLE if not exists Car_Info
(
    car_no      SERIAL PRIMARY KEY,
    member_no   INT     NOT NULL,
    car_brand   CHAR,
    car_type    CHAR,
    car_size    CHAR,
    pearl       BOOLEAN,
    clear_coat  BOOLEAN,
    created_at  DATE    NOT NULL,
    created_by  VARCHAR NOT NULL,
    modified_at DATE,
    modified_by CHAR,
    FOREIGN KEY (member_no) REFERENCES Member (member_no)
);

CREATE TABLE if not exists Product
(
    product_no   SERIAL PRIMARY KEY,
    brand_no     INT     NOT NULL,
    product_name VARCHAR NOT NULL,
    barcode      VARCHAR,
    description  VARCHAR,
    declare_no   VARCHAR NOT NULL,
    is_violation BOOLEAN NOT NULL DEFAULT false,
    created_at   DATE    NOT NULL,
    created_by   VARCHAR NOT NULL,
    modified_at  DATE,
    modified_by  VARCHAR,
    view_count   INT     NOT NULL DEFAULT 0,
    FOREIGN KEY (brand_no) REFERENCES Brand (brand_no)
);
