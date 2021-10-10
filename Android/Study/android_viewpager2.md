# ViewPager2

대부분 앱의 메인페이지는 Feed 형식으로 이뤄져 있습니다.

사용자들한테 정보를 많이 보여주기 위하여 `RecyclerView`안에 다양한 `ViewHolder`가 존재하는 형태로 이뤄져 있습니다.

이 때 횡으로 플립핑 하는 형태의 `ViewHolder`를 구현하려면 `ViewPager`를 사용해야 합니다.

하지만 `RecyclerView`에 `ViewPager`를 집어넣었을 경우 문제점이 발생하게 됩니다.

`ViewPager`는 `PagerAdapter` 기반으로 구성되어있는데 스크롤을 진행할 때 마다 `instantiateItem()` 와 `destroyItem()` 메서드가 호출 되기 때문에 스크롤 할 때 버벅거리는 현상이 나타납니다.

이러한 문제로 인해 `ViewPager`를 사용하지 않고 수직 `RecyclerView`에 수평 `RecyclerView`를 넣고 Pager의 느낌을 내기 위하여 `PagerSnapHelper`를 커스텀해서 사용 하는 경우가 빈번했습니다.

`PagerSnapHelper`를 커스텀해서 사용하는 것은 비용이 큰 작업입니다. 

그러나 우리는 새로운 `ViewPager2` 덕분에 더 이상 이런 작업들을 직접 할 필요가 없게 되었습니다! `RecyclerView`를 기반으로 만들어진 컴포넌트이며, 내부적으로 `PageSnapHelper`를 이미 구현되어 있습니다.

<br/>

## ViewPager2 뭐가 달라졌을까?

다음 그림을 통해 `ViewPager2`는 `ViewPager`와 다르게 `RecyclerView`를 기반으로 만들어진 컴포넌트라는것을 확인할 수 있습니다.

