## ◆ 코루틴 개념 정의 
코틀린은 다음 세 가지 키워드로 설명 할 수 있다.
- 협력형 멀티태스킹
- 동시성 프로그래밍
- 쉬운 비동기 처리

<br/>

### ◇ 협력형 멀티태스킹
협력형 멀티태스킹을 이해하려면 Routin에 대한 이해가 필요하다. Routin은 크게 `main routin`과 `sub routin`으로 나뉜다.

![](https://images.velog.io/images/eia51/post/6fee3e8f-31ff-4c67-861a-1f3f61e8bbec/coroutin_routin.png)

이런 `sub routin`을 보면 한 가지 특징이 있는데, 바로 `sub routin`에 **진입하는 지점과 빠져나오는 지점이 명확**하다는 것이다.

`plusOne()`이 실행되면 맨 윗줄의 코드부터 실행되어 마지막의 `return`문을 만나 서브루틴을 호출했던 곳으로 돌아가게 된다.

그러나 코루틴은 조금 다르게 동작한다.


![](https://images.velog.io/images/eia51/post/227266c4-b561-4b2a-af95-b243e1d9b132/coroutin_coroutin.png)


코루틴은 함수에 진입 할 수 있는 진입점도 여러개이고, 빠져나갈 수 있는 탈출점도 여러개이다.
즉 코루틴 함수는 꼭 `return`문이나 마지막 닫는 괄호를 만나지 않더라도 언제나 중간에 나갈 수 있고 언제든지 다시 나갔던 그 지점으로 돌아올 수 있다.

> 실제로 `startCoroutin`이라는 빌더는 존재하지 않지만, 여기선 의미를 쉽게 전달하기 위해 사용한 `pseudo code`이다.
> 정확한 코루틴의 실행 코드는 뒤에서 소개 할 '코틀린에서 코루틴 적용해보기' 항목에서 자세히 다루도록 한다.

코루틴 블록은 언제든 나왔다 다시 들어갈 수 있는데, 이런 성향은 동시성 프로그래밍과 밀접한 관계가 있다.

<br/>

### ◇ 동시성 프로그래밍
함수를 중간에 빠져나왔다가 다른 함수에 진입하고 다시 원점으로 돌아와 멈췄던 부분부터 다시 시작하는 이러한 특성은 동시성 프로그래밍을 가능하게 한다.

동시성 프로그래밍은 병렬 프로그래밍과는 완전 다른 개념이다.

동시성 프로그래밍은 여러 작업들이 있을 때, 그 각각의 작업들을 **프로세서 하나가 시분할로 돌아가면서 조금씩 나눠 수행**하는 기법을 의미하며, 병렬 프로그래밍은 여러개의 작업을 여러개의 프로세서가 동시에 나눠 수행하는 기법을 의미한다.

동시성 프로그래밍은 한 순간에 하나의 프로세스를 수행하는 개념이지만, 병렬 프로그래밍은 한 순간에 여러개의 프로세스를 수행하는 개념이다.

코루틴도 `routin`이다. 즉 쓰레드가 아니라 일반 서브루틴의 개념이기 때문에 하나의 쓰레드에 여러개의 코루틴이 존재 할 수 있으며, 코루틴을 사용하여 싱글 쓰레드에서 동시성 프로그래밍을 수행 할 수 있다.

코루틴을 통한 동시성 프로그래밍은 쓰레드를 사용해 동시성 프로그래밍 하는 것과는 차원이 다른 효율성을 제공한다. 여러개의 쓰레드를 제어하기 위해선 CPU의 컨텍스트 스위칭이 발생하게 되는데, 이는 단순히 싱글 쓰레드 내에서 함수를 왔다갔다 하는 것과는 다르게 꽤 많은 비용이 드는 작업이기 때문이다.

<br/>

### ◇ 쉬운 비동기 처리
코루틴을 사용하면 비동기 처리에 대한 손쉬운 처리가 가능해진다.
다음과 같이 순서성이 보장되어야 하는 작업에 대한 예시를 가정한다.
> 1. 8시 기상
> 2. 샤워하기
> 3. 옷입기
> 4. 출발하기
> 5. 회사 도착
> 6. 일 시작

이를 콜백으로 구현한다면 다음과 같은 코드가 나온다.
![](https://images.velog.io/images/eia51/post/b521a650-0a7c-4528-9e06-c717d8e5721c/coroutin_callback.png)

콜백으로 비동기 처리를 구현했을 때 흔히 겪을 수 있는 콜백지옥이다. 심지어 에러 처리도 따로 하지 않았는데 보기 힘든 구조를 띄고 있다.

이를 코루틴을 사용해 작업하면 다음과 같은 코드가 나온다.

![](https://images.velog.io/images/eia51/post/b170cbfd-bd35-4eb0-950f-91012e3ec28f/coroutin_callback_sol.png)

"이게 정말 비동기처리인가?" 라는 의문이 들 수 있다. 그러나 이건 분명히 비동기 코드이다. 각 함수들은 오랜 시간동안 수행되는 작업들이지만 그 함수들의 순서는 정확히 지켜진다.

이런게 가능한 이유는 `goCompany()`가 코루틴이기 때문에 `wakeUp()`을 실행함과 동시에 잠시 `goCompany()`를 빠져나간다. 그러다 `wakeUp()`이 자신의 일을 끝내면 다시 `goCompany()`로 돌아오게 된다.

이렇듯 코루틴을 사용하면 콜백지옥 없이 손쉬운 비동기처리를 수행 할 수 있다.

<br/>

## ◆ 코틀린에서 코루틴 적용해보기
TODO

### 참고자료
- [코루틴 개념 익히기](https://wooooooak.github.io/kotlin/2019/08/25/%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%BD%94%EB%A3%A8%ED%8B%B4-%EA%B0%9C%EB%85%90-%EC%9D%B5%ED%9E%88%EA%B8%B0/)
- [코틀린에서의 코루틴]()
