# 🚘 WashFit
<p align="center"><img width="1000" height="150" alt="image" src="https://github.com/Kernel360/F1-WashFit-BE/assets/91066575/43b831e5-8608-490c-a8a6-a1c6802b2024" style="width: 100%"></p>


## 💧 서비스 소개 
    세차용품 안전정보 제공 및 검증된 세차 관련 정보를 보여주는 플랫폼
<p align="center"><img src="https://github.com/Kernel360/F1-WashFit-BE/assets/91066575/06a42af8-4b98-4c1a-b7ef-15e8a3c578cd" style="width: 60%" ></p>

## 💧 서비스 대상
    - 바이럴 마케팅, 제품 제공 후기가 아닌 솔직한 리뷰를 확인하고 싶은 소비자
    - 자신의 생각, 의견에 대해 자유롭게 의사소통을 하고 싶은 사람
    - 세차 용품에 대해 자세히 알고싶은 사람
    - 그 외 세차를 취미로 즐기거나 관심있는 누구나

## 💧 사이트 URL
    https://www.washfit.site


## 💧 주요 기능 
    회원 :: 가입/SNS 로그인, 마이페이지
    제품 :: 제품 안전정보 검색/조회
    기타 :: 세차장 지도, 세차 유튜브


## 💧 주요 기능 시연 
<p align="center">
    <h4>1. 제품 정렬 & 조회</h4>
    <img src="https://github.com/Kernel360/F1-WashFit-BE/assets/91066575/8fc68753-8724-4fcd-aca5-b95c7d2c6fb8" width="500px"/>
</p><br>
<p align="center">
    <h4>2. 제품 검색</h4>
    <img src="https://github.com/Kernel360/F1-WashFit-BE/assets/91066575/8063a669-43de-4ba4-9ec1-9db5e9e628ad" width="500px"/>
</p><br>
<p align="center">
    <h4>3. 로그인 & 내 정보 등록</h4>
    <img src="https://github.com/Kernel360/F1-WashFit-BE/assets/91066575/282ab0c0-d0f3-43c2-9dd6-97e8bcc9d07e" width="500px"/>
</p><br>
<p align="center">
    <h4>4. 마이페이지</h4>
    <img src="https://github.com/Kernel360/F1-WashFit-BE/assets/91066575/aaa3dd18-2155-41dd-854e-5d0dfc1202d2" width="500px"/>
</p><br>
<p align="center">
    <h4>5. 세차장 지도</h4>
    <img src="https://github.com/Kernel360/F1-WashFit-BE/assets/91066575/9781cbf1-0ec2-4ec8-9540-b162d0c34140" width="500px"/>
</p><br>
<p align="center">
    <h4>6. 세차 유튜브</h4>
    <img src="https://github.com/Kernel360/F1-WashFit-BE/assets/91066575/96353b1e-113c-4422-9a0c-4749106c4af9" width="500px"/>
</p><br>


## 📗 기능 작성시 상세한 기획/정책/규칙 작성
    회원가입/로그인/로그아웃 :: 
    - JWT 토큰과 해시 알고리즘을 사용하여 구현. Spring Security 사용 X

    제품 정보 요청 :: 
    - 초록누리 API 위해제품목록 요청 
    - 목록 정보를 바탕으로 단일 제품 상세 정보 요청
    - 상세정보를 서비스에 사용하는 Product 테이블에 추가

    
## 🖥️ 주요 기술 스택
<div>
    <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white">
    <img src="https://img.shields.io/badge/Spring%20MVC-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
    <img src="https://img.shields.io/badge/Spring%20Batch-6DB33F?style=for-the-badge&logo=spring&logoColor=white"><br>
    <img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
    <img src="https://img.shields.io/badge/Spring%20REST%20Docs-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
    <img src="https://img.shields.io/badge/Querydsl-0?style=for-the-badge&logo=Querydsl&logoColor=white&color=%23FF69B4">
    <img src="https://img.shields.io/badge/Fixture%20Monkey-0?style=for-the-badge&logo=Fixture%20Monkey&logoColor=white&color=%2385EA2D">
</div>

