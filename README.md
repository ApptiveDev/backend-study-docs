# 백엔드 스터디 자료

## 1주차

### 스터디 내용

- 백엔드 개발 여정의 시작
- 서버의 동작 원리
- HTTP Method, 상태 코드
- 유저 플로우 작성해보기
- Spring Boot 기반 CRUD 예제 살펴보기
    - 원본 URL : [신세환의 할 일 관리 CRUD](https://github.com/sinsehwan/study-backend-sample)

### 과제
- 게시글 CRUD(생성, 조회, 수정, 삭제) 만들어보고 PR 날리기 (본인 이름 브랜치에 PR 요청 보내주세요! : [과제 제출](https://github.com/ApptiveDev/backend-study-sns))
> POST CRUD
- e.g. Post
    - content
    - like
    - username (작성자명)
    - createdAt
    - updatedAt
- 너무 쉽다면?
    - 댓글 등 기능 추가


## 2주차

### 스터디 내용

- 1주차 과제 상호 코드리뷰
- RequestParam, PathVariable, RequestBody의 차이 명확히 구분하기
- DTO와 Record 그리고 Validation
- ORM의 필요성 (feat. 객체 중심 모델과 데이터 중심 모델의 패러다임 차이)

### 과제

- 1주차 과제 피드백 내용 반영하기
- 댓글 기능 구현하기 & Post와 연관관계 설정하기
    - e.g. Comment
        - content
        - like
        - username (작성자명)
        - createdAt
        - updatedAt
- GPT 없이 최대한 구현해보기
- 테스트 코드 작성해보기(테스트 코드는 이번에는 GPT 사용하셔도 됩니다!)

## 3주차

### 스터디 내용

- 2주차 과제 상호 코드리뷰
- 인증/인가 개념 및 세션, 토큰 기반 인증 과정
- 테스트 코드 개념 및 작성 방법
- 엔티티 매니저와 영속성 컨텍스트 정리

### 과제

- 2주차 내용 피드백 반영하기
- Member 도입 및 기능 확장 (필요하다고 판단되는 필드 구성해주시면 됩니다.)
    - 멤버 검색하기
    - 멤버의 게시글 & 댓글 조회
    - 좋아요 기능 - 어떤 Member가 눌렀는지
    - (선택) 팔로우 기능 구현
