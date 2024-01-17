DROP VIEW member_view;

ALTER SEQUENCE member_member_no_seq as bigint;

ALTER TABLE member ALTER COLUMN member_no TYPE bigint USING member_no::bigint;
ALTER TABLE wash_info ALTER COLUMN member_no TYPE bigint USING member_no::bigint;
ALTER TABLE car_info ALTER COLUMN member_no TYPE bigint USING member_no::bigint;
ALTER TABLE auth ALTER COLUMN member_no TYPE bigint USING member_no::bigint;

-- ### member 테이블의 member_no 컬럼 타입 변경으로 인한 뷰 drop-create ###
-- 복호화 뷰 생성, 업데이트
CREATE OR REPLACE VIEW member_view AS
SELECT
    member_no,
    id,
    encode(pgp_sym_decrypt(password, 'changeRequired')::bytea, 'escape') as password,
    encode(pgp_sym_decrypt(email, 'changeRequired')::bytea, 'escape') as email,
    gender,
    birthdate,
    created_at,
    created_by,
    modified_at,
    modified_by
FROM member;


-- 뷰 insert 함수
CREATE OR REPLACE FUNCTION member_view_insert_trigger()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO member (member_no, id, "password", email, gender, birthdate, created_at, created_by, modified_at, modified_by)
    VALUES (nextval('member_member_no_seq'::regclass), NEW.id, pgp_sym_encrypt(NEW.password::TEXT, 'changeRequired'), pgp_sym_encrypt(NEW.email::TEXT, 'changeRequired'), NEW.gender, NEW.birthdate, NEW.created_at, NEW.created_by, NEW.modified_at, NEW.modified_by);

    RETURN NEW;

END;
$$ LANGUAGE plpgsql;

-- 뷰 insert 트리거
CREATE TRIGGER member_view_insert_trigger
    INSTEAD OF INSERT ON member_view
    FOR EACH ROW EXECUTE FUNCTION member_view_insert_trigger();


-- 뷰 update 함수
CREATE OR REPLACE FUNCTION member_view_update_trigger()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE member SET
                      id = NEW.id,
                      "password" = pgp_sym_encrypt(NEW.password::TEXT, 'changeRequired'),
                      email = pgp_sym_encrypt(NEW.email::TEXT, 'changeRequired'),
                      gender = NEW.gender,
                      birthdate = NEW.birthdate,
                      created_at = NEW.created_at,
                      created_by = NEW.created_by,
                      modified_at = NEW.modified_at,
                      modified_by = NEW.modified_by
    WHERE member_no = NEW.member_no;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 뷰 update 트리거
CREATE TRIGGER member_view_update_trigger
    INSTEAD OF UPDATE ON member_view
    FOR EACH ROW EXECUTE FUNCTION member_view_update_trigger();


-- 뷰 delete 함수
CREATE OR REPLACE FUNCTION member_view_delete_trigger()
    RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM member WHERE id = OLD.id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- 뷰 delete 트리거
CREATE TRIGGER member_view_delete_trigger
    INSTEAD OF DELETE ON member_view
    FOR EACH ROW EXECUTE FUNCTION member_view_delete_trigger();