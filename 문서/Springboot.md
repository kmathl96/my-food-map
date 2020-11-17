# Spring boot :seedling:

> 참고
>
> 1. https://velog.io/@max9106
> 2. https://goddaehee.tistory.com/



## 0. Spring boot

- 스프링 프레임워크 기반 프로젝트를 아주 간편하고 쉽게 만들 수 있도록 해줌

- 반복되는 개발환경 구축을 위한 코드작성등의 노력을 줄여주고 쉽고 빠르게 프로젝트를 설정할 수 있도록 도와줌
- 프로젝트 환경 구축에서 큰 영역을 차지하는 비기능적인 기능들을 기본적으로 제공한다.
  - 내장형 서버, 시큐리티, 측정, 상태 점검, 외부 설정



## 1. 시작하기

- GroupId : 자신의 프로젝트를 식별해주는 고유 아이디
  - `com.web.curation`
- ArtifactId : 버전 정보를 생략한 이름(jar)
  - 보통 프로젝트 ID와 동일하게 작성



### pom.xml에 코드 추가

#### 1. spring-boot-starter-parent

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.4.RELEASE</version>
</parent>
```

- 이 프로젝트의 부모 프로젝트를 'spring-boot-starter-parent'로 설정해주는 것
- 스프링부트에 필요한 dependency를 자동으로 추가해 줌



#### 2-1. spring-boot-starter-web

- Spring MVC를 사용한 RESTful 서비스를 개발하는 데 사용

#### 2-2. spring-boot-starter-test

- Junit, Hamcrest, Mockito를 포함하는 스프링 어플리케이션을 테스트 가능하도록 함

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
</dependencies>
```



#### 3. build plugin

