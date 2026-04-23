# Heart Bloom BE 구현 가이드

이 문서는 '부케 받는 사람' 기능 및 '로그인/비로그인 하이브리드 저장' 시스템 구현을 위한 단계별 계획을 담고 있습니다.

## 구현 계획

### [1단계] 데이터베이스 및 도메인 모델 고도화 (Core & Infra)
- [x] **1. BouquetReceiver 확장**
    - core의 BouquetReceiver 도메인과 infra의 BouquetReceiverEntity에 userId (Nullable) 필드를 추가합니다.
- [ ] **2. BouquetLink 상태 관리**
    - `BouquetLinkStatus` 에 `OPEN`, `COMPLETED` 상태를 정의하여 한 번 답변된 링크는 재사용할 수 없게 방지합니다.
- [x] **3. Flyway 마이그레이션 (진행 중)**
    - `V2__add_user_id_to_bouquet_receiver.sql` 파일을 생성하여 DB 스키마를 업데이트합니다. (상태값 주석 업데이트 포함)

### [2단계] Infrastructure 저장소 구현 (Infra)
- [x] **1. BouquetReceiverRepository 구현**
    - core에 인터페이스를 정의하고, infra에서 JPA를 이용해 구현합니다.
- [x] **2. BouquetLink 조회 쿼리**
    - linkToken을 통해 Bouquet 정보를 가져오는 쿼리를 작성합니다. (RepositoryImpl 구현 완료)

### [3단계] 핵심 비즈니스 로직 구현 (App - Manager/Service)
- [ ] **1. BouquetReceiverManager 신설**
    - 받는 사람 정보 생성 및 userId 업데이트(연결) 로직을 담당합니다.
- [ ] **2. BouquetService 기능 확장**
    - `getBouquetForReceiver(token)`: 받는 사람이 링크를 클릭했을 때, 보내는 사람의 이름/부케타입/질문-답변 리스트를 반환합니다.
    - `completeBouquet(token, answers, user)`:
        - 전달받은 답변을 BouquetAnswer(RECEIVER 타입)로 저장합니다.
        - user가 존재하면 BouquetReceiver에 userId를 즉시 할당합니다.
        - BouquetLink 상태를 COMPLETED로 변경합니다.
    - `claimBouquet(token, user)`:
        - 비로그인 상태에서 답변을 마친 유저가 로그인 후 호출하는 API입니다.
        - 해당 토큰의 BouquetReceiver에 현재 userId를 연결합니다.

### [4단계] API 엔드포인트 및 보안 설정 (App - API)
- [ ] **1. BouquetReceiverController 생성**
    - GET `/api/v1/bouquets/links/{token}`: 부케 정보 및 질문 조회 (비로그인 허용)
    - POST `/api/v1/bouquets/links/{token}/answers`: 답변 제출 및 완성 (비로그인 허용, JWT 선택적 수용)
    - POST `/api/v1/bouquets/links/{token}/claim`: 소유권 연결 (로그인 필수)
- [ ] **2. Security Config 수정**
    - 해당 링크 관련 API들을 `permitAll()`에 추가합니다.
    - AccessUser 인자가 JWT가 없어도 null로 들어올 수 있도록 ArgumentResolver 설정을 확인합니다.
