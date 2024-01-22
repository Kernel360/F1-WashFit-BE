-- 복호화 view 삭제
drop view member_view;

-- 함수 삭제
drop function member_view_delete_trigger;
drop function member_view_insert_trigger;
drop function member_view_update_trigger;

-- member 테이블 컬럼 자료형 변경
ALTER TABLE public."member" RENAME COLUMN birthdate TO age;
ALTER TABLE public."member" ALTER COLUMN age TYPE INT;
ALTER TABLE public."member" ALTER COLUMN gender TYPE INT;

-- 복호화 뷰 생성
CREATE
OR REPLACE VIEW member_view AS
SELECT member_no,
       id,
       encode(pgp_sym_decrypt(password, 'changedRequired')::bytea, 'escape') as password,
       encode(pgp_sym_decrypt(email, 'changedRequired')::bytea, 'escape')    as email,
       gender,
       age,
       created_at,
       created_by,
       modified_at,
       modified_by
FROM member;

/** 뷰 TO 테이블 바인딩 **/

-- 뷰 insert 함수
CREATE
OR REPLACE FUNCTION member_view_insert_trigger()
    RETURNS TRIGGER AS $$
BEGIN
INSERT INTO member (member_no, id, "password", email, gender, age, created_at, created_by, modified_at, modified_by)
VALUES (nextval('member_member_no_seq'::regclass), NEW.id, pgp_sym_encrypt(NEW.password::TEXT, 'changedRequired'),
        pgp_sym_encrypt(NEW.email::TEXT, 'changedRequired'), NEW.gender, NEW.age, NEW.created_at, NEW.created_by,
        NEW.modified_at, NEW.modified_by);

RETURN NEW;

END;
$$
LANGUAGE plpgsql;

-- 뷰 insert 트리거
CREATE TRIGGER member_view_insert_trigger
    INSTEAD OF INSERT
    ON member_view
    FOR EACH ROW EXECUTE FUNCTION member_view_insert_trigger();


-- 뷰 update 함수
CREATE
OR REPLACE FUNCTION member_view_update_trigger()
    RETURNS TRIGGER AS $$
BEGIN
UPDATE member
SET id          = NEW.id,
    "password"  = pgp_sym_encrypt(NEW.password::TEXT, 'changedRequired'),
    email       = pgp_sym_encrypt(NEW.email::TEXT, 'changedRequired'),
    gender      = NEW.gender,
    age         = NEW.age,
    created_at  = NEW.created_at,
    created_by  = NEW.created_by,
    modified_at = NEW.modified_at,
    modified_by = NEW.modified_by
WHERE member_no = NEW.member_no;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

-- 뷰 update 트리거
CREATE TRIGGER member_view_update_trigger
    INSTEAD OF UPDATE
    ON member_view
    FOR EACH ROW EXECUTE FUNCTION member_view_update_trigger();


-- 뷰 delete 함수
CREATE
OR REPLACE FUNCTION member_view_delete_trigger()
    RETURNS TRIGGER AS $$
BEGIN
DELETE
FROM member
WHERE id = OLD.id;
RETURN OLD;
END;
$$
LANGUAGE plpgsql;

-- 뷰 delete 트리거
CREATE TRIGGER member_view_delete_trigger
    INSTEAD OF DELETE
    ON member_view
    FOR EACH ROW EXECUTE FUNCTION member_view_delete_trigger();

/** 공통 코드 추가 **/

TRUNCATE TABLE public.common_code;

