-- Insert Into common_code

INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at,
                                created_by, modified_at, modified_by, sub_description)
VALUES (1, 'carbrand', 0, '', 1, false, '차량 브랜드', '2023-12-28', 'admin', NULL, NULL, NULL),
       (10, 'cartype', 0, '', 2, true, '차량 유형', '2023-12-28', 'admin', NULL, NULL, NULL),
       (15, 'segment', 0, '', 3, true, '차량 크기', '2023-12-28', 'admin', NULL, NULL, NULL),
       (20, 'color', 0, '', 4, true, '차량 색상', '2023-12-28', 'admin', NULL, NULL, NULL),
       (29, 'perl', 0, '', 5, true, '차량 페인트 펄', '2023-12-28', 'admin', NULL, NULL, NULL),
       (30, 'TRUE', 28, 'perl', 1, true, '있음', '2023-12-28', 'admin', NULL, NULL, NULL),
       (31, 'FALSE', 28, 'perl', 2, true, '없음', '2023-12-28', 'admin', NULL, NULL, NULL),
       (32, 'clearcoat', 0, '', 6, true, '차량 페인트 마감', '2023-12-28', 'admin', NULL, NULL, NULL),
       (33, 'TRUE', 31, 'clearcoat', 1, true, '있음', '2023-12-28', 'admin', NULL, NULL, NULL),
       (34, 'FALSE', 31, 'clearcoat', 2, true, '없음', '2023-12-28', 'admin', NULL, NULL, NULL);


INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at,
                                created_by, modified_at, modified_by, sub_description)
VALUES (35, 'driving', 0, '', 7, true, '주행환경', '2023-12-28', 'admin', NULL, NULL, NULL),
       (36, 'comport', 34, 'driving', 1, true, '도심', '2023-12-28', 'admin', NULL, NULL, NULL),
       (37, 'highway', 34, 'driving', 2, true, '고속', '2023-12-28', 'admin', NULL, NULL, NULL),
       (38, 'complex', 34, 'driving', 3, true, '복합', '2023-12-28', 'admin', NULL, NULL, NULL),
       (39, 'parking', 0, '', 8, true, '주차환경', '2023-12-28', 'admin', NULL, NULL, NULL),
       (40, 'house', 38, 'parking', 1, true, '실내/지하', '2023-12-28', 'admin', NULL, NULL, NULL),
       (41, 'road', 38, 'parking', 2, true, '노상', '2023-12-28', 'admin', NULL, NULL, NULL),
       (42, 'piloti', 38, 'parking', 3, true, '필로티', '2023-12-28', 'admin', NULL, NULL, NULL),
       (43, 'interest', 0, '', 9, true, '주요관심사', '2023-12-28', 'admin', NULL, NULL, NULL),
       (48, 'gender', 0, NULL, 10, true, NULL, '2024-01-10', 'admin', NULL, NULL, NULL);


INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at,
                                created_by, modified_at, modified_by, sub_description)
VALUES (49, 'man', 48, 'gender', 1, true, '남성', '2024-01-10', 'admin', NULL, NULL, NULL),
       (50, 'woman', 48, 'gender', 2, true, '여성', '2024-01-10', 'admin', NULL, NULL, NULL),
       (51, 'age', 0, NULL, 11, true, '연령대', '2024-01-10', 'admin', NULL, NULL, NULL),
       (52, 'AGE_20', 51, 'age', 1, true, '20대 이하', '2024-01-10', 'admin', NULL, NULL, NULL),
       (53, 'AGE_30', 51, 'age', 2, true, '30대', '2024-01-10', 'admin', NULL, NULL, NULL),
       (54, 'AGE_40', 51, 'age', 3, true, '40대', '2024-01-10', 'admin', NULL, NULL, NULL),
       (55, 'AGE_50', 51, 'age', 4, true, '50대', '2024-01-10', 'admin', NULL, NULL, NULL),
       (56, 'AGE_60', 51, 'age', 5, true, '60대 이상', '2024-01-10', 'admin', NULL, NULL, NULL),
       (57, 'frequency', 0, NULL, 12, true, '세차빈도', '2024-01-10', 'admin', NULL, NULL, NULL),
       (58, 'month1', 57, 'frequency', 1, true, '월 평균 1회', '2024-01-10', 'admin', NULL, NULL, NULL);


INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at,
                                created_by, modified_at, modified_by, sub_description)
