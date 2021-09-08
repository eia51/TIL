## SimpleDateFormat 이란?
[SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html) 은 Java에서 날짜를 특정 형식에 맞게 출력하기 위해 사용됩니다.

```
2021-09-06 23:50:00 
```

이와 같은 정형화 된 시간 문자열을 우리는 아래와 같이 [SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html) 클래스를 사용하여 손쉽게 만들어낼 수 있습니다.

```java
SimpleDateForat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date date = new Date();
System.out.println(sdf.format(date));
```

<br/>

## SimpleDateFormat, 제대로 알고 사용하자
### Non Thread Safe 한 SimpleDateFormat
이렇듯 편리한 [SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html) 클래스이지만, 사용하기에 앞서 여러분들이 알아야 할 것이 있습니다.

바로 <U>__[SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)은 스레드로부터 안전하지 않은 클래스__</U>이며, 멀티스레드환경에서 이러한 부분이 고려되지 않았을 때 예기치 못한 문제가 발생할 수 있다는 것입니다.

---

저처럼 리팩토링의 세계에 갓 입문한 주니어 분들은
프로젝트 내 부분 부분 날짜 포맷팅이 필요한 부분에서 [SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)을 여기저기서 독립적으로 인스턴스화 하여 사용하는 코드를 보게 된다면, 리팩토링 하고 싶은 욕망이 꿈틀거릴 것입니다.
>"왜 같은 객체를 여기저기서 여러번 생성하지? 한 번만 생성할 수 있게 Singleton으로 인스턴스를 관리하고, 객체 생성부를 캡슐화 해야되지 않을까?"

호기롭게 [SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)에 대한 리팩토링을 마치고 난 뒤, 저는 다음과 같은 에러를 맞게 됩니다.

```java
java.lang.NumberFormatException: multiple points
```

![](https://images.velog.io/images/eia51/post/60b5f805-db82-4ab1-9cd4-1804d5d30bff/a.jpg)

성능을 위해 중복 인스턴스 생성을 막아보았건만, 사실 이 [SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html) 은 
__스레드로부터 안전하지 않은__ 클래스였던 것입니다.

<br/>

### SimpleDateFormat이 Non-Thread-Safe 한 이유?

[SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)의 구현체를 보면 내부적으로 [Calendar](https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html)를 인스턴스화 해서 사용하고 있습니다.
[SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)에서 날짜 포맷팅을 위한 parse() 메소드가 호출 될 때
```java
calendar.clear()
calendar.add() 
```
가 순차적으로 호출됩니다.

이 때 멀티스레드 환경이라면 calendar 인스턴스 손상이 발생하게 되고 결과적으로 'multiple points' 에러가 발생하는 것입니다.

<br/>
<br/>

## 멀티스레드 환경에서 안전하게 날짜 출력하기
다행히 이런 문제를 우리는 큰 어려움을 들이지 않고 여러 가지 방법으로 해결할 수 있습니다.

>***1.*** 재사용 없이 사용 시점에서 매번 새로운 SimpleDateFormat 인스턴스를 생성하여 사용한다.
>
>***2.*** SimpleDateFormat을 사용하는 코드 앞에 syncrhnized 키워드 사용한다.
>
>***3.*** <U>DateTimeFormatter (Java 8) 사용한다.</U>

여러분의 개발환경이 Java8 이상이라면 새롭게 추가 된 [DateTimeFormatter](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html) 를 사용해보시길 적극 추천드립니다.

[DateTimeFormatter](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html) 은 다음과 같이 사용할 수 있습니다.
```java
DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
LocalDateTime now = LocalDateTime.now();
System.out.println(now.format(df));
```

이렇듯 사용방법도 [SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)과 유사하며, 이제 여러분들은 더이상 멀티스레드 환경에서 발생할 문제를 걱정하지 않아도 됩니다.

---

이상 멀티스레드 환경에서 [SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)을 사용했을 때 발생하는 문제와 해결 방법에 대해서 간략하게 알아보았습니다.

그럼 다들 [DateTimeFormatter](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html)과 함께 <U>스레드 세이프</U> 한 자바 코딩 되세요 ~

![](https://images.velog.io/images/eia51/post/1841efa3-cbb3-4bab-8443-325b11c036bb/c.gif)

<br/>

---

#### 참고한 내용
https://stackoverflow.com/questions/6840803/why-is-javas-simpledateformat-not-thread-safe
https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
https://www.callicoder.com/java-simpledateformat-thread-safety-issues/
