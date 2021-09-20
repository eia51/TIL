# 의도하지 않은 Activity 종료에 대응하기 

안드로이드에서는 보통 다음과 같은 상황에서 `Activity`가 종료 됩니다.
>- 사용자의 뒤로가기 동작을 통한 수동 종료
>
>
>- `finish()` 호출을 통한 프로그램적인 종료
>
>
>- **시스템에 의한 종료**
>   - 화면이 회전 될 때 
>   - 메모리가 부족 할 때
 
시스템에 의한 종료는 사용자, 개발자 그 누구도 의도하지 않은 부분입니다. 시스템에 의해 Activity가 종료 된다면 다시 시작 되는 시점에서 이전의 데이터들이 모두 소실되기 때문에 만족스러운 사용자 경험을 위해선 이전 데이터를 복구해주는 부분이 고려되어야 합니다.

오늘은 안드로이드에 시스템에 의해 Activity 종료되는 경우, 데이터를 백업/복구 하는 방법에 대해 간략히 소개하겠습니다.

<br/>

## onSaveInstanceState(Bundle bd)
시스템에 의해 Activity가 강제로 종료되는 상황에 대비하기 위한 데이터 백업 콜백입니다. 파라미터로 오는 `Bundle` 객체에 Activity의 상태에 대한 단서 데이터를 `(key, value)` 형태로 백업 할 수 있습니다. 

```java
2021-09-19 22:47:38 I/TAG_SAVE: onPause()
2021-09-19 22:47:38 I/TAG_SAVE: onStop()
2021-09-19 22:47:38 I/TAG_SAVE: onSaveInstanceState() //데이터 백업 수행
```

`onSaveInstanceState(Bundle)`는 위 라이프사이클 로그에서 볼 수 있듯이 Activity가 화면에서 완전히 가려지는 시점에 호출되어 사용자 데이터를 백업합니다.

<br/>

## onRestoreInstanceState(Bundle bd)
시스템에 의해 Activity가 종료되었을 때, 이전 상태를 복구하기 위한 복구 콜백입니다. 파라미터로 전달 되는 `Bundle` 객체에서 상태 복구에 필요한 데이터를 참조할 수 있습니다. 이 데이터는 사전 `onSaveInstance()`에서 백업해놓은 `(key, value)` 형식의 데이터입니다.

```java
2021-09-19 23:14:15 I/TAG_SAVE: onCreate()
2021-09-19 23:14:15 I/TAG_SAVE: onStart()
2021-09-19 23:14:15 I/TAG_SAVE: onRestoreInstanceState()
2021-09-19 23:14:15 I/TAG_SAVE: onResume()
```

`onRestoreInstanceState(Bundle)`은 `onStart()`와 `onResume()`의 사이에서 호출 됩니다. 항상 호출되는 라이프사이클 개념이 아닌, 시스템에 의해 종료가 발생 된 경우에만 특별히 호출되는 이벤트성 콜백입니다.

<br/>

## Sample Code
```java 
in OOOActivity.java

@Override
protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("백업 키", "백업 할 데이터");
}

@Override
protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    String data = savedInstanceState.getString("백업 키");
    // 복구 된 'data'를 기반으로 화면에 유의미한 데이터 출력
}
```
