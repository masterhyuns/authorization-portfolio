```shell

📁 src/main/java/com/yourcompany/oauthserver

├── 📁 auth
│   ├── 📁 controller
│   │   ├── AuthorizationController.java       // /authorize
│   │   ├── TokenController.java              // /token, /revoke
│   ├── 📁 service
│   │   ├── AuthorizationService.java         // 코드 발급, 검증, 저장
│   │   ├── TokenService.java                 // access/refresh 토큰 발급
│   ├── 📁 util
│   │   └── JwtUtil.java                      // JWT 서명/검증 유틸
│   ├── 📁 model
│   │   ├── AuthCode.java
│   │   ├── Token.java
│   └── 📁 repository
│       ├── AuthCodeRepository.java
│       ├── TokenRepository.java

├── 📁 resource
│   ├── 📁 controller
│   │   └── MeController.java                 // /me endpoint
│   ├── 📁 service
│   │   └── UserService.java                  // 사용자 정보 조회

├── 📁 user
│   ├── 📁 model
│   │   └── User.java
│   ├── 📁 repository
│   │   └── UserRepository.java
│   └── 📁 service
│       └── UserService.java

├── 📁 client
│   ├── 📁 model
│   │   └── Client.java
│   ├── 📁 repository
│   │   └── ClientRepository.java
│   └── 📁 service
│       └── ClientService.java

📄 Application.java


```
