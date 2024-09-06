# VideoService
이 프로젝트는 영상 플랫폼을 위한 백엔드 API를 제공하는 서비스입니다. 사용자는 계정 생성, 영상 업로드, 게시물 관리 및 조회등의 기능을 수행할 수 있습니다.  

영상을 게시할 때, 미리 등록된 광고영상이 자동으로 삽입됩니다. 게시물에 대해 영상 조회수, 광고 조회수, 영상 시청 시간등이 집계되며 이를 조회 할 수 있습니다.

이 서비스는 영상 게시 및 조회와 각종 통계자료(조회수, 영상 시청 시간)를 제공하는것을 목표로 설계되었습니다.
<br/><br/>

## 🪄 기능 요약

- **계정 관리** : 회원가입, 로그인, 프로필 조회 및 수정, 계정 삭제 등의 기능을 제공합니다.
  - Spring Security를 사용하여 보안처리를 하였습니다.
  - JWT에 기반하여 인증/인가 처리를 하였습니다.
    <br/><br/>

- **영상 관리** : 일반 영상 및 광고 영상의 업로드, 조회를 처리합니다.
  - 영상은 스트리밍 방식으로 조회됩니다.
    <br/><br/>

- **광고 영상 관리** : 각 광고영상은 영상길에 맞춰서 게시물에 삽입됩니다.
  - 광고 영상의 가중치에 따라 랜덤하게 선출되는 방식을 적용하였습니다.
  - 광고 영상을 선출하는 방식은 인터페이스로 사용되기 때문에 상황에 따라 유연하게 방식을 바꿀수 있습니다.
    <br/><br/>
  
- **게시물 관리** : 게시물의 추가, 조회, 수정, 삭제 기능을 제공합니다.
  <br/><br/>
  
- **통계 관리** : 조회수, 시청시간, 광고 조회수 등의 통계자료를 관리하며, 이러한 데이터를 조회할 수 있는 API를 제공합니다.
  - 사용자(프런트)가 주도적으로 조회수나 시청시간을 증가시킬 수 있습니다.
  - 해당 프로젝트에서는 영상시청시간에 따라서 조회수를 증가시키는 방법을 상정하였습니다. 
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
- 상세한 API 명세와 사용법은 `api.html` 파일을 통해 확인할 수 있습니다. 이 문서는 각 API의 엔드포인트, 요청 및 응답 형식, 예제 등을 포함합니다.
<br/><br/>

## ⚙️ 프로젝트 구조
- 프로젝트는 각 도메인별로 계층 구조를 가지도록 설계되었습니다.
- 데이터는 Mysql에 저장되며, 영상 파일들은 프로젝트 외부에 별도로 저장됩니다.
- 서비스는 Spring cloud Eureka에 등록되어 관리됩니다.
  <br/><br/>

![image](https://github.com/user-attachments/assets/2dd662e1-0ad0-4181-942c-e9932b74051e)

<br/><br/>

## 📃 추가 정보

- 서비스 디스커버리와 마이크로서비스 아키텍처를 지원하기 위해 Eureka를 사용하고 있습니다.
- 마이크로서비스 아키텍처에서 해당 서비스는 영상 게시물에 대한 통계자료를 제공하는것이 주 목적입니다.
