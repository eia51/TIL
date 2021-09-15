## ◆ 컴포넌트(Component) ?
컴포넌트는 앱에서 **독립적으로 실행 될 수 있는** 구성 단위이며, 시스템에 의해 생명주기가 관리됩니다. 안드로이드에서 '**4대 컴포넌트**'라는 키워드는 기본이라고 할 수 있을 만큼 중요한 개념이며, 앱을 개발하기에 앞서서 이들 각각의 역할과 생명주기에 대한 이해가 선행되어야 합니다.

컴포넌트는 독립적인 형태로 존재하며, 각각 고유한 기능을 수행하면서 인텐트를 통해 상호작용을 합니다.

안드로이드에서 다루는 컴포넌트는 **`Activity` `Service` `Content Provider` `Boradcast Receiver`** 이렇게 네 가지 입니다. 

많이들 혼동하는 개념이 있는데, 안드로이드에는 앞서 말씀드린 컴포넌트 이외에도 다양한 구성요소가 있습니다. 이를테면 `Dialog` `Fragment` 같은 개념이 되겠네요.

그러나 이와 같은 요소들은 컴포넌트라고 부를 수 없습니다. 컴포넌트는 안드로이드에서 **독립적인 실행이 가능한 구성 단위**라고 정의하고 있으며, `Dialog` `Fragment`는 그 자체적으로 실행 가능한 단위가 아니라, `Activity` `Service`에 의존해서 실행 되는 **'하위 종속'**의 관계이기 때문입니다.


## ◆ Activity 
액티비티는 사용자가 어플리케이션과 상호작용하는 단일 화면, 즉 인터페이스입니다. 모든 안드로이드 어플리케이션은 반드시 하나 이상의 액티비티를 포함하고 있으며, 이 액티비티의 `LifeCycle` 내에서 사용자가 원하는 기능들을 수행할 수 있습니다.

### ◇ Acvitity가 실행 되는 과정
그러면 이 액티비티는 어떤 원리로 실행되는걸까요?
그 과정에 대해 간단히 설명드리겠습니다. 

![](https://images.velog.io/images/eia51/post/3cf8ff84-5295-40f3-bbee-e9c5547731d5/activity_flow.png)

1. 사용자의 입력 혹은 시스템 런처에 의해 앱이 실행되는 시점에 `Intent`를 통해 `System(Activity Manager)`에 실행 할 앱의 정보가 전달됩니다. 

2. `Zygote`가 준비해놓은 클래스, 리소스를 복제하여 `Activity Thread`가 새로운 `Activity`를 바인딩해내게 됩니다.

>`Activity Thread`는 우리가 흔히 'UI를 그리는 스레드다' 라고 알고 있는 `Main Thread`와 같은 개념입니다.
>
>`Zygote`는 COW를 통해 프로세스 재사용성을 높이고, 메모리 사용을 최소화하여 액티비티가 빠르게 실행될 수 있도록 해주는 프로세스입니다.
>
>이 각각에 대한 내용 역시 중요한 부분이기 때문에, 추후 다른 포스팅을 통해 전해드리도록 하겠습니다.

### ◇ Activity의 Lifecycle
안드로이드 앱을 개발하기 위해서 `Activity Lifecycle`에 대한 이해가 반드시 필요합니다. 우리는 평소 스마트폰의 앱을 사용하면서 자연스럽게 앱에 대한 `사용자 경험`에 대해 학습하고 있습니다. 어떤 기능은 앱이 시작되는 순간 같이 실행 되길 기대하고, 또 어떤 기능은 앱이 숨게 되는 순간 더 이상 실행 되지 않기를 기대하곤 합니다.  앱을 개발하는데 있어서 이러한 사용자 경험이 세세하게 고려되지 않는다면 여러분들의 앱을 사용하는 과정에서 사용자들은 불편한 경험을 하게 될 것이며, 더 이상 여러분들의 앱을 사용하고 싶지 않아 할 것입니다.


![](https://images.velog.io/images/eia51/post/3e48972a-f065-4395-9b4b-89be877e86e3/life_cycle.jpg)


## ◆ Service
(업데이트 예정입니다.)

## ◆ Broadcase Receiver
(업데이트 예정입니다.)

## ◆ Content Provider
(업데이트 예정입니다.)

---

**참고 자료**

**이미지 출처**
- [Activity가 실행 되는 과정](https://www.researchgate.net/figure/Standard-flow-for-launching-a-new-application-in-Android_fig2_230794951)
- [Activity Lifecycle](https://ininet.org/android-operating-system.html)