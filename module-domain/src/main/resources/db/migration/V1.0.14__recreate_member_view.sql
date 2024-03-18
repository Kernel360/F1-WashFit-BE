-- V1.0.2 와 완전히 동일 -> 스크립트 통합 필요
-- AES 암호화를 위한 확장 모듈 설치
CREATE EXTENSION IF NOT EXISTS pgcrypto;


-- 테이블 복호화 함수
CREATE OR REPLACE FUNCTION washpedia_member_decrypt() RETURNS TRIGGER AS
$$
BEGIN
    -- 비밀번호 복호화
    NEW.password := pgp_sym_decrypt(NEW.password, 'changeRequired');
    -- 이메일 복호화
    NEW.email := pgp_sym_decrypt(NEW.email, 'changeRequired');

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- 테이블 복호화 트리거 insert, update 이벤트 후 member_view 업데이트
DROP TRIGGER IF EXISTS washpedia_member_decrypt_trigger ON member;

CREATE TRIGGER washpedia_member_decrypt_trigger
    AFTER INSERT OR UPDATE
    ON member
    FOR EACH ROW
EXECUTE FUNCTION washpedia_member_decrypt();


-- 복호화 뷰 생성
DROP VIEW IF EXISTS member_view;

CREATE OR REPLACE VIEW member_view AS
SELECT member_no,
       id,
       encode(pgp_sym_decrypt(password, 'changeRequired')::bytea, 'escape') as password,
       encode(pgp_sym_decrypt(email, 'changeRequired')::bytea, 'escape')    as email,
       gender,
       age,
       created_at,
       created_by,
       modified_at,
       modified_by,
       account_type
FROM member;


/** 뷰 TO 테이블 바인딩 **/

-- 뷰 insert 함수
CREATE
    OR REPLACE FUNCTION member_view_insert_trigger()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO member (member_no, id, "password", email, gender, age, created_at, created_by, modified_at, modified_by, account_type)
    VALUES (nextval('member_member_no_seq'::regclass), NEW.id, pgp_sym_encrypt(NEW.password::TEXT, 'changeRequired'),
            pgp_sym_encrypt(NEW.email::TEXT, 'changeRequired'), NEW.gender, NEW.age, NEW.created_at, NEW.created_by,
            NEW.modified_at, NEW.modified_by, NEW.account_type);

    RETURN NEW;

END;
$$
    LANGUAGE plpgsql;


-- 뷰 insert 트리거
DROP TRIGGER IF EXISTS member_view_insert_trigger ON member;

CREATE TRIGGER member_view_insert_trigger
    INSTEAD OF INSERT
    ON member_view
    FOR EACH ROW
EXECUTE FUNCTION member_view_insert_trigger();


-- 뷰 update 함수
CREATE
    OR REPLACE FUNCTION member_view_update_trigger()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE member
    SET id          = NEW.id,
        "password"  = pgp_sym_encrypt(NEW.password::TEXT, 'changeRequired'),
        email       = pgp_sym_encrypt(NEW.email::TEXT, 'changeRequired'),
        gender      = NEW.gender,
        age         = NEW.age,
        created_at  = NEW.created_at,
        created_by  = NEW.created_by,
        modified_at = NEW.modified_at,
        modified_by = NEW.modified_by,
        account_type = NEW.account_type
    WHERE member_no = NEW.member_no;
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;


-- 뷰 update 트리거
DROP TRIGGER IF EXISTS member_view_update_trigger ON member;

CREATE TRIGGER member_view_update_trigger
    INSTEAD OF UPDATE
    ON member_view
    FOR EACH ROW
EXECUTE FUNCTION member_view_update_trigger();


-- 뷰 delete 함수
CREATE
    OR REPLACE FUNCTION member_view_delete_trigger()
    RETURNS TRIGGER AS
$$
BEGIN
    DELETE
    FROM member
    WHERE id = OLD.id;
    RETURN OLD;
END;
$$
    LANGUAGE plpgsql;


-- 뷰 delete 트리거
DROP TRIGGER IF EXISTS member_view_delete_trigger ON member;

CREATE TRIGGER member_view_delete_trigger
    INSTEAD OF DELETE
    ON member_view
    FOR EACH ROW
EXECUTE FUNCTION member_view_delete_trigger();


-- 테이블 member 권한 회수 설정
REVOKE INSERT, UPDATE, DELETE, TRUNCATE ON member FROM wash_admin;