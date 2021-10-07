# 안드로이드 저장소 관리

모든 안드로이드 기기에는 파일을 저장할 수 있는 저장소가 있습니다.

저장소는 크게 외부 저장소(External Storage)와 내부 저장소(Internal Storage)로 나뉩니다. 일반적으로 내부 저장소는 안드로이드 기기 자체에서 제공하는 비휘발성 내부 메모리를 의미하고 외부 저장소는 마이크로 SD카드와 같은 이동식 저장소를 의미합니다.

현재 안드로이드의 외부 저장소는 다시 두 분류로 나뉩니다. 안드로이드 Q(Android 10, SDK 29)를 기준으로 Q버전 이전의 Legacy Storage와 Q버전 이후의 Scoped Storage로 나눠지게 됩니다.

오늘은 이 각각의 저장소에 대하여 어떻게 접근하고 관리할 수 있는지 그 방법을 알아보고자 합니다.

<br/>

## 내부 저장소 (Internal Storage)
내부 저장소는 모든 안드로이드 기기에 포함되는 저장 공간입니다. 앱을 설치하게 되면 해당 앱을 위한 별도의 저장공간이 할당 됩니다. 이 공간은 샌드박스 형식으로 되어있어서 해당 앱만 접근 할 수 있고, 외부에서는 볼 수 없습니다. 앱이 삭제 될 때 이 공간 또한 같이 삭제 됩니다.


<br/>

## 외부 저장소 (External Storage)
앱의 삭제 여부와 관계 없이 영구적으로 파일을 저장하기 위해 사용됩니다. 대부분의 기기에는 외부 저장소가 있지만 모든 기기에서 제공 되는 것이 아니기 때문에 사용 전 이를 확인하는 코드를 작성해야합니다. 

- ### Legacy Storage
  
  안드로이드 Q 이전(Android 9, SDK 28 이하)의 외부 저장소는 다음과 같은 구조로 정의 되어 있었습니다.
  
   **`공용 공간`**에 파일을 저장하여 타 앱 간 데이터를 공유 할 수도 있었으며, **`개별 앱 공간`**에 파일을 저장하여 데이터는 공유하지 않으면서 앱이 삭제 되어도 데이터를 보존 할 수 있도록 전략을 세울 수도 있었습니다.
  
   외부 저장소에 접근하기 위해선 안드로이드 M(Android 6, SDK 23) 이상인 경우 `Manifest.xml`에 다음과 같은 권한을 추가하고, 런타임에서 위험 권한을 부여받아야합니다. 런타임에서 위험 권한을 부여받는 방법은 [[여기]](https://gun0912.tistory.com/55)를 참고하세요. 
   
  ```
  //Manifest.xml
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  ```
   
  ![](https://images.velog.io/images/eia51/post/ead998f7-972e-4492-a95d-3a7e25adb0fd/q_before.png)
 
  - #### 개별 앱 공간 접근 방법 
   `/sdcard/Android/data/패키지명` 또는 `context.getExternalFilesDir(null)`
   
   - #### 공용 공간 접근 방법 
   `/sdcard` 또는 `Environment.getExternalStorageDirectory().getPath()`   

<br/>

- ### Scoped Storage

  기존에는 공용공간 안에 모든 파일이 저장 되었다면, 안드로이드 Q 이후(Android 10, SDK 29 이상)에서는 개별 앱 공간이 샌드박스 형태로 보호되어있고, 공용공간 또한 타입별로 분리되었습니다. 개발 앱 공간은 앱 삭제 시 함께 제거 되고, 공용공간은 앱이 삭제 되어도 기기에 남아있습니다.

  ![](https://images.velog.io/images/eia51/post/c4b3c261-6f8b-4b17-9fa1-562fba7218ce/q_after.png)

 - #### 개별 앱 공간 접근 방법 
   Q 이후의 경우, 개별 앱 공간을 사용하는데에는 따로 권한 요청이 필요하지 않습니다.
   `context.getExternalFilesDir()`를 통해 자신의 앱 공간에만 접근 할 수 있습니다.
   
 - #### 공용 공간 접근 방법 
   안드로이드 버전 10부터는 `MediaStore api`의 사용을 권장하고 있습니다. `MediaStore`은 사용자가 가지고 있는 파일들을 다른 앱에서도 사용 할 수 있도록 설계 된 api 입니다. `Media` 디렉토리 안에서 자신의 앱에 해당하는 곳은 **권한 없이** 사용 할 수 있습니다.
   
   이전 버전에서는 `WRITE_EXTERNAL_STORAGE`만 있으면 다른 앱의 공용 파일에도 접근이 가능했지만, 보안상의 이슈로 10부터는 이를 막아놓은 것 입니다.
   
   다음은 `MediaStore` 샘플입니다. (***다른 앱의 `MediaStore`를 사용하려면 접근 권한이 필요합니다!***)
```java
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "파일 명");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain"); 
        //더 많은 MIME TYPE = https://www.freeformatter.com/mime-types-list.html
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "Download");
        //Download, DCIM 등의 기본 공유공간 이름을 넣어야함. 그 외에는 모두 권한 에러 발생

        ContentResolver resolver = getContentResolver();
        Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues);
        try {
            String wantToWriteFile = "안드로이드";
            resolver.openOutputStream(uri).write(wantToWriteFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
```

 - #### 공용 공간 접근 방법
   접근 권한 없이 `Storage Access Framework`와 시스템 파일 선택기를 통해 사용자가 파일을 명시적으로 핸들링 할 수도 있습니다.
   
   `Stroage Access Framework`는 선택형 UI를 화면에 띄워서 앱에서 파일에 접근할 수 있도록 합니다. `Intent.ACTION_OPEN_DOCUMENT` 액션을 사용해 새로운 액티비티를 띄우는 방식입니다.
   
```java
        int REQ_CODE = 1;
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(i, REQ_CODE);
```

