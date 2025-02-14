# Event Sourcing 기반 도서 대여 시스템

## 개요

- Event Sourcing 개념을 경험하기 위한 **도서 대여 시스템.** 
- Kafka 같은 메시징 시스템 없이 **Spring Boot와 순수한 Event Sourcing**을 활용하여 CQRS, Aggregate, Projection 개념을 직접 구현한다.

---

## 아키텍처 개념

### Event Sourcing

- 모든 상태 변경을 이벤트로 `저장`하고 이를 기반으로 현재 상태를 복원한다.
- 도서의 `대여/반납` 기록을 이벤트 형태로 저장하여 상태를 추적한다.

### CQRS

- Command: 대여 및 반납 요청을 처리하여 이벤트를 생성한다.

- Query: `Projection`을 통해 현재 상태를 유지하고 조회한다.

### Aggregate

- BookAggregate를 사용하여 한 권의 도서에 대한 이벤트를 관리하고, 현재 상태를 결정한다.

### Projection

- 저장된 이벤트를 기반으로 현재 도서 상태를 조회할 수 있도록 별도의 조회 모델 (book_view) 을 유지한다.

---

## 패키지 정리

- `aggregate:` 도서(책)의 상태 관리 (이벤트 적용)

- `event:` 이벤트 정의 (변경 사항 저장)
- `command:` 사용자의 요청 (빌리기, 반납 등)
- `projection:` 조회용 데이터 모델 (현재 상태 유지)

- `repository:` Projection을 위한 JPA 저장소
- `service:` Command를 받아서 이벤트를 생성

- `controller:` HTTP 요청을 받아 Command 실행




---

## API 예시

### 도서 대여 요청 (Command)

```
POST /books/{bookId}/borrow
Content-Type: application/json
{
  "userId": "user123"
}
```

### 현재 도서 상태 조회 (Projection)

```
GET /books/{bookId}
```

응답 예시
```
{
  "bookId": "book123",
  "title": "Event Sourcing Guide",
  "isBorrowed": true
}
```