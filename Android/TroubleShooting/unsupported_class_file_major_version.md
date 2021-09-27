## 안드로이드 Unsupported class file major version 에러
안드로이드 개발환경 (Android Studio 2020.3.1.24버전, jdk 17버전)을 업데이트 한 이후로 앱이 빌드 되지 않는 문제가 생겼습니다.

IDE에 출력되는 에러는 다음과 같습니다.

![](https://images.velog.io/images/eia51/post/22694598-2e20-450a-8322-0fae2ebbc236/jdk.png)

`Unsupported class file major version 61`

이 메세지는 Android의 `Gradle` 시스템이 현재 적용 된 `jdk` 버전과 호환 되지 않을 때 발생하는 문제입니다.

저의 경우 `jdk ver.17`을 적용하려고 했기 때문에 오류 메세지의 major 버전이 61로 나온 것이며, 이 버전 숫자는 적용하려고 하는 `jdk` 버전에 따라 다르게 나옵니다.


### 해결 방법
현재 `Gradle`에서 안정적으로 지원하는 `jdk` 버전은 11이며, 개발환경의 `jdk`를 11버전으로 다운그레이드 하면 이 문제가 해결됩니다.



1. `jdk ver.11` 다운로드
  [[여기]](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)로 이동해서 본인의 OS에 맞는 11버전의 `jdk`를 다운로드 받습니다.
  ![](https://images.velog.io/images/eia51/post/5705a2c4-434f-4498-9dba-91ed66cf79e7/jdk_sol1.png)
  
  
2. 안드로이드 스튜디오에서 다운그레이드 한 `jdk`를 연동해줍니다
  (`File` → `Settings` → `Build, Execution, Deployment` → `Build Tools` → `Gradle`)
  ![](https://images.velog.io/images/eia51/post/3fcffe87-5cb3-4d68-b0ec-bf258e4e6ad1/jdk_sol.png)

 
 3. `jdk`가 적용되기 위한 약간의 시간이 지난 뒤 여러분들의 앱은 다시 정상적으로 빌드가 가능해지게 됩니다!
