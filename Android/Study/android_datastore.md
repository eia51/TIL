## DataStore

- `Protocol Buffer`를 사용하여 `Key-Value`데이터나 `Typed Objects`를 저장할 수 있다.

- `DataStore`는 `Coroutine + Flow`를 사용하여 비동기적이고 일관적인 트랜잭션 방식으로 데이터를 저장한다.

- `DataStore`는 `Preferences DataStore`과 `Proto DataStore` 두 가지로 분류된다.

- **SharedPreferences를 대체**하는 새로운 데이터 저장수단이다.

<br/>

## DataStore과 SharedPreference
![](https://images.velog.io/images/eia51/post/4a0f617c-0c82-44ab-9d16-b5ea31f01620/sp.png)

`DataStore`과 `SharedPreference`를 비교하면 위와 같다.

위 내용을 요약하자면,

- `Flow`를 사용하여 I/O에 대한 비동기 처리가 가능.
- `Dispatcher.IO` 하위에서 동작하여 `MainThread`에서 호출되어도 안전함.
- `RuntimeException`으로부터 안전함

이젠 더 이상 `SharedPreference`의 사용을 고집 할 필요가 없을 것 같다.

<br/>


## DataStore 사용 방법

### 의존성 추가
앱모듈의 `build.gradle`에 다음 내용 추가
```kotlin
dependencies {
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")
}
```

<br/>

### DataStore 객체 생성
```kotlin
class DataStoreModule(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "dataStore")

    private val myStringKey = stringPreferencesKey("keyName") 
    private val myIntKey = intPreferencesKey("keyName") 
}
```
`DataStore`에서 사용하는 키 값은 `사용할타입PreferencesKey("keyName")`과 같은 형태로 선언 할 수 있다. 위의 예시는 각각 `string` `int` 타입의 데이터를 저장하기 위한 예시이다.

<br/>

### Data를 읽는 Flow 만들기
코루틴의 `Flow`를 사용하여 `DataStore`에서 데이터를 읽어올 때 해당 데이터를 `Flow`객체로 전달한다.

```kotlin
// 위에서 만든 `myStringKey` Key 값에 대응하는 Value 반환
val textData: Flow<String> =
    context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[myStringKey] ?: ""  // 위에서 만든 Key
        }
```

- `map()`을 활용하여 `myStringKey`에 대응하는 Value를 Flow 형태로 가져오게 된다.

- `catch()`를 사용하여 데이터 읽는 과정에서 문제가 생겼을 때 예외처리를 해줄 수 있다.

<br/>

### 데이터 가져오기
`DataStore`에서 읽은 데이터를 `TextView`에 적용해보도록한다. (다음 작업은 `CoroutineScope` 내에서 수행되어야한다.)

```kotlin
CoroutineScope(Dispatchers.Main).launch {
    MyApplication.getInstance().getDataStore().textData.collect {
        textView.text = it
    }
}
```

<br/>

### 데이터 저장하기
데이터를 쓰기 위해선 `edit()`를 사용한다. 해당 작업은 비동기적으로 동작하므로 `suspend` 키워드를 통해 코루틴 영역에서 동작할 수 있도록 한다.

```kotlin
// String 값을 `stringKey` 의 Value 로 저장
suspend fun setTextData(text: String) {
    context.dataStore.edit { preferences ->
        preferences[stringKey] = text  // 아까 만든 Key 이용
    }
}

```

위와 같이 데이터를 쓰기 위한 `suspend` 함수를 만들었다면, 다음과 같이 호출하여 사용 할 수 있다.

```kotlin
CoroutineScope(Dispatchers.Main).launch {
    MyApplication.getInstance().getDataStore().setTextData("H43RO")
}
```

> `DataStore`를 사용하면서 비동기 처리가 아닌 동기 처리를 하고 싶다면, 다음와 같이 사용 할 수 있다.
>
>```kotlin
>runBlocking {
>    val text = MyApplication.getInstance().getDataStore().textData.first()
>}
>```
> 
> 다만, `runBlocking` 안에서 무거운 작업은 피해야한다. 블로킹 되는 시간이 길어짐에 따라 프레임 드랍으로 인한 화면 버벅임(16ms), ANR(5s)로 이어질 수 있다.


<br/>

## 결론
앞으론 `SharedPreference` 대신 `DataStore`를 사용하자

![](https://images.velog.io/images/eia51/post/8108dd3d-8c53-4b27-b772-b6ee8f93d067/end.png)

---

### 참고자료

https://android-developers.googleblog.com/2020/09/prefer-storing-data-with-jetpack.html

https://developer.android.com/topic/libraries/architecture/datastore?gclid=Cj0KCQjwqKuKBhCxARIsACf4XuHSV6c0dQKCbCAO0rH42Pc-MFbVKxhgf1YRYxu2qf_yPmkeU5m3WfoaAqfKEALw_wcB&gclsrc=aw.ds#kts

https://android-developers.googleblog.com/2020/09/prefer-storing-data-with-jetpack.html

https://velog.io/@haero_kim/Android-DataStore-%EC%B2%AB-%EC%9D%B8%EC%83%81-%EC%82%B4%ED%8E%B4%EB%B3%B4%EA%B8%B0
