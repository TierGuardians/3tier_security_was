# 💸 TierGuardians Finances - WAS

TierGuardians Finances는 사용자의 자산, 예산, 소비 내역을 효율적으로 관리할 수 있는 백엔드 API입니다.  
Spring Boot 기반 RESTful API로 구성되어 있으며, JWT 인증과 Spring Security를 통해 보안을 강화하였습니다.

---

## 🏗️ 기술 스택

| 구성 요소      | 기술 스택                                      |
|----------------|-----------------------------------------------|
| Backend        | Spring Boot 3.3.1, Spring Security, JWT       |
| Build Tool     | Gradle                                         |
| DB             | MySQL (운영), H2 (개발/테스트)                 |
| ORM            | Spring Data JPA                                |
| 문서화         | Swagger (springdoc-openapi 2.2.0)             |
| 인증 방식      | JWT (Access Token + Refresh Token)             |
| 배포 환경      | Ubuntu (VM), Nginx, Java 17                    |

---

## 🔐 인증 및 보안

### 🔑 JWT 기반 인증

- Access Token: 15분 유효
- Refresh Token: 7일 유효
- 로그인 시 두 개의 토큰 발급
- Refresh Token은 DB에 저장하여 재발급 요청 시 검증

### 🔐 Spring Security 적용

- `/users/login`, `/users/signup`, `/token/refresh` 엔드포인트는 비인증 허용
- 나머지 모든 요청은 JWT 인증 필요
- 인증 필터: `JwtAuthenticationFilter`

---

## 🗃️ 데이터베이스 테이블 구조 요약

| 테이블명   | 설명             |
|------------|------------------|
| `users`    | 사용자 정보 저장 (비밀번호는 BCrypt로 암호화) |
| `assets`   | 자산 정보        |
| `budgets`  | 예산 정보        |
| `expenses` | 소비 내역        |

> ✅ Refresh Token은 `users` 테이블의 `refresh_token` 컬럼에 저장됩니다.

---

## 📂 API 문서 (Swagger)

Swagger UI를 통해 전체 API 명세를 확인할 수 있습니다.

- 주소: `http://localhost:8080/swagger-ui/index.html`
- JWT 인증이 필요한 API 테스트를 위해 "Authorize" 버튼 사용 가능

---

## 🧱 시스템 아키텍처

```plaintext
Client (React)
   |
   |  HTTP 요청 (JWT 포함)
   v
[ Nginx (Reverse Proxy) ]
   |
   v
[ Spring Boot (REST API) ]
   |
   |  JPA / Hibernate
   v
[ MySQL DB (Remote) ]
```

---

## 🚀 실행 방법

- 환경 설정 : properties

```
# application.properties

jwt.secret-key=your-secret-key
spring.datasource.url=jdbc:mysql://{your-host}:{port}/your-db
spring.datasource.username=your-user
spring.datasource.password=your-password
```
