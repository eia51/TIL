# 안드로이드 이미지 로딩 최적화
안드로이드 앱을 개발하다보면 이미지를 로드해서 보여줘야하는 경우가 빈번하게 발생합니다. 어떤 포맷의 이미지를 어떤 방식을 사용해 로드하느냐에 따라 이미지의 품질, 렌더링 속도가 천차만별로 달라질 수 있습니다. 따라서 최상의 사용자 경험을 이끌어내기 위해선 이미지 로딩 방법에 대한 최적화를 고민해야합니다. 

안드로이드에서 이미지를 로딩하기에 앞서 고민해야 될 내용은 크게 다음과 같습니다.

> - **어떤 포맷의 이미지를 사용 할 것인가?**
>    - JPEG?
>    - PNG?
>    - WebP?
>
>
> - **어떤 방법으로 이미지를 로드 할 것인가?**
>    - 직접 이미지 로딩을 구현 할 것인가?
>    - 널리 알려진 이미지 라이브러리를 선택하여 사용 할 것인가?
>      - Glide 
>      - Picasso
>      - AUIL (Android Universal Image Loader)
>      - Volly
 
<br/>

## 보여질 이미지 포맷 선택하기
일반적으로 이미지 포맷하면 `JPEG` `PNG` 등의 포맷이 먼저 떠오를 것입니다.
양자화를 통해 픽셀 압축을 진행하는 손실 이미지 표준인 `JPEG`은 저장공간의 효율을 위해 사용되어왔고, 알파채널을 포함하는 비손실 이미지 표준인 `PNG`는 투명도를 포함해 더 다채로운 색을 표현하기 위해 사용되어져 왔습니다.

모두 이미지를 포맷팅하기 위한 훌륭한 방법들임에는 분명하지만 안드로이드 앱을 개발하고있는 여러분들에겐 더 나은 선택지가 존재합니다.

바로 2013년도 GoogleI/O에서 공개 된 `webp`라는 이미지 포맷입니다.

### WebP ?
`webp`는 2013 Google I/O에서 발표 된 손실/비손실 이미지 파일 포맷입니다. 비디오 코덱에서 프레임을 압축하는 방법과 동일한 방법으로 `predictive coding` 기법을 사용하여 이미지를 인코딩합니다. `predictive coding`은 인접 픽셀 블록 값을 사용해 블록의 값을 예측, 필요한 블록만 인코딩 하는 기법입니다.


`WebP`는 `png` 대비 26%의 크기를, `jpeg` 대비 34% 크기를 아끼지만, 거의 동등한 수준의 이미지 퀄리티를 보장합니다.

