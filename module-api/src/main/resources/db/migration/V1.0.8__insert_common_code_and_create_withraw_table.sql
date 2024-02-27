CREATE TABLE if not exists withraw_member
(
    member_no   bigserial         NOT NULL,
    id          varchar           NOT NULL,
    email       varchar           NOT NULL,
    ip          varchar,
    created_at  date              NOT NULL,
    created_by  varchar(10485760) NULL,
    modified_at date              NULL,
    modified_by varchar(10485760) NULL
);


INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description,
                                sub_description, created_at, created_by, modified_at, modified_by)
VALUES (67, 'OTHERS', 48, NULL, 3, true, '변경필요', NULL, '2024-01-10', 'admin', NULL, NULL)
ON CONFLICT DO NOTHING;
INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description,
                                sub_description, created_at, created_by, modified_at, modified_by)
VALUES (68, 'AGE_99', 51, NULL, 6, true, '변경필요', NULL, '2024-01-10', 'admin', NULL, NULL)
ON CONFLICT DO NOTHING;

UPDATE public.common_code
SET code_name='MALE',
    modified_at = CURRENT_TIMESTAMP,
    modified_by = 'admin'
WHERE code_no = 49
;

UPDATE public.common_code
SET code_name='FEMALE',
    modified_at = CURRENT_TIMESTAMP,
    modified_by = 'admin'
WHERE code_no = 50
;