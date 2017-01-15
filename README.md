# 개인 프로젝트 (setlist)

## 1. 프로젝트 목표
* 다음 세가지를 만족하는 것을 목표로 간단한 사이트 개발
* DDD (도메인 주도 디자인) 개념 도입
* Spring Boot를 통한 구성 간소화 (application.yml만으로 설정하기)
* Multi Project (여러 프로젝트에서 비즈니스 로직 공유)

## 2. 사용 기술
* Spring Boot 1.4.3 (스프링4.3.5)
* EhCache : 정적이며 반복적인 요청을 빠른 속도로 처리
* Spring data JPA : 도메인 주도 설계를 위한 데이터 처리 (간단한 CRUD용)
* MyBatis : 복잡한 쿼리 사용을 위한 SQL매퍼
* Tiles : 템플릿 엔진
* Bootstrap : HTML UI 라이브러리
* Webjars : 자바용 외부 js, css 라이브러리 의존성 관리
* H2 : 학습용이며 회사와 집에서 동시에 개발이 진행돼야 하기 때문에 설치가 불필요한 메모리 DB 사용
* Gradle : 의존성 관리, 빌드, Multi-project 구성
* ~~Social~~
* ~~Thymeleaf ~~

## 3. 사이트 개요
* 제목 : 세트리스트.kr
* 국내외 음악가들 공연의 연주곡을 공유하여 다음 공연의 세트리스트를 예상하는게 목적
* 콘서트나 페스티벌에서 부른 노래 목록을 정리 (Wiki)
* 세트리스트 검색, 등록 기능이 포함됨
