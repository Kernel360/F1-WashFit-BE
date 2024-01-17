-- SEQUENCE 데이터 타입 변경
ALTER SEQUENCE brand_brand_no_seq as bigint;
ALTER SEQUENCE wash_info_wash_no_seq as bigint;
ALTER SEQUENCE car_info_car_no_seq as bigint;
ALTER SEQUENCE product_product_no_seq as bigint;

-- TABLE COLUMN 데이터 타입 변경
ALTER TABLE brand ALTER COLUMN brand_no TYPE bigint USING brand_no::bigint;
ALTER TABLE wash_info ALTER COLUMN wash_no TYPE bigint USING wash_no::bigint;
ALTER TABLE car_info ALTER COLUMN car_no TYPE bigint USING car_no::bigint;
ALTER TABLE product ALTER COLUMN product_no TYPE bigint USING product_no::bigint;
ALTER TABLE product ALTER COLUMN brand_no TYPE bigint USING brand_no::bigint;
ALTER TABLE common_code ALTER COLUMN code_no TYPE bigint USING code_no::bigint;
ALTER TABLE common_code ALTER COLUMN upper_no TYPE bigint USING upper_no::bigint;
ALTER TABLE auth ALTER COLUMN auth_no TYPE bigint USING auth_no::bigint;