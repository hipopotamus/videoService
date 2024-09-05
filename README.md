# VideoService
이 프로젝트는 영상 플랫폼을 위한 백엔드 API를 제공하는 서비스입니다. 사용자는 계정 생성, 영상 업로드, 게시물 관리 및 조회등의 기능을 수행할 수 있습니다.  
영상을 게시할 때, 미리 등록된 광고영상이 자동으로 삽입됩니다. 게시물에 대해 영상 조회수, 광고 조회수, 영상 시청 시간등이 집계되며 이를 조회 할 수 있습니다.
이 서비스는 영상 게시 및 조회와 각종 통계자료(조회수, 영상 시청 시간)를 제공하는것을 목표로 설계되었습니다.
<br/><br/>

## 🪄 기능 요약

- **계정 관리** : 회원가입, 로그인, 프로필 조회 및 수정, 계정 삭제 등의 기능을 제공합니다.
  - test
  - test

- **영상 관리** : 비디오 및 광고 영상의 업로드, 조회, 삭제를 처리합니다.
  - test

- **광고 영상 관리** : 광고영상을 업로드 할 수 있습니다. 각 광고영상은 가중치에 따라서 영상 게시물에 랜덤하게 삽입됩니다.
  
- **게시물 관리** : 게시물의 추가, 조회, 수정, 삭제 기능을 제공합니다.
  
- **통계 관리** : 조회수, 시청시간, 광고 조회수 등의 통계자료를 관리하며, 이러한 데이터를 조회할 수 있는 API를 제공합니다.
<br/><br/>

## 🛠 기술 스택

- <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> : 애플리케이션의 기반 프레임워크로, RESTful API 개발에 사용됩니다.
- <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> : 인증 및 권한 관리를 위한 보안 프레임워크입니다.
- <img src="https://img.shields.io/badge/spring eureka-6DB33F?style=for-the-badge&logo=icloud&logoColor=white"> : 서비스 디스커버리를 위한 유레카 클라이언트로, 마이크로서비스 환경에서 서비스 등록 및 검색을 지원합니다.
- <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> : 관계형 데이터베이스로, 사용자 정보 및 영상 게시물 데이터를 관리합니다.
- <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white"> : 사용자 인증 및 세션 관리를 위한 토큰 기반 인증 방식입니다.
- <img src="https://img.shields.io/badge/Query Dsl-4479A1?style=for-the-badge&logo=stagetimer&logoColor=white"> : 커서방식으로 데이터 목록을 조회하는 SQL 쿼리를 작성하기 위한 프레임워크입니다.
- <img src="https://img.shields.io/badge/REST Docs-6DB33F?style=for-the-badge&logo=readthedocs&logoColor=white"> : API 문서를 자동으로 생성하는 도구로, 테스트코드를 기반으로 RESTful API 명세를 문서화합니다.
<br/><br/>

## 📖 API 명세

- API 명세는 [Spring REST Docs](https://spring.io/projects/spring-restdocs)를 사용하여 자동으로 생성되었습니다.
- 상세한 API 명세와 사용법은 `src/main/resources/static/docs/asciidoc/index.html` 파일을 통해 확인할 수 있습니다. 이 문서는 각 API의 엔드포인트, 요청 및 응답 형식, 예제 등을 포함합니다.
<br/><br/>

## ⚙️ 프로젝트 구조

- **AccountController**: 계정과 관련된 API를 관리합니다.
- **VideoController**: 비디오 업로드 및 관리를 담당합니다.
- **AdVideoController**: 광고 영상의 업로드 및 관리를 담당합니다.
- **VideoFileController**: 비디오 파일의 조회 및 스트리밍을 처리합니다.
- **BoardController**: 게시물의 생성, 조회, 수정, 삭제 기능을 제공합니다.
- **BoardStatisticsController**: 게시물 통계자료의 조회, 업데이트를 관리합니다.
- **WatchHistoryController**: 사용자의 시청 기록을 업데이트합니다.
<br/><br/>

## 📃 추가 정보

- 이 프로젝트는 Spring Boot를 기반으로 개발되었으며, MySQL을 사용하여 데이터를 관리합니다. 또한, JWT를 사용하여 인증과 권한 관리를 처리하며, QueryDSL을 통해 데이터베이스 쿼리를 안전하게 작성합니다.
- 서비스 디스커버리와 마이크로서비스 아키텍처를 지원하기 위해 Eureka를 사용하고 있으며, API 문서는 Spring REST Docs를 통해 자동으로 생성되었습니다.
