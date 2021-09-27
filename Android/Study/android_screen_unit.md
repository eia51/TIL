
## px (pixel)
화면을 구성하는 최소 단위이자, 절대적인 표시 단위입니다.
앱이 단일 해상도만 지원해도 된다면 `px`를 기본 단위로도 사용할 수 있으나, 다양한 해상도를 지원해야하는 안드로이드 특성 상 `px`보다는 `dp` 단위를 사용해야합니다.

## dpi (dot per inch)
1인치에 들어가는 픽셀의 수를 나타내는 픽셀 밀도입니다. 안드로이드에서 기본으로 사용되는 dpi는160dpi(=mdpi)이며, 그 외에도 주로 사용되는 dpi는 다음과 같습니다.
![](https://images.velog.io/images/eia51/post/dc5e517f-a59c-407b-986c-7aa6765af499/FA379CC2-8429-4DD8-B46D-40A65EBBD387.png)

## dp (dip)
다양한 해상도의 기기를 지원해야 하는 안드로이드에서 화면의 크기가 달라도 화면에 같은 비율로 보여주기 위해 사용하는 단위입니다. 1dp는 mdpi(기준밀도)에서 1px과 같은 수치입니다.

## dp 단위를 px 단위로 변환
> px = dp * (단말dpi / 기준밀도(=mdpi))
> dp = px * (기준밀도(=mdpi) / 단말dpi)

#### dpi, density 구하기
```java
DisplayMetrics displayMetrics= new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

int dpi = displayMetrics.densityDpi;
float density =  displayMetrics.density;
```

#### dp → px 변환하기
```java
public float dpToPx(Context context, float dp) {
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
}
```

#### px → db 변환하기
```java
public float pxToDp(Context context, float px) {
    // 해상도 마다 다른 density 를 반환. xxxhdpi는 density = 4
    float density = context.getResources().getDisplayMetrics().density;

    //xxxhdpi (density = 4)기준으로 density 값을 재설정 한다
    if (density == 1.0)      // mpdi  (160dpi)
        density *= 4.0;
    else if (density == 1.5) // hdpi  (240dpi)
        density *= (8 / 3);
    else if (density == 2.0) // xhdpi (320dpi)
        density *= 2.0;

    return px / density;     // dp 값 반환
}
```
---

#### 참고자료
[px dp dpi - 1](http://design.gabia.com/wordpress/?p=33289)
[px dp dpi - 2](https://blog.cracker9.io/2018/03/13/Android_DPI/)
[dp px 변환](https://inma.tistory.com/72)
