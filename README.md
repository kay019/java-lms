# 학습 관리 시스템(Learning Management System)

## 진행 방법

- 학습 관리 시스템의 수강신청 요구사항을 파악한다.
- 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
- 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
- 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정

- [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

# 1단계

## 질문 삭제하기 요구사항

- 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
- 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
- 답변이 없는 경우 삭제가 가능하다.
- 질문자와 답변 글의 모든 답변자가 같은 경우 삭제가 가능하다.
- 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
- 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.
- 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.

## 리팩터링 요구사항

- nextstep.qna.service.QnaService의 deleteQuestion()는 앞의 질문 삭제 기능을 구현한 코드이다. 이 메소드는 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드가 섞여 있다.
- QnaService의 deleteQuestion() 메서드에 단위 테스트 가능한 코드(핵심 비지니스 로직)를 도메인 모델 객체에 구현한다.
- QnaService의 비지니스 로직을 도메인 모델로 이동하는 리팩터링을 진행할 때 TDD로 구현한다.
- QnaService의 deleteQuestion() 메서드에 대한 단위 테스트는 src/test/java 폴더 nextstep.qna.service.QnaServiceTest이다. 도메인 모델로 로직을 이동한 후에도 QnaServiceTest의 모든 테스트는 통과해야 한다.

### 🔁 리팩터링 목록 (책임 이동 중심)

- 질문 삭제 가능 여부 판단 로직을 `Question` 도메인으로 이동한다
  - [x] 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
  - [x] 답변이 없는 경우 삭제가 가능하다.
  - [x] 질문자와 답변 글의 모든 답변자가 같은 경우 삭제가 가능하다.
  - [x] 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.
- [x] 질문 삭제 상태 변경 책임을 `Question` 도메인으로 이동한다
- 답변 삭제 책임을 `Answer` 도메인으로 이동한다
  - [x] 답변을 삭제할 때 삭제 상태(deleted)를 변경한다.
  - [x] 질문을 삭제할 때 답변 또한 삭제한다.
- [x] 질문과 답변의 삭제 이력 생성 책임을 도메인 내부로 이동한다

## 🎯 수강 신청 기능 요구사항

### 📘 과정(Course)과 강의(Session) 구조

- [x] 과정은 기수 단위로 운영됩니다. -> nextstep에서는 각 기수가 세션으로 관리됨
- [x] 하나의 과정은 여러 개의 강의(Session)를 포함할 수 있습니다.
- [x] 각 강의는 시작일과 종료일 정보를 가집니다.

---

### 🖼️ 강의 이미지 요구사항

- [x] 모든 강의는 커버 이미지 정보를 가집니다.
- 이미지 제약 조건:
  - [x] 최대 크기: **1MB 이하**
  - [x] 지원 형식: **gif, jpg(jpeg), png, svg**
  - [x] 최소 해상도: **너비 300픽셀, 높이 200픽셀 이상**
  - [x] 비율: **너비:높이 = 3:2**

---

### 💡 강의 유형 및 수강 제한

- [x] 강의는 **무료** 또는 **유료**로 구분됩니다.
- **무료 강의**:
  - [x] 수강 인원 제한 없음
- **유료 강의**:
  - [x] 최대 수강 인원을 초과할 수 없음
  - [x] 수강생 결제 금액과 강의 수강료가 **일치해야** 수강 신청 가능

---

### 🚦 강의 상태 및 수강 신청 조건

- [x] 강의 상태: `준비중`, `모집중`, `종료`
- 수강 신청 가능 조건:
  - [x] 강의 상태가 **모집중**일 때만 가능

---

### 💳 결제 처리

- 유료 강의의 경우 **결제 완료된 상태로 가정**하고 구현
- [x] 결제 정보는 외부 `payments` 모듈에서 관리됩니다.
- [x] 결제 정보는 `Payment` 객체 형태로 제공됩니다.

## 구현 결과

### 객체별 역할

- **Course**: 강의 정보 관리 및 여러 세션 포함
- **Session**: 강의 기수를 나타내는 추상 클래스
- **FreeSession**: 무료로 수강 가능한 세션
- **PaidSession**: 최대 참여자 수와 가격이 설정된 유료 세션
- **Sessions**: Course 내 여러 Session 객체 관리 컬렉션
- **SessionStatus**: 세션 상태(준비 중, 모집 중, 마감) 관리 열거형
- **Period**: 세션 시작일과 종료일 관리 값 객체
- **Image**: 세션 커버 이미지 정보 관리 값 객체
- **CourseRepository**: Course 저장 및 조회 인터페이스
- **SessionRepository**: Session 조회 인터페이스
- **EnrollService**: 사용자 세션 수강 신청 처리 서비스

### 객체 간 관계

- Course → Sessions → Session
- Session ← FreeSession, PaidSession(상속)
- Session → Image, Period, SessionStatus
- EnrollService → SessionRepository → Session

# 3단계

## TODO

1. DB 테이블 설계

   - session 테이블: 강의 세션 정보 저장
   - course_session 테이블: 강의와 세션의 관계
   - image 테이블: 세션의 이미지 정보
   - participant 테이블: 세션 참여자 정보

2. Repository 연결

   - SessionRepository 구현
   - CourseRepository 구현
   - 각 도메인 객체와 DB 테이블 매핑

3. 기존 객체 수정
   - 도메인 객체에 DB 매핑 관련 필드 추가
   - JPA 엔티티 매핑 설정
   - 관계 설정 (1:1, 1:N, N:M)
