-- 공통코드 성별, 연령대 추가
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(48, 'gender', 0, NULL, 10, true, NULL, '2024-01-10', 'admin', NULL, NULL);
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(49, 'man', 48, 'gender', 1, true, '남성', '2024-01-10', 'admin', NULL, NULL);
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(50, 'woman', 48, 'gender', 2, true, '여성', '2024-01-10', 'admin', NULL, NULL);
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(52, 'age', 0, NULL, 11, true, '연령대', '2024-01-10', 'admin', NULL, NULL);
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(53, '20s', 52, 'age', 1, true, '20대 이하', '2024-01-10', 'admin', NULL, NULL);
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(54, '30s', 52, 'age', 2, true, '30대', '2024-01-10', 'admin', NULL, NULL);
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(55, '40s', 52, 'age', 3, true, '40대', '2024-01-10', 'admin', NULL, NULL);
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(56, '50s', 52, 'age', 4, true, '50대', '2024-01-10', 'admin', NULL, NULL);
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at, created_by, modified_at, modified_by) VALUES(57, '60s', 52, 'age', 5, true, '60대 이상', '2024-01-10', 'admin', NULL, NULL);

-- 권한 테이블
CREATE TABLE if not exists Auth (
    auth_no	    int	NOT NULL,
    member_no	int NULL,
    jwt_token	varchar	NULL,
    sns_token	varchar	NULL,
    created_at  date NOT NULL,
    created_by  varchar NOT NULL,
    modified_at date NULL,
    modified_by varchar NULL
);
CREATE SEQUENCE if not exists auth_auth_no_seq increment  by 50;
alter sequence auth_auth_no_seq increment by 50;