![](https://images.velog.io/images/eia51/post/9b0d58a5-02c2-459c-bcdb-6121d3404409/vp2.png)

`ViewPager2`는 `ViewGroup`을 상속받았고 `initialize` 메서드에서 `RecyclerView`를 생성하는 것을 확인 할 수 있습니다.

```java
public final class ViewPager2 extends ViewGroup {
    private void initialize(Context context, AttributeSet attrs) {
        mRecyclerView = new RecyclerViewImpl(context);
        mLayoutManager = new LinearLayoutManagerImpl(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPagerSnapHelper = new PagerSnapHelperImpl();
        mPagerSnapHelper.attachToRecyclerView(mRecyclerView);
        . . .
	}
}
```

그리고 `LinearLayoutManager`와 `PageSnapHelper`를 설정하는것을 확인 할 수 있습니다.

때문에 `ViewPager2`에서는 `RecylerView.Adapter`의 기능들을 이용 할 수 있습니다.

한 가지 아쉬운 점은 `ViewPager2`는 `final class`로 선언되어있기 때문에 `Custom ViewPager2`를 만들 수 없습니다.

이어서 `ViewPager2`에 새롭게 추가된 기능은 다음과 같습니다.

- RTL (right to left) layout support
- Vertical orientation support
- Reliable Fragment support
- Dataset change animations

이제 `ViewPager2`를 적용하는 방법에 대하여 알아보도록 하겠습니다.

<br/>

## ViewPager2 프로젝트에 적용하기

`ViewPager2`를 적용하기 위하여 앱모듈의 `build.gradle`에 다음 의존성을 추가합니다.

```java
dependencies {
    implementation "androidx.viewpager2:viewpager2:1.0.0"
}
```

그리고 `layout.xml` 파일에 `ViewPager2`를 설정합니다

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.viewpager2.widget.ViewPager2 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/viewPager"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

`RecyclerView.Adapter`를 설정하듯이 `Adapter`를 다음과 같이 설정 합니다.

```kotlin
class MyAdapyer(var items: ArrayList<String> = arrayListOf()): 
					RecyclerView.Adapter<ViewHolder>() {
  
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
		.inflate(R.layout.list_item, parent, false))
    }
  
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(items[position])
    }
  
    override fun getItemCount(): Int = items.size
    
    ...
}

```
위와 같이 만들어진 어댑터는 다음과 같이 적용 할 수 있습니다.
```kotlin
viewPager.adapter = MyAdapter() 
```

이렇게 `ViewPager2`를 프로젝트에 적용하기 위한 간단한 방법들을 소개 해드렸고, 이어서 `ViewPager2`에서 변경 된 <U>부가 기능</U>에 대해서 설명 드리겠습니다.


#### 수직 방향 스크롤
- `layout` 파일에서 `android:orientation="vertical"`과 같이 적용 할 수 있습니다.
- `Programmtically` 한 설정은 `viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL`과 같습니다.

#### 양쪽 페이지 미리보기
`ViewPager`에서 양쪽 페이지를 미리보는 기능을 만들려면, `setPageMargin()` 메서드를 호출하여 설정했습니다.

하지만 `ViewPager2`에서는 `setPageMargin()` 메서드가 존재 하지 않습니다.

`ViewPager2`에서 양쪽 페이지를 미리보는 기능을 만들기 위해선 다음과 같이 작업해야 합니다.

```
android:clipToPadding="false"
android:clipChildren="false"
```

```kotlin
val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pagerWidth)
val screenWidth = resources.displayMetrics.widthPixels
val offsetPx = screenWidth - pageMarginPx - pagerWidth

viewPager.setPageTransformer { page, position ->
    page.translationX = position * -offsetPx
}
```

위 코드에서는 page offset을 적용하기 위해 현재 보여지고 있는 screen width에서 page 간의 margin과 width 값을 빼서 offset을 구했습니다.

그냥 상수를 적용해도 되긴 하지만, 그렇게 할 경우 여러 기기에서 보여지는 UI가 제각각이 될 수 있기 때문에 위와 같이 적용했습니다.

<br/>

## ViewPager2 사용 시 주의사항
`ViewPager2`를 적용 할 때 몇가지 주의 해야 할 사항이 있습니다.

- ### Pages must fill the whole ViewPager2 (use match_parent)
  `ViewPager2`의 ChildView를 `inflate` 할 경우에 attachToRoot 값은 `false`여야 하고 width, height 값은 `match_parent` 여야 합니다.

   ```kotlin
   LayoutInflater.from(context).inflate(resource, this, attachToRoot)
   ```  

   그렇지 않을 경우 다음과 같은 에러 메세지가 뜹니다.

   ```java
   java.lang.IllegalStateException: Pages must fill the whole ViewPager2 (use match_parent) 
     at androidx.viewpager2.widget.ViewPager2$2.onChildViewAttachedToWindow(ViewPager2.java:170)
   ```

   이런 에러가 발생하는 이유는 `ViewPager2`가 `initialize` 할 때 `addOnChildAttachStateChangeListener`를 설정 하는데 이 과정에서 `enforceChildFillListener`를 등록하게 됩니다. 

   해당 메서드를 살펴보면, `layoutParam`의 `width` 또는 `height`가 `match_parent`가 아닐 경우 예외를 발생시키도록 설정 되어 있습니다.

   ```java
   private RecyclerView.OnChildAttachStateChangeListener enforceChildFillListener() {
       return new RecyclerView.OnChildAttachStateChangeListener() {
           @Override
           public void onChildViewAttachedToWindow(@NonNull View view) {
               RecyclerView.LayoutParams layoutParams =
                       (RecyclerView.LayoutParams) view.getLayoutParams();
               if (layoutParams.width != LayoutParams.MATCH_PARENT
                       || layoutParams.height != LayoutParams.MATCH_PARENT) {
                   throw new IllegalStateException("Pages must fill the whole ViewPager2 (use match_parent)");
               }
           }
       };
   }
   ```
   때문에 ChildView의 width, height는 `match_parent`로 설정 되어야 합니다.

<br/>

- ### PageTransformer 설정 뒤 notifyDataSetChanged() 호출 시 View가 깨지는 문제

   `ViewPager2`에 `PageTransformer`을 설정 한 뒤, 데이터를 추가하고나서 `notifyDataSetChanged()` 메서드를 호출 하면 뷰가 깨지는 현상이 발생합니다.

   그 이유는 `notifyDataSetChanged()` 메서드를 호출 하게 되면 `LayoutManager는` 강제로 현재 보이는 모든 `View`들에 대해서 `rebind` `relayout`을 수행하게 되는데, 이 과정에서 `PageTransformer`의 설정이 다시 적용되지 않기 때문입니다.

   전체를 갱신 하는 `notifyDataSetChanged()` 메서드 대신에 다음과 같이 부분 갱신을 수행하는 메서드를 호출하게 되면 정상적으로 작동하는것을 확인할 수 있습니다.

   - `notifyItemChanged(int)`
   - `notifyItemRangeChanged(int, int)`

<br/>

## 결론
- `RecyclerView` 안에서 `ViewPager`를 써야한다면 대신 `ViewPager2`를 사용해보자.
- 경우에 따라 `ViewPager2`와 `RecyclerView`의 전환이 용아하기 때문에 변화에 대응하기 유리하다.

<br/>

### 참고 자료
- https://developer.android.com/jetpack/androidx/releases/viewpager2?hl=ko
- https://medium.com/google-developer-experts/exploring-the-view-pager-2-86dbce06ff71
- https://sudonull.com/post/30180-How-to-work-with-ViewPager2

