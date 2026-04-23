# Heart Bloom BE 구현 가이드

이 문서는 '부케 받는 사람' 기능 및 '로그인/비로그인 하이브리드 저장' 시스템 구현을 위한 단계별 계획을 담고 있습니다.

## 구현 계획

### [1단계] 데이터베이스 및 도메인 모델 고도화 (Core & Infra)
- [x] **1. BouquetReceiver 확장**
    - core의 BouquetReceiver 도메인과 infra의 BouquetReceiverEntity에 userId (Nullable) 필드를 추가합니다.
- [x] **2. BouquetLink 상태 관리**
    - `BouquetLinkStatus` 에 `OPEN`, `COMPLETED` 상태를 정의하여 한 번 답변된 링크는 재사용할 수 없게 방지합니다.
- [x] **3. Flyway 마이그레이션**
    - `V2__add_user_id_to_bouquet_receiver.sql` 파일을 생성하여 DB 스키마를 업데이트했습니다.

### [2단계] Infrastructure 저장소 구현 (Infra)
- [x] **1. BouquetReceiverRepository 구현**
    - core에 인터페이스를 정의하고, infra에서 JPA를 이용해 구현했습니다. (Dao 패턴 적용)
- [x] **2. BouquetLink 조회 쿼리**
    - linkToken을 통해 Bouquet 정보를 가져오는 쿼리를 작성했습니다. (RepositoryImpl 구현 완료)

### [3단계] 핵심 비즈니스 로직 구현 (App - Manager/Service)
- [x] **1. BouquetReceiverManager 신설**
    - 받는 사람 정보 생성 및 userId 업데이트(연결) 로직을 담당합니다.
- [x] **2. BouquetService 기능 확장**
    - `getBouquetForReceiver(token)`: 수신자용 부케 정보(보내는 사람 이름, 부케 타입, 질문-답변) 조회 로직 구현.
    - `completeBouquet(token, answers, user)`: 수신자 답변 저장 및 링크 상태 COMPLETED 처리.
    - `claimBouquet(token, user)`: 비로그인 답변을 회원 계정에 소유권 이전 로직 구현.

### [4단계] API 엔드포인트 및 보안 설정 (App - API)
- [x] **1. BouquetReceiverController 생성**
    - GET `/api/v1/bouquets/links/{token}`: 부케 정보 및 질문 조회 (비로그인 허용)
    - POST `/api/v1/bouquets/links/{token}/answers`: 답변 제출 및 완성 (비로그인 허용, 로그인 시 자동 연결)
    - POST `/api/v1/bouquets/links/{token}/claim`: 소유권 연결 (로그인 필수)
- [x] **2. Security Config 수정**
    - 모든 요청이 `permitAll()`로 설정되어 있어 비로그인 접근이 가능함을 확인했습니다.
    - `BouquetService`에서 `AnonymousUser`를 체크하여 예외 없이 안전하게 처리되도록 보완했습니다.
