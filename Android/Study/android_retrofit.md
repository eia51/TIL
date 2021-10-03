## Retrofit?
Retrofit은 안드로이드에서 http 통신을 할 수 있도록 도와주는 라이브러리입니다. okhttp를 확장하여 만들어졌으며, 어노테이션 프로세싱을 통해 네트워킹에 필요한 코드들을 컴파일 타임에 자동으로 생성해주기 때문에 작성해야하는 코드의 양도 적고, 보다 직관적입니다.


## 프로젝트에 Retrofit 적용하기
### Gradle 추가
>최신 버전은 [Retrofit Github](https://github.com/square/retrofit)에서 확인 할 수 있습니다.

```java
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    
    // Data Converter
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0' //JSON 처리
    implementation 'com.squareup.retrofit2:converter-scalars:2.9.0' //String 처리
```
Retrofit을 사용하려면 위와 같은 Gradle을 추가해야 합니다. Retrofit은 응답 데이터 포맷에 따라 그에 맞는 converter를 등록해야합니다. 일반적으로 서버 통신 간 많이 사용 되는 포맷인 `JSON` 포맷의 응답 데이터를 객체로 변환하기 위해선 `gson converter`(두 번째 줄)를 사용해야하며, 객체로 변환 할 필요 없는, 단순한 `String` 포맷의 데이터를 처리하고자 한다면, `Scalar converter`를 사용합니다.

### Manifest 추가

```java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="_">
 
    <uses-permission android:name="android.permission.INTERNET"/> <----- 1번
 
    <application
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.SelfStudy_Kotlin"
        android:usesCleartextTraffic="true"> <----- 2번
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
 
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
 
</manifest>
```
1번은 서버와 통신하기 위해 인터넷 권한을 부여하는 코드이고, 2번은 https 프로토콜을 지원하지 않는 사이트에 대하여 http 프로토콜로 접근 해야할 때 이를 허용해주는 옵셔널한 코드입니다.


### Retrofit 객체 정의
Retrofit을 사용하려면 다음과 같은 형태로 객체를 생성해야합니다.
```java
 Retrofit retrofit = new Retrofit.Builder()   
    .baseUrl("서버 URL") //서버 url설정 (반드시 주소가 '/'로 끝나야함)
    //데이터 파싱 설정
    // JSON 데이터 처리 시 : GsonConverterFactory
    // String 데이터 처리 시 : ScalarConverterFactory
    .addConverterFactory(GsonConverterFactory.create())
    .build();
     
// 실제 통신을 위한 객체 인스턴스 생성
MyApi api = retrofit.create(MyApi.class)
```
일반적으로 앱에서 네트워킹은 특정 액티비티의 라이프사이클에 귀속되지 않고 어플리케이션 전반에 걸쳐 사용되어집니다. 게다가 이런 작업을 매번 수행 할 때마다 객체생성/해제 하기에는 문맥교환에 대한 비용이 크기 때문에 **어플리케이션 단에 `Singleton`으로 만들어두고 재사용**하는 것이 바람직합니다.

Retrofit에서 설정하는 `baseUrl`은 '/'로 끝나는 것을 원칙으로 하며, 이를 지키지 않을 시 컴파일타임에 에러가 발생합니다.


```java

public interface MyApi {
    @POST("/users/{userId}")
    Single<User> getUser(@Path("userId") long userId);
    
     @GET("/posts")
    Single<List<Post>> getPost();
}
```
Retrofit 객체를 인스턴스화 하기 위해선 위와 같이 `GET` `POST` 방식의 요청 정보를 포함하는 인터페이스를 실행 인자로 넘겨줘야 합니다.

Retrofit에서 `GET` `POST` 방식의 구분은 어노테이션으로 합니다.
위 코드의 `@POST("/users/{userId}")`라고 되어있는게 바로 어노테이션을 통해 `POST` 방식의 요청을 처리하도록 정의하는 부분입니다. 


### Retrofit 인스턴스의 API 호출
위와 같이 Retrofit 객체를 만들었다면 다음과 같이 실제 http 통신을 시작 할 수 있습니다.

```java
CompositeDisposable disposable = new CompositeDisposable();
disposable.add(api.getPost()	
	.subscribeOn(Schedulers.io())	// 1차적으로 네트워킹은 IO스레드에서
	.observeOn(AndroidSchedulers.mainThread()) // 네트워킹 이후의 처리는 mainThread에서
	.subscribe(최종 성공 처리, 최종 실패 처리)
);
```
`api.getPost()`가 Observable 한 객체를 반환하기 때문에, 위와 같이 `subscribe`를 통해 스트림을 생성하고 `Disposable` 형태로 발행 하여 http 통신을 수행 할 수 있습니다. 

<br/>

다음 예제는 서버에서 받은 `List<Post>` 형태의 자료를 `flatMapObservable`을 통해 반복이 가능한 `Iterable` 객체로 변환하고, 미리 준비 된 이벤트 리스너와 함께 객체에 매핑한 결과를 다시 리스트 형태로 가공하여 반환하는 예제입니다.

```java 
CompositeDisposable disposable = new CompositeDisposable();
disposable.add(api.getPost()	
	.flatMapObservable(Observable::fromIterable)
	.map(item -> new PostItem(item, eventListener))
	.toList()
	.doOnSuccess(중간 연산 성공 처리)
	.doOnError(중간 연산 실패 처리)
	.subscribeOn(Schedulers.io())	
	.observeOn(AndroidSchedulers.mainThread())
	.subscribe(최종 성공 처리, 최종 실패 처리)
);

```
단순히 서버에서 데이터를 받아 보여주는 것이 아닌, 중간에 역직렬화, 직렬화 과정이 포함 되어있습니다. 이렇게 중간 연산 과정이 추가되는 경우, `subscribe`에서 전체 흐름에 대한 성공/실패 처리를 하는 것과 별개로 중간 과정에 대한 성공/실패 처리가 필요 할 수 있습니다. 이 경우에 사용하는 것이 `doOnSuccess` `doOnError` 입니다.

---

### 참고자료
https://yuuj.tistory.com/174

https://todaycode.tistory.com/41

https://todaycode.tistory.com/38?category=979455
