
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

CREATE SEQUENCE if not exists public.member_member_no_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

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

CREATE SEQUENCE if not exists public.brand_brand_no_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

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

CREATE SEQUENCE if not exists public.wash_info_wash_no_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

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

CREATE SEQUENCE if not exists public.car_info_car_no_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

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

CREATE SEQUENCE if not exists public.product_product_no_seq
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

CREATE TABLE if not exists common_code
(
    code_no int4 NOT NULL,
    code_name varchar NOT NULL,
    upper_no int4 NULL,
    upper_name varchar NULL,
    sort_order int4 NOT NULL,
    is_used bool NOT NULL DEFAULT true,
    description varchar NULL,
    created_at date NOT NULL,
    created_by varchar NOT NULL,
    modified_at date NULL,
    modified_by varchar NULL
);

INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(1, 'carbrand', 0, '', 1, false, '차량 브랜드', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(2, 'Hyundai', 1, 'carbrand', 1, false, '현대', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(3, 'Kia', 1, 'carbrand', 2, false, '기아', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(4, 'Renault', 1, 'carbrand', 3, false, '르노', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(5, 'KG Mobility', 1, 'carbrand', 4, false, 'KG모빌리티', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(5, 'Chevrolet', 1, 'carbrand', 5, false, '쉐보레', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(6, 'Benz', 1, 'carbrand', 6, false, '벤츠', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(7, 'BMW', 1, 'carbrand', 7, false, '벰베', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(8, 'Audi', 1, 'carbrand', 8, false, '아우디', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(9, 'ETC', 1, 'carbrand', 9, false, '기타', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(10, 'cartype', 0, '', 2, true, '차량 유형', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(11, 'Sedan', 10, 'cartype', 1, true, '세단', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(12, 'Hatchback', 10, 'cartype', 2, true, '해치백', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(13, 'SUV', 10, 'cartype', 3, true, 'SUV', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(14, 'ETC', 10, 'cartype', 4, true, '기타', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(15, 'segment', 0, '', 3, true, '차량 크기', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(16, 'Micro', 15, 'segment', 1, true, '경차', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(17, 'Subcompact', 15, 'segment', 2, true, '소형', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(18, 'Compact', 15, 'segment', 3, true, '중형', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(19, 'Fullsize', 15, 'segment', 4, true, '대형', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(20, 'color', 0, '', 4, true, '차량 색상', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(21, 'white', 20, 'color', 1, true, '흰색', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(22, 'gray', 20, 'color', 5, true, '쥐색', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(23, 'black', 20, 'color', 6, true, '검정색', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(24, 'red', 20, 'color', 4, true, '빨간색', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(25, 'yellow', 20, 'color', 2, true, '노란색', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(26, 'green', 20, 'color', 3, true, '초록색', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(27, 'blue', 20, 'color', 4, true, '파란색', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(28, 'etc', 20, 'color', 7, true, '기타', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(29, 'perl', 0, '', 5, true, '차량 페인트 펄', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(30, 'TRUE', 28, 'perl', 1, true, '있음', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(31, 'FALSE', 28, 'perl', 2, true, '없음', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(32, 'clearcoat', 0, '', 6, true, '차량 페인트 마감', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(33, 'TRUE', 31, 'clearcoat', 1, true, '있음', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(34, 'FALSE', 31, 'clearcoat', 2, true, '없음', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(35, 'driving', 0, '', 7, true, '주행환경', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(36, 'comport', 34, 'driving', 1, true, '도심', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(37, 'highway', 34, 'driving', 2, true, '고속', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(38, 'complex', 34, 'driving', 3, true, '복합', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(39, 'parking', 0, '', 8, true, '주차환경', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(40, 'house', 38, 'parking', 1, true, '실내/지하', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(41, 'road', 38, 'parking', 2, true, '노상', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(42, 'piloti', 38, 'parking', 3, true, '필로티', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(43, 'interest', 0, '', 9, true, '주요관심사', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(44, 'outercare', 42, 'washInfo', 1, true, '도장', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(45, 'tire', 42, 'washInfo', 2, true, '타이어', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(46, 'wheel', 42, 'washInfo', 3, true, '휠', '2023-12-28', 'admin', NULL, NULL);
INSERT INTO common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(47, 'innercare', 42, 'washInfo', 4, true, '실내', '2023-12-28', 'admin', NULL, NULL);