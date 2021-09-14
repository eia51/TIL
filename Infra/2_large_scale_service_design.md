## LoadBalancer 
사용자의 요청을 여러 서버로 분배해주는 하드웨어/소프트웨어 장비
요청하는 입자에서는 LoadBalancer 뒤의 목록은 알지 못한다.

 Nginx, HAProxy 등의 유명한 소프트웨어 LoadBalancer가 존재

 **LoadBalancer의 Failover**
 LB1 장애 시, LB1이 가진 ip주소를 LB2가 가져가는 방식으로 동작

<br/>

## LoadBalancer의 형태
- ### Server Side LoadBalancer
  - LB를 통해 요청을 분산한다.
  - 클라이언트가 서버의 개별 주소를 알 필요가 없다. (LB의 주소를 알아야함)
  - 실제적으로 한 단계를 더 거쳐야 하므로 Latency가 늘어날 수 있다. (=Hop)
  - LB에 장애가 발생하면 서비스가 더 이상 되지 않는다.

- ### Client Side LoadBalancr
  - 클라이언트가 서버의 대수 및 주소를 모두 알아야 한다. (How? Coordinator 통한 서비스 디스커버리)
  - Hop이 존재하지 않으므로 더 빠른 Latency를 보장한다.
  - 장애포인트가 줄어든다.
  - 클라이언트에서 서버의 목록과 주소를 관리해야한다는 단점이 존재. 
     (서버의 주소가 바뀌거나, 서버 목록이 추가/삭제 된다면 지속적으로 Config 파일의 배포가 필요함)

<br/>

## Service Discovery
우리는 특정 서비스를 어떻게 찾을 수 있을까?" 하는 질문에 가장 먼저 떠오를만한 대답은 일반적으로 'DNS'.
> #### DNS (Domain Name Service) 란?
>  - IP를 기억하기 힘들기 때문에 이를 좀 더 기억하기 쉬운 문자 형태로 제공하는 것 
>  - 클라이언트들은 외부DNS를 이용해서 접속해야할 API 서버를 알아내고, 이 API 서버들은 내부DNS를 이용해서 접속해야할 DB를 알아낸다.
 
  DNS로 서버 목록을 제공한다면, 외부에서 DNS 목록을 통해 우리 서버의 주소를 알 수 있다.
  
  그러나 서버가 죽었을 때 DNS TTL에 의하여 클라이언트는 TTL 시간동안 잘못 된 정보를 가지고 있게 된다. 
  
  DNS TTL로 인해 발생 가능한 문제를 회피하고자 나타난게 바로 _**서비스 디스커버리**_.
  
  `* DNS TTL : DNS 서버의 부하를 줄이기 위하여 한번 가져간 데이터는 일정 시간동안 다시 요청하지 않도록 하는 값`

  <br/>

  ### 서비스 디스커버리란?
   - 서비스의 주소, 포트, 프로토콜 등을 알려주는 방법
   - 서비스에 속한 서버를 DNS 통하지 않고, 어떻게 찾을 것인지에 대한 솔루션
   - 서비스 디스커버리를 구현하기 위해 사용하는 둘로는 `Zookeeper` `Etcd` `Consul` `Eureka` 등이 있으며, 
     통상적으로 Coordinator라고 칭한다.
   - Cluster Membership으로 인해 서비스 간의 등록/해제 등의 과정이 자동화 된다.
     `* Cluster Membership : 서비스 디스커버리의 서비스 자동 동기화 기능`

  > #### ※ 왜 서비스 디스커버리를 사용해야할까?
  > LB가 없는 Client Side Balancing에서 서버1이 서버2의 주소를 어떻게 알 수 있을까?
  > 통상적으로 설정 파일을 통해서, DNS를 통해서일 것이다.
  > 그러나 이 두 방법은 각각 문제의 소지가 있다.
  >
  > 1. **설정 파일**
  >    - 서버 주소목록을 설정 파일에 넣어두고 관리
  >    - 서버 주소가 바뀌면 설정 파일 수정 후 재배포
  >    - <U>장애가 났을 때 사람이 수동으로 배포하게 된다</U>
  >    
  > <br/>
  > 
  > 2. **DNS**
  >    - DNS를 이용해 동적으로 바꾸자
  >    - DNS 질의를 이용해 서버의 목록을 가져오자
  >    - <U>DNS는 누가 업데이트해주나? TTL 문제는?</U>
  >    - <U>서버가 추가삭제 될 때 LB를 쓸 수 없다면?</U>
  >  
  > 위 에서 언급된 방법들의 문제를 <U>**서비스 디스커버리**는 모두 해소할 수 있다.</U>
  
  <br/>

