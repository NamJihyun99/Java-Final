# E-Commerce Java Application

## 기능 명세 체크리스트

### 사용자 관리
- [x] 회원 가입 : 이름, 이메일, 비밀번호, 전화번호 (상태와 구분은 자동 지정)
- [x] 로그인 : 이메일, 비밀번호
- [x] 회원 정보 조회 : 이름, 이메일, 전화번호
- [x] 회원 정보 수정 : 이름, 전화번호 / 비밀번호 (별도 확인 절차 추가 - 자동 로그아웃)
- [x] 로그아웃 (메인 화면으로 이동)
- [x] 회원탈퇴 (Status 마킹)

### 상품 관리
- [x] 상품 등록
- [x] 상품 수정 (이름, 설명, 가격)
- [x] 재고 업데이트 / 품절 처리
- [x] 상품 삭제
- [x] 상품 목록 조회 (관리자 재고 관리용)

### 상품 전시
- [x] 상품 목록 조회 (가격순 내림차순 정렬)
- [x] 상품 상세 조회 (상품명, 설명, 가격)
- [x] 상품명 검색

### 장바구니 구현
- [x] 나의 장바구니 목록 조회 (품목별 ID, 상품명, 가격, 개수, 총액 + 총계)
- [x] 장바구니 담기 (재고 확인 + 동일 품목 존재할 경우 단순 덧셈)
- [x] 장바구니 수량 수정 (재고 확인)
- [x] 장바구니 품목 삭제
- [x] 장바구니 비우기

## 메뉴 구조
```markdown
📦 메인 메뉴
├── 1. 회원 가입
├── 2. 로그인
│   └── 🔓 로그인 성공 시 → 회원 메뉴
├── 3. 상품 목록 (비회원용)
└── 4. 종료

📁 회원 메뉴
├── 1. 상품 목록
│   └── 상품 상세 조회 선택
│       └── 장바구니 담기 여부 확인
├── 2. 상품명 검색
│   └── 검색 결과
│       └── 상품 상세 조회 선택
│           └── 장바구니 담기 여부 확인
├── 3. 장바구니 조회
│   ├── 1. 품목 수량 수정
│   ├── 2. 품목 삭제
│   └── 3. 장바구니 비우기
├── 4. 회원 정보 조회
├── 5. 회원 정보 수정
│   ├── 1. 비밀번호 변경
│   └── 2. 이름 및 전화번호 수정
├── 6. 로그아웃 성공 시 → 메인 메뉴
├── 7. 회원 탈퇴
└── 8. 관리자 메뉴 (⚙️ 관리자만 접근 가능)

🔧 관리자 메뉴
├── 1. 상품 등록
├── 2. 상품 정보 수정
├── 3. 상품 삭제
├── 4. 재고 관리
└── 5. 뒤로 가기 → 회원 메뉴
```

## 프로젝트 구조
```markdown
📦 Java-Final
├── 📁 common
│   ├── AppConfig.java         # 의존성 주입 설정
│   ├── DBConnectionUtil.java  # DB 연결 유틸리티
│   ├── LoginSession.java      # 로그인 세션 관리
│   └── ReaderUtil.java        # 입력 처리 유틸
│
├── 📁 controller              # 메뉴 흐름 및 상위 컨트롤러
│   ├── AdminHandler.java
│   ├── BasketHandler.java
│   ├── MainController.java    # 메인 메뉴 컨트롤러
│   ├── MemberController.java  # 회원 메뉴 컨트롤러
│   └── UserHandler.java
│
├── 📁 product
│   ├── 📁 dao
│   │   ├── ProductDao.java
│   │   └── OracleProductDao.java
│   ├── 📁 domain
│   │   └── Product.java
│   ├── 📁 dto
│   │   ├── ProductInfoResponse.java
│   │   ├── ProductsAdminResponse.java
│   │   ├── ProductSaveRequest.java
│   │   ├── ProductsResponse.java
│   │   └── ProductUpdateRequest.java
│   └── 📁 service
│       └── ProductService.java
│
├── 📁 user
│   ├── 📁 dao
│   │   ├── UserDao.java
│   │   ├── OracleUserDao.java
│   │   ├── BasketDao.java
│   │   ├── OracleBasketDao.java
│   │   ├── BasketItemDao.java
│   │   └── OracleBasketItemDao.java
│   ├── 📁 domain
│   │   ├── User.java
│   │   ├── Basket.java
│   │   ├── BasketItem.java
│   │   ├── Role.java
│   │   └── Status.java
│   ├── 📁 dto
│   │   ├── LoginRequest.java
│   │   ├── UserSaveRequest.java
│   │   ├── UserUpdateRequest.java
│   │   ├── PasswordUpdateRequest.java
│   │   ├── UserInfoResponse.java
│   │   ├── BasketResponse.java
│   │   ├── BasketItemResponse.java
│   │   ├── BasketItemSaveRequest.java
│   │   └── BasketItemUpdateRequest.java
│   └── 📁 service
│       ├── UserService.java
│       └── BasketService.java
│
├── 📁 view                    # CLI View 클래스
│   ├── MainView.java
│   └── MemberView.java
│
├── 📁 lib                     # 외부 라이브러리 (Oracle JDBC 등)
│   ├── ojdbc11-23.7.0.25.01.jar
│   └── oraclepki-23.7.0.25.01.jar
│
├── 📁 resources               # 설정 파일 등 리소스
│   └── application.properties
│
├── Main.java                 # 실행 진입점
└── README.md                 # 프로젝트 설명

```

