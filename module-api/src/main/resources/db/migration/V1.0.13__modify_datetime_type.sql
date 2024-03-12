ALTER TABLE if exists admin
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists all_ingredient_product
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists auth
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists brand
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists car_info
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists common_code
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists concerned_product
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists file
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists likes
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

-- 멤버 뷰 삭제 --
DROP VIEW if exists member_view;

ALTER TABLE if exists member
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists product
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists reported_product
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists review
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists violated_product
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists wash_info
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;

ALTER TABLE if exists withdraw_member
    ALTER COLUMN created_at TYPE TIMESTAMP,
    ALTER COLUMN modified_at TYPE TIMESTAMP;