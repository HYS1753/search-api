# Search API
---
## Ⅰ. Intro
- OpenSource 검색엔진을 사용해 보기 위한 RestAPI 프로젝트 입니다.
- 현재의 Apache Lucene 연동을 통한 검색 실습과 더불어, ElaticSearch 연동을 목적으로 합니다.

## Ⅱ. Implementation
### 1. RestAPI
- IDE 
  - Spring Tool Suite 4 (sts-4.17.1.RELEASE)
- JAVA
  - jdk-17.0.6
- Project Dependencies
  - SpringBoot
    - `implementation 'org.springframework.boot:spring-boot-starter-web'`
  - Swagger3
    - `implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2"`
  - DataBase
    - `implementation 'org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16'`
	- `runtimeOnly 'org.postgresql:postgresql'`
  - lombok
    - `compileOnly 'org.projectlombok:lombok:1.18.24'`
	- `annotationProcessor('org.projectlombok:lombok:1.18.24')`
  - csv
    - `implementation 'com.opencsv:opencsv:5.2'`
  - Lucene
    - `implementation 'org.apache.lucene:lucene-core:8.11.2'`
    - `implementation 'org.apache.lucene:lucene-analyzers-nori:8.11.2'`
  - common
    - `implementation 'com.googlecode.json-simple:json-simple:1.1.1'`
    - `implementation 'commons-lang:commons-lang:2.6'`
    - `implementation 'org.apache.httpcomponents:httpclient:4.5.13'`

### 2. Batch
- tobe

## Ⅲ.Reference
- [실전비급 아파치 루씬 7](https://github.com/JAVACAFE-STUDY/elasticbooks/tree/master/project/lucene)