![](https://images.velog.io/images/eia51/post/7d96449c-ce20-4d90-ba8e-fba29e962757/webp.png)

![](https://images.velog.io/images/eia51/post/045e04bf-4599-4f74-bea4-daf6858c9cb8/webp2.png)

이렇듯 우리는 `webp`를 사용함으로써  **더욱 빠른 이미지의 렌더링**을 기대할 수 있게 됩니다.

`WebP` 포맷의 이미지 파일을 만들어보고 싶다면 [[링크]](https://developer.android.com/studio/write/convert-webp)를 참조 해보시길 바랍니다. 

> Lossy WebP(손실 압축)을 위해선 Android 4.0(API 14) 이상의 환경이 필요하며,
> Lossless/Transparent WebP (비손실 압축)을 위해선 Android 4.3(API 18) 이상의 환경이 필요합니다.

<br/>

## 이미지 로드할 방법 정하기
안드로이드에서 이미지 로딩을 하기 위해 로딩 라이브러리를 직접 구현 할 수도, 이미 만들어진 라이브러리를 사용 할 수도 있습니다.

이미지 로딩 라이브러리를 직접 구현하기 위해선 `안정적인 HTTP 통신` `OOM을 회피하는 비트맵 디코딩` `메모리/디스크 캐싱` `병렬처리` `실패처리` 등의 내용을 고민해야 합니다. 보다 자세한 내용이 궁금하다면, 이 [[링크]](https://d2.naver.com/helloworld/429368)를 참조 해보시길 바랍니다.

이 포스팅에서는 주로 사용되어지고 있는 이미지 로딩 라이브러리들에 대한 분석과 이를 통해 보다 나은 선택의 기준을 제시하는 것을 목표로 합니다.

먼저, <U>결론부터 정리</U>해드리겠습니다.

> **[ 이럴 때 `Glide`를 사용하세요 ]**
> - 이미지의 품질보다 렌더링 속도가 우선시 될 때
> - TODO..
>
> <br/>
>
> **[ 이럴 때 `Picasso`를 사용하세요 ]**
> - TODO.. 

<br/>

### 이미지 로딩 라이브러리 성능 비교
안드로이드에서 대표적으로 사용되고 있는 이미지 라이브러리로는 `Glide` `Picasso` `AUIL` `Volley` 등이 있습니다. 이 각각의 라이브러리의 성능을 비교한다면 다음과 같습니다. ([출처](![](https://images.velog.io/images/eia51/post/b0ce32d4-7191-4889-b1ea-9da41d6a8561/image.png)))
![](https://images.velog.io/images/eia51/post/7029f455-46d7-4c7a-bd09-dbe54a8e0f36/cmp_library.png)

- `Glide` : 스크롤 시 많은 메모리 사용, 빠른 로딩 속도
- `Picasso` : 기본적으로 많은 메모리를 사용, 스크롤 시 효율적인 메모리 관리
- `AUIL` : 메모리를 많이 사용하며, 낮은 로딩속도, 캐시 히팅률
- `Volley` : 스크롤 시 메모리를 많이 사용, 낮은 캐시 효율

이 실험을 통해 `Glide` `Picasso`가 안드로이드의 이미지 로딩 라이브러 중 비교적 좋은 효율을 낸다는 것을 알 수 있습니다.

<br/>

### Glide vs Picasso
`Glide`는 썸네일, GIF 로딩, 동영상 미리보기 기능까지 많은 기능을 지원하는 구글의 이미지 라이브러리입니다. 전체 이미지 라이브러리 중 가장 빠른 로딩속도를 자랑합니다.

`Picasso`는 `Glide`가 등장하기 전 안드로이드에서 이미지 로딩을 위해 사용되어지던 라이브러리입니다. 

#### 기본 사용법
기본적인 사용 방법은은 `Glide` `Picasso` 모두 같은 방식입니다. 앱모듈의 `build.gradle`에 다음 종속성을 추가함으로써 이 라이브러리들을 사용할 수 있습니다.

```java
dependencies { 
	//Glide
	implementation 'com.github.bumptech.glide:glide:4.10.0'
    
	//Picasso
	implementation 'com.squareup.picasso:picasso:2.71828'
}
```
```java
//Glide
Glide.with(context).load("이미지경로").into(imageView);

//Picasso
Picasso.with(context).load("이미지경로").into(imageView);
```
 
#### 기본 비트맵 포맷
기본적으로 `Glide`는 `RGB 565` 포맷을 사용하며, `Picasso`는 `ARGB 8888` 포맷을 사용합니다.

`RGB 565`은 이미지를 나타내기 위해 픽셀 당 2byte가 필요하며, `ARGB 8888`는  4byte를 필요로 합니다.

![](https://images.velog.io/images/eia51/post/13fa2715-3877-4d09-99f1-5d87204dfb6a/bitmap.png)

위 사진은 동일한 사진을 각각 `RGB 565` `ARGB 8888`로 나타낸 것입니다.
자세히 보면 `RGB 565`가 색의 음영이 지는 Gradient를 표시하는 부분에서 픽셀의 끊김 현상이 발생 하는 것을 볼 수 있습니다.

![](https://images.velog.io/images/eia51/post/19c2fb85-78e9-4ba6-a8a8-45451eb7c1d6/bitmap2.png)

<br/>


---

### 참고자료

**◇ WebP**

[1] https://developers.google.com/speed/webp/

[2] https://developer.android.com/studio/write/convert-webp

[3] https://developers.google.com/speed/webp/gallery

**◇ 이미지 로딩 라이브러리**

[4] https://d2.naver.com/helloworld/429368

[5] https://proandroiddev.com/how-to-optimize-memory-consumption-when-using-glide-9ac984cfe70f

[6] https://gun0912.tistory.com/19

[7] https://ebbnflow.tistory.com/176