INSERT INTO public.common_code (code_no,code_name,upper_no,upper_name,sort_order,is_used,description,created_at,created_by,modified_at,modified_by) VALUES
    (1,'carbrand',0,'',1,false,'차량 브랜드','2023-12-28','admin',NULL,NULL),
    (2,'Hyundai',1,'carbrand',1,false,'현대','2023-12-28','admin',NULL,NULL),
    (3,'Kia',1,'carbrand',2,false,'기아','2023-12-28','admin',NULL,NULL),
    (4,'Renault',1,'carbrand',3,false,'르노','2023-12-28','admin',NULL,NULL),
    (5,'KG Mobility',1,'carbrand',4,false,'KG모빌리티','2023-12-28','admin',NULL,NULL),
    (5,'Chevrolet',1,'carbrand',5,false,'쉐보레','2023-12-28','admin',NULL,NULL),
    (6,'Benz',1,'carbrand',6,false,'벤츠','2023-12-28','admin',NULL,NULL),
    (7,'BMW',1,'carbrand',7,false,'벰베','2023-12-28','admin',NULL,NULL),
    (8,'Audi',1,'carbrand',8,false,'아우디','2023-12-28','admin',NULL,NULL),
    (9,'ETC',1,'carbrand',9,false,'기타','2023-12-28','admin',NULL,NULL);
INSERT INTO public.common_code (code_no,code_name,upper_no,upper_name,sort_order,is_used,description,created_at,created_by,modified_at,modified_by) VALUES
    (10,'cartype',0,'',2,true,'차량 유형','2023-12-28','admin',NULL,NULL),
    (11,'Sedan',10,'cartype',1,true,'세단','2023-12-28','admin',NULL,NULL),
    (12,'Hatchback',10,'cartype',2,true,'해치백','2023-12-28','admin',NULL,NULL),
    (13,'SUV',10,'cartype',3,true,'SUV','2023-12-28','admin',NULL,NULL),
    (14,'ETC',10,'cartype',4,true,'기타','2023-12-28','admin',NULL,NULL),
    (15,'segment',0,'',3,true,'차량 크기','2023-12-28','admin',NULL,NULL),
    (16,'Micro',15,'segment',1,true,'경차','2023-12-28','admin',NULL,NULL),
    (17,'Subcompact',15,'segment',2,true,'소형','2023-12-28','admin',NULL,NULL),
    (18,'Compact',15,'segment',3,true,'중형','2023-12-28','admin',NULL,NULL),
    (19,'Fullsize',15,'segment',4,true,'대형','2023-12-28','admin',NULL,NULL);
INSERT INTO public.common_code (code_no,code_name,upper_no,upper_name,sort_order,is_used,description,created_at,created_by,modified_at,modified_by) VALUES
    (20,'color',0,'',4,true,'차량 색상','2023-12-28','admin',NULL,NULL),
    (29,'perl',0,'',5,true,'차량 페인트 펄','2023-12-28','admin',NULL,NULL),
    (30,'TRUE',28,'perl',1,true,'있음','2023-12-28','admin',NULL,NULL),
    (31,'FALSE',28,'perl',2,true,'없음','2023-12-28','admin',NULL,NULL),
    (32,'clearcoat',0,'',6,true,'차량 페인트 마감','2023-12-28','admin',NULL,NULL),
    (33,'TRUE',31,'clearcoat',1,true,'있음','2023-12-28','admin',NULL,NULL),
    (34,'FALSE',31,'clearcoat',2,true,'없음','2023-12-28','admin',NULL,NULL),
    (35,'driving',0,'',7,true,'주행환경','2023-12-28','admin',NULL,NULL),
    (36,'comport',34,'driving',1,true,'도심','2023-12-28','admin',NULL,NULL),
    (37,'highway',34,'driving',2,true,'고속','2023-12-28','admin',NULL,NULL);
INSERT INTO public.common_code (code_no,code_name,upper_no,upper_name,sort_order,is_used,description,created_at,created_by,modified_at,modified_by) VALUES
    (38,'complex',34,'driving',3,true,'복합','2023-12-28','admin',NULL,NULL),
    (39,'parking',0,'',8,true,'주차환경','2023-12-28','admin',NULL,NULL),
    (40,'house',38,'parking',1,true,'실내/지하','2023-12-28','admin',NULL,NULL),
    (41,'road',38,'parking',2,true,'노상','2023-12-28','admin',NULL,NULL),
    (42,'piloti',38,'parking',3,true,'필로티','2023-12-28','admin',NULL,NULL),
    (43,'interest',0,'',9,true,'주요관심사','2023-12-28','admin',NULL,NULL),
    (48,'gender',0,NULL,10,true,NULL,'2024-01-10','admin',NULL,NULL),
    (49,'man',48,'gender',1,true,'남성','2024-01-10','admin',NULL,NULL),
    (50,'woman',48,'gender',2,true,'여성','2024-01-10','admin',NULL,NULL),
    (21,'FFFFFF',20,'color',1,true,'흰색','2023-12-28','admin',NULL,NULL);