VALUES (59, 'month2', 57, 'frequency', 2, true, '월 평균 2회', '2024-01-10', 'admin', NULL, NULL, NULL),
       (60, 'month3', 57, 'frequency', 3, true, '월 평균 3회', '2024-01-10', 'admin', NULL, NULL, NULL),
       (61, 'month4', 57, 'frequency', 4, true, '월 평균 4회', '2024-01-10', 'admin', NULL, NULL, NULL),
       (62, 'cost', 0, NULL, 13, true, '지출비용', '2024-01-10', 'admin', NULL, NULL, NULL),
       (63, '1to3', 62, 'cost', 1, true, '월 평균 1~3만원', '2024-01-10', 'admin', NULL, NULL, NULL),
       (64, '4to6', 62, 'cost', 2, true, '월 평균 4~6만원', '2024-01-10', 'admin', NULL, NULL, NULL),
       (44, 'outercare', 42, 'interest', 1, true, '도장', '2023-12-28', 'admin', NULL, NULL, NULL),
       (45, 'tire', 42, 'interest', 2, true, '타이어', '2023-12-28', 'admin', NULL, NULL, NULL),
       (46, 'wheel', 42, 'interest', 3, true, '휠', '2023-12-28', 'admin', NULL, NULL, NULL),
       (47, 'innercare', 42, 'interest', 4, true, '실내', '2023-12-28', 'admin', NULL, NULL, NULL);


INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at,
                                created_by, modified_at, modified_by, sub_description)
VALUES (2, 'hyundai', 1, 'carbrand', 1, false, '현대', '2023-12-28', 'admin', NULL, NULL, NULL),
       (3, 'kia', 1, 'carbrand', 2, false, '기아', '2023-12-28', 'admin', NULL, NULL, NULL),
       (4, 'renault', 1, 'carbrand', 3, false, '르노', '2023-12-28', 'admin', NULL, NULL, NULL),
       (5, 'kg mobility', 1, 'carbrand', 4, false, 'KG모빌리티', '2023-12-28', 'admin', NULL, NULL, NULL),
       (5, 'chevrolet', 1, 'carbrand', 5, false, '쉐보레', '2023-12-28', 'admin', NULL, NULL, NULL),
       (6, 'benz', 1, 'carbrand', 6, false, '벤츠', '2023-12-28', 'admin', NULL, NULL, NULL),
       (7, 'bmw', 1, 'carbrand', 7, false, '벰베', '2023-12-28', 'admin', NULL, NULL, NULL),
       (8, 'audi', 1, 'carbrand', 8, false, '아우디', '2023-12-28', 'admin', NULL, NULL, NULL),
       (9, 'etc', 1, 'carbrand', 9, false, '기타', '2023-12-28', 'admin', NULL, NULL, NULL),
       (11, 'sedan', 10, 'cartype', 1, true, '세단', '2023-12-28', 'admin', NULL, NULL, NULL);


INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at,
                                created_by, modified_at, modified_by, sub_description)
VALUES (12, 'hatchback', 10, 'cartype', 2, true, '해치백', '2023-12-28', 'admin', NULL, NULL, NULL),
       (13, 'suv', 10, 'cartype', 3, true, 'SUV', '2023-12-28', 'admin', NULL, NULL, NULL),
       (14, 'etc', 10, 'cartype', 4, true, '기타', '2023-12-28', 'admin', NULL, NULL, NULL),
       (16, 'micro', 15, 'segment', 1, true, '경차', '2023-12-28', 'admin', NULL, NULL, NULL),
       (17, 'subcompact', 15, 'segment', 2, true, '소형', '2023-12-28', 'admin', NULL, NULL, NULL),
       (18, 'compact', 15, 'segment', 3, true, '중형', '2023-12-28', 'admin', NULL, NULL, NULL),
       (65, '7to9', 62, 'cost', 3, true, '월 평균 7~9만원', '2024-01-10', 'admin', NULL, NULL, NULL),
       (66, '10over', 62, 'cost', 4, true, '월 평균 10만원 이상', '2024-01-10', 'admin', NULL, NULL, NULL),
       (19, 'fullsize', 15, 'segment', 4, true, '대형', '2023-12-28', 'admin', NULL, NULL, NULL),
       (21, 'white', 20, 'color', 1, true, '흰색', '2023-12-28', 'admin', NULL, NULL, 'FFFFFF');


INSERT INTO public.common_code (code_no, code_name, upper_no, upper_name, sort_order, is_used, description, created_at,
                                created_by, modified_at, modified_by, sub_description)
