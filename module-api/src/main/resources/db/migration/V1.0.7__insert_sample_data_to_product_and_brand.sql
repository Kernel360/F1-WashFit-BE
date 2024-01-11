Insert Into brand (brand_no, brand_name, nation_name, created_at, created_by)
VALUES (1, '더클래스', '대한민국', current_date, 'admin'),
       (51, '오가닉K', '대한민국', current_date, 'admin'),
       (101, '(주)제일', '대한민국', current_date, 'admin'),
       (151, '루나틱폴리시', '대한민국', current_date, 'admin'),
       (201, '불스원', '대한민국', current_date, 'admin'),
       (251, '도깨비', '대한민국', current_date, 'admin'),
       (301, '(주) 에스엠테크', '대한민국', current_date, 'admin'),
       (351, 'Quantum', '대한민국', current_date, 'admin'),
       (401, '림피오', '대한민국', current_date, 'admin'),
       (451, '(주)월드그린', '대한민국', current_date, 'admin'),
       (501, '루미너스', '대한민국', current_date, 'admin'),
       (551, '광동케미칼', '대한민국', current_date, 'admin'),
       (601, '주식회사 오월', '대한민국', current_date, 'admin'),
       (651, '주식회사 다온벤카코리아', '대한민국', current_date, 'admin'),
       (701, '엠케이코리아(오토가디언)', '대한민국', current_date, 'admin'),
       (751, '젠틀맨', '대한민국', current_date, 'admin'),
       (801, '디아만테', '대한민국', current_date, 'admin'),
       (851, '청원상사', '대한민국', current_date, 'admin'),
       (901, '(주)오토왁스', '대한민국', current_date, 'admin');

INSERT INTO product (product_name, brand_no, declare_no, is_violation, view_count, created_by, created_at)
VALUES ('더클래스 불렛 피닉스 (Bullet Phoenix)', 1, 'HB21-07-1241', false, 0, 'admin', current_date),
       ('더클래스 차량 외부용 왁스부스터', 1, 'DB24-06-0002', false, 0, 'admin', current_date),
       ('인퓨전스 드레싱제', 1, 'EB22-06-0252', false, 0, 'admin', current_date),

       ('바마드 퍼포먼스 차량용 발수 유리막 코팅제 건식사용', 51, 'CB23-06-0181', false, 0, 'admin', current_date),

       ('레자왁스(+코팅)', 101, 'GB24-06-0001', false, 0, 'admin', current_date),
       ('차량용 내부 원터치 살균제', 101, 'DB23-21-0063', false, 0, 'admin', current_date),
       ('다용도세정제(+멀티크리너)', 101, 'GB24-01-0003', false, 0, 'admin', current_date),
       ('휠클리너(+철분제거)', 101, 'GB24-01-0002', false, 0, 'admin', current_date),

       ('루나틱폴리시 하이브리드 고체왁스', 151, 'CB24-06-0003', false, 0, 'admin', current_date),

       ('크리스탈 타이어 매트 드레싱', 201, 'CB19-06-0109', false, 0, 'admin', current_date),
       ('크리스탈 타이어코트 스프레이', 201, 'CB21-06-0917', false, 0, 'admin', current_date),

       ('도깨비 디(D)01 페인트 클렌져', 251, 'FB21-02-0365', false, 0, 'admin', current_date),

       ('에탄올 워셔액 에탄파워', 301, 'DA21-16-0017', false, 0, 'admin', current_date),

       ('퀀텀 울트라 뷰 에탄올 워셔액', 351, 'DA21-16-0005', false, 0, 'admin', current_date),

       ('림피오 타이어 드레싱 (Limpio Tire Dressing)', 401, 'FA21-06-0826', false, 0, 'admin', current_date),

       ('타이어 광택제', 451, 'CB20-06-0372', false, 0, 'admin', current_date),

       ('에스프리워시', 501, 'GB24-01-0006', false, 0, 'admin', current_date),
       ('루멘 카샴푸', 501, 'GB23-01-0089', false, 0, 'admin', current_date),

       ('생활미소 상큼향 형광 카샴푸', 551, 'FB22-01-0808', false, 0, 'admin', current_date),
       ('생활미소 달콤향 보라 카샴푸', 551, 'FB22-01-0811', false, 0, 'admin', current_date),

       ('대용량 중성 카샴푸 5L', 601, 'EB23-01-1012', false, 0, 'admin', current_date),

       ('뿌리는카샴푸', 651, 'DB21-01-1159', false, 0, 'admin', current_date),

       ('고농축프리미엄카샴푸', 701, 'DB23-01-0422', false, 0, 'admin', current_date),

       ('젠틀맨 유리세정제', 751, 'GB23-01-0957', false, 0, 'admin', current_date),
       ('젠틀맨 철분제거제', 751, 'GB23-01-0899', false, 0, 'admin', current_date),

       ('디아만테 유막(클리닝에이전트)', 801, 'HB20-01-0775', false, 0, 'admin', current_date),
       ('디아만테 아이언(클리닝에이전트)', 801, 'HB20-01-0775', false, 0, 'admin', current_date),
       ('디아만테 버블폼(클리닝에이전트)', 801, 'HB20-01-0775', false, 0, 'admin', current_date),
       ('디아만테 고농축스노우폼 (클리닝에이전트)', 801, 'HB20-01-0775', false, 0, 'admin', current_date),
       ('디아만테 탈지제 (클리닝에이전트)', 801, 'HB20-01-0775', false, 0, 'admin', current_date),

       ('청원상사 컴파운드 광택코팅제', 851, 'HB23-06-0089', false, 0, 'admin', current_date),

       ('트림 앤 모터 코트', 901, 'HB21-06-0743', false, 0, 'admin', current_date),
       ('카나우바 스프리츠', 901, 'HB21-06-0765', false, 0, 'admin', current_date),
       ('패스트 왁스', 901, 'HB21-06-0763', false, 0, 'admin', current_date),
       ('그래핀 딥글로스 세라믹 실런트', 901, 'HB21-06-0766', false, 0, 'admin', current_date),
       ('글라이드', 901, 'HB21-06-0765', false, 0, 'admin', current_date),
       ('파이널 폴리쉬', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('울티마 페인트 실런트', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('프리왁스 클린져', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('세라믹 휠 실런트', 901, 'HB21-06-0766', false, 0, 'admin', current_date),
       ('원스텝 세라믹 폴리쉬', 901, 'HB21-06-0766', false, 0, 'admin', current_date),
       ('클레이 루브리컨트', 901, 'HB21-06-0765', false, 0, 'admin', current_date),
       ('타이어앤트림 프로텍턴트 젤', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('스월버스터 폴리쉬', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('스프레이 왁스 플러스', 901, 'HB21-06-0765', false, 0, 'admin', current_date),
       ('아크릴릭 스프레이 왁스', 901, 'HB21-06-0765', false, 0, 'admin', current_date),
       ('리스토어 폴리쉬', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('페인트웍 클린져', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('얼티메이트 페인트 프로텍션', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('크롬 폴리쉬', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('알루미늄 폴리쉬', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('이온 페인트 실런트', 901, 'HB21-06-0757', false, 0, 'admin', current_date),
       ('알루미늄 실런트', 901, 'HB21-06-0757', false, 0, 'admin', current_date);