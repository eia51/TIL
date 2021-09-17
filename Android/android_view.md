## View가 그려지는 과정
뷰는 포커스를 얻었을 때 레이아웃을 드로잉을 요청합니다. 레이아웃 드로잉은 Root가 되는 ViewGroup에서 시작되어 xml 트리 구조를 따라 전위순회방식으로 그려지게 됩니다.

뷰는 다음과 같이 `measure` `layout` `draw` 단계를 거쳐 그려집니다.

![](https://images.velog.io/images/eia51/post/f28b2ffa-3ed8-44f2-a253-8d79e218a016/view_drawing.png)
### ◆ onAttachToWindow() 
부모뷰에 의해 `addView()`가 호출 된 후 자식뷴느 윈도우에 붙게됩니다. 이 시점부터 뷰의 `id`를 통한 접근이 가능해집니다.

<br/>

### ◆ measure(int widhMeasureSpec, int heightMeasureSpec)
뷰의 크기와 좌표를 알아내고자 할 때 호출 됩니다. 뷰의 실질적인 크기 측정은 `measure()`가 아닌, `measure()` 안에 래핑 되어있는 `onMeasure()`에서 `MeasureSpec`에 기반하여 수행됩니다.

`measure()`에서는 `onMeasure()`를 통해 직전에 측정 된 값을 캐싱합니다. 이를 통해 반복적인 호출이 발생하는 상황에서 보다 빠른 처리가 가능하게 됩니다. 강제로 뷰의 크기 측정이 다시 필요해지는 시점이 있을 수 있는데, 그럴 때는 `foreceLayout flag`를 설정하여 캐싱 된 값을 사용하지 않고 다시 측정할 수 있습니다. 이 flag는 `requestLayout()`을 통해 설정 가능합니다.

측정 과정에서 부모와 자식뷰의 크기 정보를 전달하기 위해 2가지 클래스를 사용합니다.

> - **ViewGroup.LayoutParams**
>   ㆍ 자식뷰가 부모뷰에게 자신이 측정될 방법을 알릴 때 사용합니다.
>   ㆍ ViewGroup의 종류별로 다른 LayoutParams가 존재할 수 있습니다.
>   ㆍ LayoutParams의 속성은 `숫자` `MATCH_PARENT` `WRAP_CONTENT`로 나뉩니다.
>
> <br/>
>
> - **ViewGroup.MeasureSpec**
>   부모뷰가 자식뷰에게 요구사항을 전달하기 위해 사용합니다.
>   ㆍ **`UNSPECIFIED`** 부모뷰는 자식뷰가 원하는대로 치수를 결정
>   ㆍ `EXACTLY` 부모뷰가 자식뷰에게 정확한 크기를 강요
>   ㆍ `ATMOST` 부모뷰가 자식뷰에게 최대 크기를 강요

<br/>

### ◆ layout(int left, int top, int right, int bottom)
뷰의 위치를 배치하기 위해 호출됩니다. `measure()`에 의해 각 뷰에 측정되어 저장 된 크기와 좌표 정보를 가지고 화면에서 뷰가 보여질 위치를 결정합니다. `layout()`에서는 뷰의 위치가 변경 되었는지 검사를 수행하며, 이 검사 결과 위치 변경이 확인되었다면 `onLayout()`을 호출하여 위치를 다시 결정하게 됩니다. 

<br/>

### ◆ draw(Canvas canvas)
뷰가 그려지기 위해 필요한 작업들을 다음과 같은 순서대로 수행합니다.

- 백그라운드 드로잉
- 뷰의 그림자 효과를 위한 캔버스 캐싱
- 뷰 드로잉 (`onDraw()` 호출)
- 자식뷰 드로잉
- 뷰 그림자 드로잉
- `Paint`를 통한 뷰의 색상 설정

실질적인 뷰의 드로잉은 `onDraw()`에서 호출됩니다. `onDraw()`는 `Canvas`를 메소드의 파라미터로 제공받아 뷰의 모양을 그립니다.

<br/>

## View의 업데이트
뷰의 라이프사이클 중 `invalidate()`와 `requestLayout()`을 찾아볼 수 있습니다. 이 메소드들은 `Runtime` 시점에 뷰를 다시 그릴 수 있도록 합니다.

### ◆ invalidate()
> 뷰의 그래픽이 변경되었을 때 호출합니다.

뷰를 측정이나 위치 변경 없이 다시 그리고 싶을 때 사용합니다. 뷰에 포함되는 `Text` 혹은 `Color`가 변경되었을 때, 사용자의 터치로 인해 뷰의 그래픽적인 요소가 바뀌어야 할 때 `onDraw()`를 재호출하면서 뷰를 업데이트 합니다.

<br/>

### ◆ requestLayout
> 뷰의 크기, 위치가 변경되었을 때 호출합니다.

`onMeasure()` 부터 시작해서 뷰를 다시 그립니다. 뷰의 사이즈가 변경되었을 때, 그 변경 된 크기 정보와 위치를 다시 계산해야하는 상황에서 사용합니다.

<br/>

---

#### 참고한 내용

[현우의 개발노트](https://hyeonu1258.github.io/2018/03/26/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%20%EB%A9%B4%EC%A0%91/)

[뷰 그려지는 과정 사진](https://jungwoon.github.io/android/2019/10/02/How-to-draw-View.html)