VALUES (22, 'gray', 20, 'color', 5, true, '쥐색', '2023-12-28', 'admin', NULL, NULL, '808080'),
       (23, 'black', 20, 'color', 6, true, '검정색', '2023-12-28', 'admin', NULL, NULL, '37383C'),
       (24, 'red', 20, 'color', 4, true, '빨간색', '2023-12-28', 'admin', NULL, NULL, 'FF4500'),
       (25, 'yellow', 20, 'color', 2, true, '노란색', '2023-12-28', 'admin', NULL, NULL, 'FFD400'),
       (26, 'green', 20, 'color', 3, true, '초록색', '2023-12-28', 'admin', NULL, NULL, '2F4F4F'),
       (27, 'blue', 20, 'color', 4, true, '파란색', '2023-12-28', 'admin', NULL, NULL, '145B7D'),
       (28, 'etc', 20, 'color', 7, true, '기타', '2023-12-28', 'admin', NULL, NULL, '6E0000');



-- Insert Into brand

Insert Into brand (brand_no, brand_name, company_name, nation_name, created_at, created_by)
VALUES (1, '더클래스', '코스메디슨(CosMedicine)', '대한민국', current_date, 'admin'),
       (51, '크리스탈 코트', '(주) 불스원', '대한민국', current_date, 'admin'),
       (101, '불스원', '(주) 불스원', '대한민국', current_date, 'admin'),
       (151, '글로스브로', '주식회사 제이씨웍스', '대한민국', current_date, 'admin'),
       (201, '파이어볼', '(주)파이어볼', '대한민국', current_date, 'admin'),
       (251, '티에이씨시스템', '티에이씨시스템', '대한민국', current_date, 'admin'),
       (301, '기온테크놀로지', '주식회사 기온테크놀로지(Gyeon Technology)', '대한민국', current_date, 'admin'),
       (351, '루나틱폴리시', '(주) 불스원', '대한민국', current_date, 'admin'),
       (401, '젠틀맨', '주식회사 에스피', '대한민국', current_date, 'admin'),
       (451, '디아만테', '현일', '대한민국', current_date, 'admin'),
       (501, '(주)오토왁스', '(주)오토왁스', '대한민국', current_date, 'admin'),
       (551, '라보코스메디카', '주식회사 대흥아이앤씨(INC)', '이탈리아', current_date, 'admin'),
       (601, '마프라', '주식회사 대흥아이앤씨(INC)', '이탈리아', current_date, 'admin'),
       (651, '매니악', '주식회사 대흥아이앤씨(INC)', '이탈리아', current_date, 'admin'),
       (701, 'EXQ', '이엑스큐(EXQ)', '대한민국', current_date, 'admin'),
       (751, 'EXQ', '이엑스큐(EXQ)', '중국', current_date, 'admin'),
       (801, '터틀왁스', '(주)에스씨에이', '미국', current_date, 'admin'),
       (851, '잭스왁스', '주식회사 잭스왁스코리아', '미국', current_date, 'admin'),
       (901, '오토브라이트', '주식회사 오토브라이트다이렉트코리아(AUTOBRITE DIRECT KOREA CO.,LTD)', '영국', current_date, 'admin'),
       (951, '블루믹스', '제이웍스 (J-works)', '대한민국', current_date, 'admin'),
       (1001, '림피오', '림피오(LIMPIO)', '대한민국', current_date, 'admin'),
       (1051, '좀비', '한국씨앤에스', '대한민국', current_date, 'admin'),
       (1101, '카프로', '(주)카프로코리아', '대한민국', current_date, 'admin'),
       (1151, '이지카케어', '발스코리아(주)', '영국', current_date, 'admin'),
       (1201, '니그린', '(주)다스모터스', '영국', current_date, 'admin'),
       (1251, '도도쥬스', '주식회사투왁스', '영국', current_date, 'admin'),
--        (1301, '루미너스', '주식회사 엘엠', '대한민국', current_date, 'admin'),
       (1351, '소낙스', '(주)알레스', '독일', current_date, 'admin'),
       (1401, '스마트왁스', '에이큐에이 주식회사', '미국', current_date, 'admin'),
       (1451, '케미컬가이', '에이큐에이 주식회사', '미국', current_date, 'admin'),
       (1501, '리바이브', '주식회사 에스피', '영국', current_date, 'admin'),
       (1551, '보닉스', '대흥아이앤씨', '브라질', current_date, 'admin'),
       --        (1601, '더클래스', '더클래스', '대한민국', current_date, 'admin'),
       (1651, '루미너스', '루미너스코리아', '대한민국', current_date, 'admin');