### 서비스 디스커버리가 제공해야 하는 기능
   - 어떤 서비스가 있는지 알려주는 기능
   - 서비스 접송 방법을 알려주는 기능
   - 서비스 내의 서버 추가/제거가 있을 때 이를 알려주는 기능
  
     `- Server Side Load Balancing에서는 LoadBalancer의 주소와 Port`
     
     `-  Client Side Load Balancing에서는 해당 서버 목록과 Port`
     
<br/>
  
### 서비스 디스커버리의 동작 구조
   - 서비스 디스커버리는 각 서버에게 Health check를 수행한다
   - 서버A가 추가됐을 때, 서버A는 서비스 디스커버리에게 추가 됐음을 알림
   - 서비스 디스커버리는 연결 된 다른 서버에게 서버A의 추가됨을 알림
   - 연결 된 다른 서버는 새롭게 추가 된 서버A에 대한 인지를 하고 접속이 가능해지게 됨

<br/>

### 서비스 디스커버리를 위한 Coordinator
- 서비스 가용성이 높다.
  - 일반적으로 3대 이상으로 동 
  - 데이터의 동기화가 이뤄지므로 절반 이상이 장애가 나지 않는 이상 서비스의 유지가 가능
- 특정 값을 저장할 수 있는 대시보드의 역할
  - 이러한 특성을 이용해 특정 서비스 군의 서버 목록을 저장
  - 변경되는 값에 대한 Notification 가능
  - Cluster Membership
- 노드의 순서를 보장해준다
  - Leader Election

<br/>   

### Leader Election
헬스체크를 수행해야 하는 방법에 대해 고민해본다면,
> 1. 서버를 체크하기 위한 헬스체크 체이닝(헬스체크1, 헬스체크1을 감시하기 위한 헬스체크2...)
> 2. 서버를 체크하기 위한 패러럴 헬스체크(헬스체크1,2,3이 서버를 체크)

이런 구조는 비효율적일 수 있다.
불필요한 헬스체크로 인한 리소스낭비가 심한 구조이기 때문이다.
  
1번의 경우에는 저러한 체이닝이 계속해서 늘어나게 될 것이고,
2번의 경우에는 한번의 장애로 인해 N개의 알림이 올 수 있고, 이는 정확한 문제 파악에 방해가 될 수 있다.
  
이런 상황에 대한 솔루션이 바로 <u>Leader Election</U> !

Leader Election은 **Leader로 설정 된 '단 하나'**의 헬스체크만 동작하게 하는 방식이다.

`쉽게말해 헬스체크1이 실패했을 때, 헬스체크2가 동작하고, 헬스체크2가 실패했을 때, 헬스체크3이 동작하는 방식`

Leader 선정은 어떤 Coordinator tool을 사용하는지에 따라 다르다.
- `Zookeeper` : 목록의 서버군들 중 가장 위 서버가 Leader
- `Etcd` : 특정 key를 쓰는데 성공한 서버가 Leader가 된다

<br/>

---

강의 출처
The RED : 백엔드 에센셜 : 대용량 서비스를 위한 아키텍처 with Redis by 강대명 https://github.com/charsyam/the_red_infra/blob/main/install_pyenv.sh