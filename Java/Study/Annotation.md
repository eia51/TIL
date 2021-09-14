## ◆ 어노테이션이란?

어노테이션은 자바 소스코드에 추가할 수 있는 일종의 메타 데이터입니다.

자체적으로는 프로젝트에 아무런 영향을 끼치지 않지만,

컴파일 타임, 런타임에 어떻게 해석하느냐에 따라 생산성에 많은 영향을 끼치기도 하는 자바의 유용한 기능입니다.

일반적으로 `클래스` `인터페이스` `메서드` `변수` `파라미터` 등에 사용됩니다.

<br/>

## ◆ 어노테이션, 왜 사용해야할까?
> 어노테이션은 빠르고, 또 빠르고, 효율적입니다.

### ◇ 빠른 속도
Annotation Processor는 실제로 javac의 일부이므로 모든 처리를 컴파일 타임에 하게 됩니다.

런타임이 아닌, 컴파일 타임에 특정 작업을 수행함으로서 런타임 때 부담 할 일을 줄여 더 빠른 속도의 처리가 가능하게 됩니다.

`* javac : jdk에 포함 된 자바 컴파일러. 소스코드를 jvm에서 돌아가는 바이트코드로 변환`


### ◇ No! 리플렉션 
`리플렉션`은 구체적인 클래스의 타입을 알지 못해도 그 내부에 있는 메소드, 타입, 변수를 접근하게끔 해주는 자바의 API입니다.

`javac`에 의해 바이트코드로 컴파일되어 Static 영역에 위치하는 클래스 정보를 읽어들여, 정확한 타입을 알지 못해도 이 API를 통해 그 클래스의 요소들을 사용할 수 있게 됩니다.

하지만, 이렇듯 요청이 발생할 때 마다 클래스 정보를 읽어 처리하다보니 `리플렉션`은 비용이 높은 작업으로 분류됩니다.
  
어노테이션 프로세서는 `리플렉션` 없이 `Mirror API`를 사용해서 프로그램의 구조를 파악하기 때문에 보다 빠르고 효율적입니다.

`Mirror API : 프로그램의 의미 구조를 가상머신의 레벨이 아닌 언어 레벨에서 모델링 하기 위한 API. 클래스에 대한 런타임을 제공하지 않고 프로그램을 정적, 빌드시간 단위로 모델링 한다.`


### ◇ 보일러 플레이트 코드 제거
어노테이션 프로세서를 사용하는 가장 큰 장점은 `보일러 플레이트 코드`를 자동으로 생성할 수 있다는 것 입니다.

`보일러 플레이트 코드`는 비슷한 형태로 반복되는 코드를 뜻합니다.

이런 코드가 프로젝트에 많아질수록 관리해야하는 포인트가 늘어나게 되고, 이로 인해  버그 발생의 위험성은 높아지게 됩니다.

`보일러 플레이트 코드`를 자동으로 만들어주는 어노테이션 사용을 통해 여러분들의 관리 포인트는 크게 줄어들 수 있고, 클린 코드에 한발짝 더 다가갈 수 있게 될 것입니다.

실제로 안드로이드 개발에서 대중적으로 사용되고 있는 `Databinding` `Room` `Retrofit` `Dagger2` 등의 많은 라이브러리들이 보일러 플레이트 코드를 자동으로 생성하고자 어노테이션을 사용하고 있습니다.

<br/>

## ◆ 어노테이션이 처리되는 과정
어노테이션은 컴파일타임에 어노테이션 프로세서에 의해 처리됩니다.

어노테이션의 처리는 여러 라운드에 걸쳐 수행 될 수 있습니다.

이 라운드는 사용자가, 혹은 사용되는 라이브러리에서 정의 된 어노테이션 패턴, 즉 depth에 따라 결정됩니다. 
 
