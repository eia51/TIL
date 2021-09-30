# Context ?

- 앱의 현재 상태를 가지고 있는 변수.
- 시스템이 관리하고 있는 `Application` `Activity`의 정보를 얻기 위해 사용(`context.getSystemService(..)`)
- `Application` `Activity는` 모두 `Context`를 상속 받는다.
- 그렇기 때문에 안드로이드의 `Context`는 `ApplicationContext` `ActivityContext` 두 분류로 나뉜다.
- 잘못 된 `Context`의 사용은 메모리 릭으로 이어지기 때문에 각 `Context`의 사용처를 잘 구분해야한다.

<br/>

### Application Context
- `Activity`에서 `getApplicationContext()`를 통해 얻을 수 있는 싱글톤 인스턴스.
- `Application` 라이프사이클을 따른다. 
- 때문에 `Activity`의 범위를 벗어나는 곳에서 `Context`를 사용해야하는 경우 적합하다.

<br/>

### Activity Context
- `Activity`에서 `this` 키워드를 통해 다른 객체로 전달 할 수 있다.
- `Activity`의 라이프사이클을 따른다.
- `Activity` 범위 내에서 만들어지고 사라지는 객체에서 `Context`를 사용해야하는 경우 적합하다. 
> 앱 전역에 사용되는 객체를 생성할 때 `ActivityContext`를  넣게 되면 `Activity`가 종료되고 난 뒤에도 GC 되지 않아 메모리 참조가 계속 남아있게 되고, 결과적으로 메모리 릭으로 이어진다.

<br/>

### Application Context 사용 시 주의점
- `ApplicationContext`는 `ActivityContext`가 지원하는 모든 항목을 지원하지 않는다. 
  `View Component` 관련 동작에 있어서는 `ActivityContext`를 사용하도록 하자.

- `Dialog`를 만들기 위한 생성자에 `ApplicationContext`를 넣으면 다음과 같은 오류가 발생한다.
  `java.lang.IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activit`
  
- `ApplicationContext`를 사용하여 만든 객체를 더 이상 사용하지 않을 때 메모리 할당을 해제하도록 하자. 그렇지 않을 경우 `Application`이 살아있는 동안 계속해서 메모리에 남아 메모리 릭을 유발할 수 있다.

<br/>

### 결론
항상 `Context`를 사용함에 있어서 **가장 가까운** 스코프의 `Context`를 사용하면 된다.

<br/>

---

### 참고자료
https://blog.mindorks.com/understanding-context-in-android-application-330913e32514
