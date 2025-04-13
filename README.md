# 학습 관리 시스템(Learning Management System)
## 진행 방법
* 학습 관리 시스템의 수강신청 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


## 리팩토링
### step1
- [x] Question에 메시지 보내서 질문, 답변 삭제하기
- [x] Question으로 DeleteHistory 생성하여 저장하기
- [x] 힌트 참고해서 보강
- [x] 리뷰 피드백
  - [x] 어색한 메서드 네이밍 변경
  - [x] delete 실행 시, DeleteHistory까지 하나의 생명주로 가져가도록 변경
### step2
- [x] 강의(Session)는 시작일과 종료일을 갖는다.
- [ ] 강의(Session)은 강의 커버 이미지를 갖는다.
  - [ ] 이미지 크기는 1MB 이하
  - [ ] 이미지 타입은 gif, jpg(jpeg), png, svg 허용
  - [ ] 이미지 width는 300px, height는 200px 이상이어야 함
  - [ ] width와 height의 비율은 3:2
- [ ] 강의(Session)는 무료와 유료로 나뉜다.
  - [ ] 무료 강의는 수강 인원 제한이 없음
  - [ ] 유료 강의는 최대 수강 인원이 존재
  - [ ] 유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청 가능
  - [ ] 유료 강의의 결제 정보는 payments 모듈을 통해 관리됨
- [ ] 강의(Sessino) 상태는 3가지 상태를 갖는다. (준비중, 모집중, 종료)
- [ ] 강의(Session) 수강신청은 강의 상태가 모집중일 때만 가능하다.
- [ ] 과정(Course)은 여러 개의 강의(Session)을 갖는다.