![](https://images.velog.io/images/eia51/post/bef90c12-d0ef-48bf-9d9b-8b450dc2ec88/anotation_processing.png)

먼저 **첫 번째 프로세싱 라운드**가 시작되면 실행되지 않은 어노테이션 프로세서들이 소스코드에 있는 어노테이션을 스캔하고, 그에 따른 작업을 수행합니다. 이 과정에서 보일러 플레이트 코드가 생성됩니다. 

첫 번째 프로세싱 라운드가 수행되면서 만들어진 만들어진 보일러 플레이트 코드에는 또 다른 어노테이션이 포함되어 있을 수 있습니다. 그러면 어노테이션 프로세서는 다시 이 새로운 어노테이션을 스캔하고, 그에 따른 작업을 수행하는 **두 번째 프로세싱 라운드**를 수행하게 됩니다.

이러한 라운드는 어노테이션 프로세서가 더 이상 처리 할 어노테이션이 없어질 때 까지 반복합니다.

<br/>

## ◆ 어노테이션의 종류
어노테이션은 크게 `Built-in Annotation` `Meta Annotation` `Custom Annotation` 세 분류로 나눠 볼 수 있습니다.

### ◇ Built-in Annotation
이미 Java에 내장되어있는 <U>컴파일러를 위한</U> 어노테이션입니다.

- **@Override**
  메소드 앞에 붙임으로서 현재 메소드가 수퍼클래스의 메소드를 재정의 했음을 컴파일러에게 명시하고, 재정의 시 오탈자를 막아줌
  
- **@Deprecated**
  차후 버전에서 지원 중단 예정, 때문에 더 이상 사용하지 말아야 할 메소드를 표시
  `Deprecated 어노테이션이 붙은 메소드를 사용했을 때, 메소드의 이름이 실선 처리가 되어 보여지게 됩니다. 이 경우 메소드 레퍼런스를 따라가 Instead 링크 부분에 명시 된 대안 메소드를 사용하시길 권장합니다` 
  
- **@SupressWarning**
  컴파일러가 주는 경고 메세지를 프로그래머가 의도적으로 무시하고자 할 때 사용  
  
- **@NonNull**
  파라미터로 Null을 넣지 못하게 경고하는 의미에서 사용되는 어노테이션
  파라미터에 NonNull이 붙어있는 메소드를 호출 할 때 인자로 `null`을 넣으면 컴파일러가 경고를 표시함
  
- **@FunctionalInterace**
  컴파일러에게 함수형 인터페이스라는 것을 알려 입력 실수를 방지함 (@Override와 유사한 기능)


### ◇ Meta Annotation
어노테이션을 위한 어노테이션으로 해당 <U>어노테이션의 동작 대상, 스코프를 결정</U>하는 어노테이션입니다. 주로 새로운 어노테이션을 정의할 때 사용됩니다.

- #### @Target
  어노테이션이 적용 가능한 대상을 지정하는데 사용. 여러 대상을 지정해야 할 때 `{ }`로 묶어서 사용.

- #### @Retention
  어노테이션의 `유지기간(라이프사이클)`을 지정하기 위해 사용.
  다음 세 가지의 정책이 존재한다.
  1. `SOURCE` ~~(=그냥 주석)~~
    소스파일에만 존재하는 어노테이션. 컴파일 타임에 컴파일러에 의해 삭제 됨.
    
  2. `CLASS`
	클래스파일에는 존재하지만, 실질적으로 런타임까지 유지되진 않음. 
    (※클래스파일엔 포함되지만, 런타임 전 사라지기 때문에 리플렉션으로 어노테이션을 참조 할 수 없습니다.)
    
    RetentionPolicy의 default 값.
  
  3. `RUNTIME`
	클래스 파일에 존재하며, 런타임의 종료 시점까지 메모리가 유지 됨.

- #### @Documented
  어노테이션에 대한 정보가 javadoc으로 작성한 문서에 포함 되도록 할 때 사용하는 어노테이션. `Built-in Annotation` 중 `@Override`와 `@SuppressWarnings`를 제외하고는 모두 이 어노테이션이 붙어있다.

- #### @Inherited
  어노테이션을 자식 클래스에게도 붙이기 위해(상속) 사용하는 어노테이션. 이 어노테이션을 수퍼클래스에 붙이면 서브 클래스에서도 이 어노테이션이 붙은 것과 같이 인식된다.
  
- #### @Native
  `네이티브 메소드`에 의해 참조되는 상수 필드에 붙이는 어노테이션.
  
  > **네이티브 메소드**
  > JVM이 설치 된 OS의 메소드. 자바에서는 메서드의 선언부만 정의하고, 실질적인 구현은 C언어로 되어있다.
  >
  > 네이티브 메소드와 자바에서 정의한 메소드를 연결하는 것을 `JNI`라고 한다.

### ◇ Custom Annotation
사용자가 개발의 편의를 위해 정의하는 어노테이션입니다.
어노테이션은 특별한 종류의 인터페이스이며, 일반 인터페이스와 타입 구분을 위해 @를 앞에 붙여 선언합니다.

```java
   public @interface CustomAnnotation {
   }
```

어노테이션 타입은 암묵적으로 `java.lang.annotation.Annotation`을 확장하기 때문에 `extend` 절을 갖지 못합니다.


어노테이션은 메타데이터의 저장을 위한 Element를 가질 수 있습니다. 그리고 이 Element의 개수에 따라 `Marker Annotation` `Single-value Annotation` `Full Annotation` 으로 분류할 수 있습니다.

- #### Marker Annotation ?
  Element가 하나도 없는, 단순 표식으로 사용되는 어노테이션, 컴파일러에게 의미를 전달하거나 <U>주석 목적</U>으로 사용

- #### Single-value Annotation
  Element가 한개인 어노테이션, 값을 명시해 <U>데이터를 전달하기 위해</U> 사용 
  
  아래의 예시는 실무에서 빈번하게 사용되는 `Retrofit2`의 `POST` 어노테이션.
  Single-value Annotation의 일종으로 사용자에게 Path 정보를 입력 받아 `baseUrl`과 매핑하여 requestUrl을 결정한다.
```java
     package retrofit2.http;
     
     @Documented
     @Target(METHOD)
     @Retention(RUNTIME)
     public @interface POST {
	String value() default "";
     }
   
     @POST("/my_path") 
     Single<List<Object>> getPosts();     
```

- #### Full Annotation
  Element로 둘 이상의 변수를 갖는 어노테이션, 데이터를 배열 안에 `key-value` 형태로 전달. `Element의 타입은 기본형, String, enum, Annotation, Class만 허용한다.`
```java
     public @interface FullAnno {
	int count() default 1;
	String value();
     }
   
     @FullAnno("payload") // @FullAno(count=1, value=payload) 와 같다
     class TestClass {...}    
```  
  


  
  
---
#### 참고 자료

Annotation
https://simostory.tistory.com/32
https://asfirstalways.tistory.com/309

Reflection 
https://gyrfalcon.tistory.com/entry/Java-Reflection

Mirror API
https://docs.oracle.com/javase/7/docs/jdk/api/apt/mirror/overview-summary.html

first class constructs
https://stackoverflow.com/questions/646794/what-is-a-first-class-programming-construct
