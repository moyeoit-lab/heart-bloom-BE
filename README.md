# 마음꽃집 백엔드 프로젝트

## 코드 아키텍쳐

```text
heart-bloom-BE
|-- app     # 애플리케이션 진입점 및 설정
|-- core    # 도메인 모델 및 저장소 인터페이스 (비즈니스 로직 추상화)
|-- infra   # JPA 엔티티, DB 설정, Flyway 마이그레이션 (데이터 접근 계층)
|-- common  # 공통 유틸리티 (예정)
```

---

## 로컬 개발 환경 설정

**1. DB 실행**

```bash
docker compose up -d
```

**2. 앱 실행**

```bash
./gradlew :app:bootRun
```