<div>
    <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white">
    <img src="https://img.shields.io/badge/Redis-D9281A?style=for-the-badge&logo=redis&logoColor=white">
    <img src="https://img.shields.io/badge/JUnit5-0?style=JUnit5-square&logo=junit5&logoColor=white&color=%2325A162">
    <img src="https://img.shields.io/badge/Swagger-0?style=flat-square&logo=Swagger&logoColor=white&color=%2385EA2D">
    <img src="https://img.shields.io/badge/Flyway-0?style=flat-square&logo=flyway&color=%23CC0200"><br><br>
</div>

<div>
    <img src="https://img.shields.io/badge/Vultr-007BFC?style=for-the-badge&logo=vultr&logoColor=white">
    <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">
    <img src="https://img.shields.io/badge/Amazon%20RDS-FF9900?style=for-the-badge&logo=amazon%20rds&logoColor=white">
    <img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=amazon%20s3&logoColor=white">
    <img src="https://img.shields.io/badge/Amazon%20ECR-FF9900?style=for-the-badge&logo=amazon%20ecr&logoColor=white"><br>
    <img src="https://img.shields.io/badge/Github Actions-2088FF?style=for-the-badge&logo=Github Actions&logoColor=white">
    <img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">

</div>
<div>
    <img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">
    <img src="https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">
    <img src="https://img.shields.io/badge/nGrinder-0?style=for-the-badge&logoColor=white&color=grey">
    <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">
</div>
<br>


## 🏛️ 아키텍처
### 프로젝트 구조 (멀티모듈)
<img alt="프로젝트 구조" src="https://github.com/Kernel360/F1-WashFit-BE/assets/88479739/d17e3d56-cae7-4f8c-9a91-df5d49bec80a" width="800px"/>
<br><br>

### 운영 인프라 구조
<img alt="운영 인프라 구조" src="https://github.com/Kernel360/F1-WashFit-BE/assets/88479739/8cf11491-878b-421e-9f88-37cdca7b5c47" width="800px"/>
<br><br>

### 개발 인프라 구조
<img alt="개발 인프라 구조" src="https://github.com/Kernel360/F1-WashFit-BE/assets/88479739/89600c8d-97f4-4fd7-a02d-14ebe316b4e0" width="800px"/>
<br><br>

### ERD
<img alt="ERD" src="https://github.com/Kernel360/F1-WashFit-BE/assets/88479739/3a99eeda-65bd-44ec-8696-a205d16bc36a" width="800px"/>
<br><br>

## 💬 그 외 정보들
    추후 구현해야 하는 기능 :: 
    - 회원탈퇴(탈퇴회원 관리 정책 필요)
    - Admin 페이지
    - (제품 추천 시스템)

## 🙏🏻 팀원 소개
<table>
  <tr>
    <td align="center" width="120px">
      <a href="https://github.com/gunsight1">  
        <img src="https://github.com/Kernel360/blog-image/blob/main/kernel-crew-1/crew-image/%EC%A0%95%EC%A7%80%EC%9A%A9.png?raw=true" alt="정지용 프로필" />
      </a>
    </td>
   <td align="center" width="120px">
      <a href="https://github.com/linglong67">  
        <img src="https://github.com/Kernel360/blog-image/blob/8b5c7975367ed48f7ccd0bd4490003e92f5479f6/kernel-crew-1/crew-image/%EA%B9%80%EC%98%81%EB%A1%B1.png" alt="김영롱 프로필" />
      </a>
    </td>
    <td align="center" width="120px">
      <a href="https://github.com/cgk95">  
        <img src="https://github.com/Kernel360/blog-image/blob/main/kernel-crew-1/crew-image/%EA%B9%80%EC%B0%AC%EA%B7%9C.png?raw=true" alt="김찬규 프로필" />
      </a>
    </td>
    <td align="center" width="120px">
      <a href="https://github.com/HyunJunSon">  
        <img src="https://github.com/Kernel360/blog-image/blob/8b5c7975367ed48f7ccd0bd4490003e92f5479f6/kernel-crew-1/crew-image/%EC%86%90%ED%98%84%EC%A4%80.png" alt="손현준 프로필" />
      </a>
    </td>
  </tr>
</table>
