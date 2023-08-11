# [도서 대출 시스템] FreeLibrary

# 📜 Introduce
알엠소프트 과제전형 코딩테스트 프로젝트입니다. 

기획, 개발, 퍼블리싱, 배포에 걸쳐서 4일정도 시간소요가 있었습니다. 

* **URL** : [freelibrary](https://freelibrary.co.kr)

# 📜ERD
<img src="https://github.com/garlicpollpoll/assignment/assets/86602266/2f8ccb68-fc7a-4f8c-b59d-4623f6e0db3b">

# 📜테이블 정의서
<img src="https://github.com/garlicpollpoll/assignment/assets/86602266/b6700ec7-53e6-47cd-8f94-7d57f8e0ec5a">

# 📜기능 정의서
<img src="https://github.com/garlicpollpoll/assignment/assets/86602266/f3e82c25-5b4a-4209-9ea1-410de1ab3d82">

# 📜AWS 구성 내역
<img src="https://github.com/garlicpollpoll/assignment/assets/86602266/f153dedc-fed2-4dd8-87d2-52c95c4ba7ec">

# 📜시스템 구성도
<img src="https://github.com/garlicpollpoll/assignment/assets/86602266/3458e9bd-7907-4d4c-8bfd-ffcfe5665815">

# 💻CI / CD
### 블루 / 그린 배포
배포를 진행할 때 무중단 배포를 위해 블루 / 그린 배포를 진행했습니다. 

블루 / 그린 배포는 다음과 같은 절차를 따릅니다. 

* **블루 버전이 존재하는지 확인**
* **블루 버전이 존재하는 경우 블루 버전을 내리고 그린 버전을 올린다**
* **그린 버전이 존재하는 경우 그린 버전을 내리고 블루 버전을 올린다**
* **둘 다 존재하지 않는 경우 블루 버전을 올린다**
* **이전 버전의 컨테이너를 내리고 도커 이미지를 삭제한다**

### CI / CD 자동화
CI / CD는 Jenkins를 이용하여 파이프라인을 구축해 자동화를 진행했습니다. 

Jenkins를 이용한 파이프라인은 다음과 같습니다. 
* **깃허브 pulling**
* **application.yml 파일 복사**
* **Gradle 빌드**
* **Docker, Docker-Compose 다운**
* **빌드한 JAR 파일을 이용해 이미지 생성**
* **블루 그린 배포**
* **AWS CLI를 이용해 로드 밸런서의 타겟 그룹 변경**

# 🛎️구현한 기능
### 회원가입
회원은 회원가입을 진행할 수 있습니다. 

책을 대여할 때 아이디로 대여해야하기 때문에 아이디 중복확인 절차가 포함되어 있습니다. 

회원가입을 진행할 때 다음과 같은 상황에 대비하였습니다. 

* **빈칸으로 중복확인을 진행할 때** : "빈 칸은 사용할 수 없습니다." 메시지 출력
* **아이디 중복확인을 거치지 않고 회원가입을 진행할 때** : "중복확인을 진행해주세요." 메시지 출력
* **빈 칸으로 회원가입을 진행할 때** : "아이디는 빈 칸일 수 없습니다." or "비밀번호는 빈 칸일 수 없습니다." 메시지 출력
* **비밀번호가 정규표현식과 일치하지 않을 때** : "비밀번호는 문자, 숫자, 특수문자를 포함한 최소 8글자여야 합니다." 메시지 출력

### 로그인
회원은 로그인을 진행할 수 있습니다. 

로그인을 진행할 때 스프링 시큐리티를 이용해 인가를 적용하여 일반 회원과 관리자로 구분하여 로그인 처리를 진행할 수 있습니다. 

일반 회원은 관리자 페이지에 접근할 수 없으며 403 상태 코드에 맞는 페이지가 따로 준비되어 있습니다. 

### 도서 등록
도서는 관리자에 한하여 등록할 수 있습니다. 

도서를 등록할 때 도서 이미지, 책 이름, 출판사, 저자, ISBN, 재고를 입력할 수 있습니다. 

만약 빈 칸으로 입력하는 경우 "빈 칸은 입력할 수 없습니다." 메시지가 출력됩니다. 

### 도서 수정
관리자에 한하여 도서를 수정할 수 있습니다. 

도서를 수정할 때 이미지 또한 수정할 수 있으며, 등록과 마찬가지로 필드가 제공됩니다. 

### 도서 검색
회원은 도서를 키워드로 검색할 수 있습니다. 

### 도서 대여
회원은 자신의 아이디를 입력하여 도서를 대여할 수 있습니다. 

도서를 대여할 때 다음과 같은 상황에 대비하였습니다. 

* **입력한 아이디가 데이터베이스에 존재하지 않을 때** : "해당하는 회원이 없습니다." 메시지 출력
* **책의 재고가 0일 때** : "책의 재고가 없습니다." 메시지 출력
* **아이디가 데이터베이스에 존재하고 책의 재고가 있을 때** : "책이 대여되었습니다." 메시지 출력
* **아이디가 데이터베이스에 존재하고 책의 재고가 있지만 자신의 아이디가 아닐 때** : "자신의 아이디로 대여를 진행해주세요" 메시지 출력

### 도서 대여 이력
회원은 자신이 대여한 도서의 이력을 확인할 수 있습니다. 

이 때 자신이 대여한 날짜와 반납한 날짜를 확인할 수 있으며 앞으로 남은 일 수를 확인할 수 있습니다. 

대여 상태가 BORROW인 책만 보여지게 되고 대여 상태가 RETURN인 상태는 보여지지 않습니다. 

### 도서 반납
회원은 자신이 대여한 도서에 한하여 반납처리를 진행할 수 있습니다. 

도서를 반납하면 대여 상태가 BORROW에서 RETURN으로 변경됩니다. 