INSERT INTO public.common_code (code_no,code_name,upper_no,upper_name,sort_order,is_used,description,created_at,created_by,modified_at,modified_by) VALUES
    (22,'808080',20,'color',5,true,'쥐색','2023-12-28','admin',NULL,NULL),
    (23,'37383C',20,'color',6,true,'검정색','2023-12-28','admin',NULL,NULL),
    (24,'FF4500',20,'color',4,true,'빨간색','2023-12-28','admin',NULL,NULL),
    (25,'FFD400',20,'color',2,true,'노란색','2023-12-28','admin',NULL,NULL),
    (51,'age',0,NULL,11,true,'연령대','2024-01-10','admin',NULL,NULL),
    (52,'AGE_20',51,'age',1,true,'20대 이하','2024-01-10','admin',NULL,NULL),
    (53,'AGE_30',51,'age',2,true,'30대','2024-01-10','admin',NULL,NULL),
    (54,'AGE_40',51,'age',3,true,'40대','2024-01-10','admin',NULL,NULL),
    (55,'AGE_50',51,'age',4,true,'50대','2024-01-10','admin',NULL,NULL),
    (56,'AGE_60',51,'age',5,true,'60대 이상','2024-01-10','admin',NULL,NULL);
INSERT INTO public.common_code (code_no,code_name,upper_no,upper_name,sort_order,is_used,description,created_at,created_by,modified_at,modified_by) VALUES
    (57,'frequency',0,NULL,12,true,'세차빈도','2024-01-10','admin',NULL,NULL),
    (58,'month1',57,'frequency',1,true,'월 평균 1회','2024-01-10','admin',NULL,NULL),
    (59,'month2',57,'frequency',2,true,'월 평균 2회','2024-01-10','admin',NULL,NULL),
    (60,'month3',57,'frequency',3,true,'월 평균 3회','2024-01-10','admin',NULL,NULL),
    (61,'month4',57,'frequency',4,true,'월 평균 4회','2024-01-10','admin',NULL,NULL),
    (62,'cost',0,NULL,13,true,'지출비용','2024-01-10','admin',NULL,NULL),
    (63,'1to3',62,'cost',1,true,'월 평균 1~3만원','2024-01-10','admin',NULL,NULL),
    (64,'4to6',62,'cost',2,true,'월 평균 4~6만원','2024-01-10','admin',NULL,NULL),
    (26,'2F4F4F',20,'color',3,true,'초록색','2023-12-28','admin',NULL,NULL),
    (27,'145B7D',20,'color',4,true,'파란색','2023-12-28','admin',NULL,NULL);
INSERT INTO public.common_code (code_no,code_name,upper_no,upper_name,sort_order,is_used,description,created_at,created_by,modified_at,modified_by) VALUES
    (28,'6E0000',20,'color',7,true,'기타','2023-12-28','admin',NULL,NULL),
    (44,'outercare',42,'interest',1,true,'도장','2023-12-28','admin',NULL,NULL),
    (45,'tire',42,'interest',2,true,'타이어','2023-12-28','admin',NULL,NULL),
    (46,'wheel',42,'interest',3,true,'휠','2023-12-28','admin',NULL,NULL),
    (47,'innercare',42,'interest',4,true,'실내','2023-12-28','admin',NULL,NULL),
    (65,'7to9',62,'cost',3,true,'월 평균 7~9만원','2024-01-10','admin',NULL,NULL),
    (66,'10over',62,'cost',4,true,'월 평균 10만원 이상','2024-01-10','admin',NULL,NULL);

