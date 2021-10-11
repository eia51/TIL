# Java volatile이란?
- `volatile`은 Java 변수를 `Main Memory`에 저장하겠다라는 것을 명시하는 것입니다.

- 매번 변수의 값을 Read할 때마다 `CPU cache`에 저장 된 값이 아닌 `Main Memory`에서 읽는 것입니다.

- 또한 변수의 값을 Write할 때마다 `Main Memory`까지 작성하는 것입니다.

<br/>

## 왜 volatile이 필요할까?
![](https://images.velog.io/images/eia51/post/ec892df2-5144-422d-a3c4-f48ca3bc9681/vol.png)

- `volatile`을 사용 하지 않는 `MultiThread` 어플리케이션에서는 Task를 수행하는 동안 성능 향상을 위해 `Main Memory`에서 읽은 변수 값을 `CPU Cache`에 저장하게 됩니다.


- 만약에 `MultiThread` 환경에서 Thread가 변수 값을 읽어올 때 각각의 `CPU Cache`에 저장된 값이 다르기 때문에 변수 값 불일치 문제가 발생하게 됩니다.

<br/>

## 언제 volatile이 적합할까?
- `MultiThread` 환경에서 하나의 Thread만 read, write하고 나머지 Thread가 read하는 상황에서 가장 최신의 값을 보장합니다.

- 여러 Thread에서 read, write가 발생하는 환경이라면, `synchronized`를 통해 변수의 원자성을 보장해야 합니다.

<br/>

## volatile이 성능에 어떤 영향을 미칠까?
- `volatile`는 변수의 read와 write를 `Main Memory`에서 진행하게 됩니다.

- `CPU Cache`보다 `Main Memory`가 비용이 더 크기 때문에 변수 값 일치을 보장해야 하는 경우에만 `volatile` 사용하는 것이 좋습니다.

<br/>

## 요약
- `volatile`은 `Main Memory`에 read, write를 보장하는 키워드

- 하나의 Thread가 write하고 나머지 Thread가 읽어오는 상황에서 사용하자

- 사용 시 성능은 조금 떨어진다는 것을 감안하자

<br/>

## 참고자료
- http://tutorials.jenkov.com/java-concurrency/volatile.html
