# OAuth2 Service

OAuth2 Service Spring Boot 기반 API입니다. OAuth2 소셜 로그인 및 Swagger UI를 지원합니다.

## 폴더 구조
<pre>
src/main/java  
└── com.example.studymatching_api  
    ├── config  
    │   ├── SecurityConfig         # Spring Security 보안 설정 클래스  
    │   └── SwaggerConfig          # Swagger 설정 클래스  
    │  
    ├── controller  
    │   └── HomeController         # 기본 페이지 라우팅 컨트롤러  
    │  
    ├── domain  
    │   ├── kakao  
    │   │   └── KakaoUserInfo      # Kakao 사용자 정보 처리  
    │   │  
    │   ├── naver  
    │   │   ├── NaverUserInfo      # Naver 사용자 정보 처리  
    │   │   ├── User               # 사용자 엔티티 클래스  
    │   │   └── UserRepository     # 사용자 저장소 인터페이스  
    │   │  
    │   └── oauth  
    │       ├── CustomOAuth2UserService  # OAuth2 사용자 서비스 구현  
    │       ├── OAuth2UserInfo           # 공통 OAuth 사용자 정보 처리 인터페이스  
    │       ├── OAuthAttributes          # OAuth 응답 속성 매핑 클래스  
    │       ├── OAuthController          # OAuth 관련 API 컨트롤러  
    │       └── SessionUser              # 세션 사용자 정보 저장 클래스  
    │  
    └── StudymatchingApiApplication      # Spring Boot 애플리케이션 시작점  
</pre>

  
</li>






## 주요 기능

### OAuth2 로그인
- Kakao 및 Naver를 통한 소셜 로그인 지원.
- 사용자 정보를 엔티티로 매핑 후 데이터베이스에 저장.

### API
- **HomeController**: 기본 페이지 라우팅.
- **OAuthController**: OAuth 관련 API 제공.

### Swagger UI
- `/swagger-ui`를 통해 API 명세 확인 가능.

### 보안
- Spring Security 설정을 통한 인증 및 권한 관리.

## 사용된 기술 스택
- **백엔드**: Java 17, Spring Boot 3.0, Spring Security, Spring Data JPA
- **뷰**: Thymeleaf
- **API 문서화**: Swagger
- **OAuth2**: Kakao, Naver
- **빌드 도구**: Gradle

## 설치 및 실행

### 프로젝트 클론
```bash
git clone <repository_url>
cd studymatching_api
