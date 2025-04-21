## 핵심 학습 목표
2단계에서 구현한 객체 구조(도메인 구조)를 가능한 유지하면서 DB 테이블과 매핑한다.
성능보다 도메인 객체에 로직 구현하는 것을 목표로 연습한다.
객체 구조를 유지하기 위해 여러 번의 DB 쿼리를 실행해도 괜찮다.

## 수강 신청 기능 (요구 사항 DB 매핑)
- 과정(Course)은 기수 단위로 운영하며, 여러 개의 강의(Session)를 가질 수 있다.
- 강의는 시작일과 종료일을 가진다. (SessionDate)
- 강의는 강의 커버 이미지 정보를 가진다.(SessionCoverImage)
    - 이미지 크기는 1MB 이하여야 한다. (ImageFileSize)
    - 이미지 타입은 gif, jpg(jpeg 포함), png, svg만 허용한다. (ImageType)
    - 이미지의 width는 300픽셀, height는 200픽셀 이상이어야 하며, width와 height의 비율은 3:2여야 한다. (ImageSize)
- 강의는 무료 강의와 유료 강의로 나뉜다. (FreeSession, PaidSession)
    - 무료 강의는 최대 수강 인원 제한이 없다.
    - 유료 강의는 강의 최대 수강 인원을 초과할 수 없다.
    - 유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다. (Enrollment)
- 강의 상태는 준비중, 모집중, 종료 3가지 상태를 가진다. (SessionStatus)
- 강의 수강신청은 강의 상태가 모집중일 때만 가능하다.
- 유료 강의의 경우 결제는 이미 완료한 것으로 가정하고 이후 과정을 구현한다.
    - 결제를 완료한 결제 정보는 payments 모듈을 통해 관리되며, 결제 정보는 Payment 객체에 담겨 반한된다.

## 프로그래밍 요구 사항
- 앞 단계에서 구현한 도메인 모델을 DB 테이블과 매핑하고, 데이터를 저장한다.
  - CRUD 쿼리와 코드를 구현하는데 집중하기 보다 테이블을 설계하고 객체 매핑하는 부분에 집중한다.
- Payment는 테이블 매핑을 고려하지 않아도 된다.

## 기능 구현 목록
- [x] Session 도메인에서 courseId 를 갖도록 변경한다.
- [x] SessionCoverImage 도메인에서 sessionId 를 갖도록 필드를 추가한다.
- [x] Student 도메인에서 수강신청한 강의 목록을 갖도록 필드를 추가한다.
- [x] DB 테이블 설계 (schema.sql 구현)
  - [x] Session table 설계
  - [x] SessionCoverImage table 설계
  - [x] Enrollment(등록 현황) table 설계
- [x] DB 테이블에 따른 엔티티를 구현한다.
- [x] 도메인과 엔티티 객체 간 변환하는 mapper 를 구현한다.
- [x] DB에 데이터를 저장하는 repository method 를 작성한다.
- [x] repository 에 저장 및 조회하는 method 를 구현한다.
