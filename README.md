# 주요 기능

# 1. 회원 프로필 목록 조회 API

## Member 도메인 설계
- ID (ID)
- 회원 이름 (userName)
- 포인트 (point)
- Profile (Profile) OneToOne 연관관계 -> 주인

## Profile 도메인 설계
- ID (ID)
- Member (Member) oneToOne 연관관계
- 프로필 상세 조회수 (viewCount)
- 프로필 등록 날짜 (createdDate)

### 회원 프로필 목록 검색 및 조회 기능 API
- 정렬 기능 : [ O ]
    - 회원 이름 가나다순
    - 조회수 내림차순
    - 등록일 최신순


- 페이징 지원: [ O ]
    - 검색 결과를 페이지 단위로 제공해야 함 (페이지 번호 및 크기 지정 가능)

### 회원 프로필 상세 조회수 업데이트 기능 API
- 프로필 상세 조회 시 호출 될 조회수 업데이트 API [ O ]
- 업데이트된 조회수는 프로필 목록 조회에 반영 되어야 함 [ O ]

# 2. 페이먼트 기능 추가
 - 확장성 고려 Payment
   - Toss [ O ]
   - KakaoPay [ O ]