> 빌드에 사용할 플러그인

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```



## 2. 실행하기

1. src - main - java에 패키지 생성

2. 패키지 내에 클래스 생성 후 아래 코드 입력

   ```java
   @SpringBootApplication
   public class Application {
       public static void main(String[] args) {
           SpringApplication.run(Application.class, args);
       }
   }
   ```

3. 실행



# Spring boot 프로젝트 구조 :deciduous_tree: 

- src - main - **java**
  - 자바 소스코드
- src - main - **resources**
  - 자바 코드를 제외한 모든 것
  - 자바 application에서 resources의 모든 하위 항목들을 참조할 수 있음
- src - main - **test**
  - 테스트 코드
- 스프링에서는 메인 클래스(`@SpringBootApplication`)를 가장 최상위 패키지 하위에 두는 것을 추천
  - java 바로 아래에 두면 모든 패키지를 다 스캔하기 때문



# 의존성 관리 :droplet:

- 스프링부트는 여러 의존성들을 관리
- 스프링부트가 관리해주는 의존성
  - pom.xml에서 version을 명시해주지 않아도 자동으로 version을 설정해줌
  - 사용할 때 version없이 dependency만 추가해주면 됨
  - 의존성 버전을 바꾸고 싶을 때는 pom.xml에서 의존성 버전을 함께 정의
- 스프링부트가 관리해주는 의존성이 아닌 의존성
  - dependency에 version과 함께 정의



# 자동 설정 :gear:

> @EnableAutoConfiguration



## @SpringBootApplication

- 메인 클래스에 붙어 있음
- @SpringBootConfiguration, @ComponentScan, @EnableAutoConfiguration이 합쳐진 것이라고 볼 수 있음
- SpringBootApplication은 `Bean`(스프링 컨테이너에 의해 만들어진 **객체**)을 2번 등록
  1. 처음에 ComponentScan으로 등록
  2. EnableAutoConfiguration으로 추가적인 Bean들을 읽어서 등록



## @ComponentScan

- 해당 페이지에서 @component 또는 @Configuration, @Repository, @Service, @Controller, @RestController를 가진 Bean들을 스캔해서 등록



## @EnableAutoConfiguration

> Configuration : 조건에 따라 Bean을 등록하는 자바 설정 파일

1. 메인 클래스(@SpringBootApplication) 실행
2. @EnableAutoConfiguration에 의해 spring.factories 안에 들어있는 수많은 자동 설정들이 조건에 따라 적용되어 수 많은 Bean들이 생성됨
3. 스프링 부트 어플리케이션이 실행됨



## 자동 설정 구현

1. 의존성 추가

   ```xml
   <dependencies>
   	<dependency>
   		<groupId>org.springframework.boot</groupId>
   		<artifactId>spring-boot-autoconfigure</artifactId>
   	</dependency>
   	<dependency>
   		<groupId>org.springframework.boot</groupId>
   		<artifactId>spring-boot-autoconfigure-processor</artifactId>
   		<optional>true</optional>
   	</dependency>
   </dependencies>
   
   <!-- 추가해준 의존성 버전 관리를 하기 위함 -->
   <dependencyManagement>
   	<dependencies>
   		<dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-dependencies</artifactId>
   			<version>2.0.3.RELEASE</version>
   			<type>pom</type>
   			<scope>import</scope>
   		</dependency>
   	</dependencies>
   </dependencyManagement>
   ```

2. Configuration 파일 작성

3. spring.factories 파일 생성

   - main - resources에 META-INF 디렉토리 생성

   - spring.factories 파일 생성

     - 만들어준 설정 파일을 명시적으로 정의

     ```
     org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
       패키지이름.Configuration파일이름
     ```

4. mvn install

5. 다른 프로젝트에서 Bean 사용

   - 의존성 추가해주면 사용 가능



# 외부 설정 :airplane:

> 외부 설정 파일 : 애플리케이션에서 사용하는 여러 가지 설정 값들을 애플리케이션의 밖이나 안에 정의할 수 있는 기능



## 1. Application.properties

> 스프링부트가 애플리케이션을 구동할 때 자동으로 로딩하는 파일

- key-value 형식으로 값을 정의하면 애플리케이션에서 참조하여 사용 가능



### 값을 참조하는 방법

1. @Value로 값을 받아올 수 있음

   ```java
   @Value("${my.name}")
   String name;
   ```

2. application.properties에 ${random.~}으로 랜덤 값을 줄 수 있음

   ```java
   my.age = ${random.int}
   ```

3. 프로퍼티 우선순위



## 2. @ConfigurationProperties

> properties 파일의 key 값이 같은 값으로 시작할 때, 그것을 묶어서 Bean으로 등록



# Spring data JPA

## 1. ORM

> **O**bject **R**elational **M**apping

- 객체와 테이블을 mapping할 때 발생하는 개념적인 불일치를 해결하는 프레임워크

- 프로그램의 복잡도를 줄이고 자바 객체와 쿼리를 분리
- 트랜잭션 처리나 기타 데이터베이스 관련 작업들을 좀 더 편리하게 처리



## 2. JPA

> **J**ava **P**ersistence **A**PI\

- 자바 ORM 기술에 대한 API 표준 규격

- 스프링 데이터 JPA는 JPA 표준 스펙을 쉽게 사용할 수 있게 스프링 데이터로 추상화 시켜놓은 것

- ORM을 사용하기 위한 인터페이스를 모아둔 것
- 자바 어플리케이션에서 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스



---



# Controller :joystick:



## 1. MVC

> **M**odel **V**iew **C**ontroller

1. Model : 비즈니스 규칙
2. View : 프레젠테이션
3. Controller : Model, View를 분리하기 위하여 양측 사이에 배치된 인터페이스



## 2. Controller

> 사용자의 요청을 처리한 후 지정된 view에 model 객체를 넘겨주는 역할



### Annotation

#### 1. @Controller

- Controller의 역할을 수행한다고 명시
- 필요한 비즈니스 로직을 호출하여, 전달할 model과 이동하 view 정보를 `DispatcherServlet`에 반환
  - DispatcherServlet : 제일 앞에서 서버로 들어오는 모든 요청을 처리하는 프론트 컨트롤러; 공통처리 작업을 처리한 후, 적절한 세부 컨트롤러로 작업을 위임해줌
- Bean으로 등록
- @Component의 구체화된 annotation

#### 2. @RequestMapping

- 요청에 대해 어떤 Controller, 어떤 메소드가 처리할지 맵핑하기 위한 annotation
- 클래스나 메소드 선언부에 @RequestMapping과 함께 URL을 명시하여 사용
- default viewName : @RequestMapping의 path로 설정한 URL

##### RequestMapping 속성

1. **value**(String[]) : URL 값
2. **method**(RequestMethod[]) : HTTP Request 메소드 값
   - GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE
3. **params**(String[]) : HTTP Request 파라미터
   - @RequestParam : 사용자가 원하는 매개변수에 값을 매핑하기 위해 사용
   - @PathVariable : url 경로를 변수화하여 사용
4. **consumes**(String[]) : Request body에 담는 타입 제한
   - @PostMapping("/login", consumes="application/json")
     : 헤더에 application/json이 존재해야 처리



## 3. Controller 생성

### 1. Controller 생성

> 원하는 패키지에 controller 파일 생성

```java
@Controller
public class TestController {
    // 1. view를 반환해야 하는 경우
    @RequestMapping(value = "/home")
    public String home(){
        return "index.html";
    }
    
    // 2. data를 반환해야 하는 경우
    @ResponseBody
    @RequestMapping("/valueTest")
    public String valueTest(){
        String value = "테스트 String";
        return value;
    }
}
```

- `@ResponseBody`를 통해 String, Map, JSON 등을 전달할 수 있음
- Spring 4.0이상은 `@Controller`와 `@ResponseBody` 어노테이션을 추가하는 것 대신 `@RestController`을 사용하면 됨



### 2. View 생성

> src - main - resources - static 경로에 index.html 파일 추가

```html
<!DOCTYPE html>
<html lang="en">
    <head> <meta charset="UTF-8">
        <title>Index</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
```

```html
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Index</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>
            $.ajax({
                type: "GET",
                url: "/valueTest",
                success: (data) => {
                    console.log(data);
                    $('#contents').html(data);
                }
            });
        </script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <div id="contents">
        </div>
    </body>
</html>
```



### 3. 실행

1. Application 파일 실행
2. 설정한 URL로 접속