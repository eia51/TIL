## 대규모 서비스가 가져야 할 특성

- `확장성`  가용용량을 늘이고 줄이는 것이 쉬워야 한다.
- `장애회복성`  장애가 발생했을 때 수동 처리 없이 자동 회복
- `자동화`  배포, 장애 처리 등이 최대한 자동화 되어 버튼 클릭 한 두 번 정도로 진행 되어야 함.
- `모니터링`  상태를 항상 모니터링

<br/>

---
<br/>

### 확장성

트래픽의 규모에 따라 서버를 추가하거나 줄이는 상황이 필연적으로 발생하게 된다.

이 때 어떤 종류의 서버던 (API, DB..) 쉽게 추가, 제거 될 수 있어야 한다.

<br/>

### 장애회복성

- 서버에 장애가 발생해도 서비스는 계속 지속될 수 있어야 한다. (failover, replication..)
- 장애회복성을 위해선 SPOF가 없어져야 한다.

*SPOF(single point of failure) : 한 지점에서 발생한 장애가 전체 서비스에 영향을 줄 수 있는 부분

SPOF를 최대한 없애야 한다.
한 대의 물리 서버 안에서 API서버와 DB서버를 운용하는 경우, 이는 반드시 SPOF가 된다.
이런 SPOF를 제거하기 위해선 각 부분에서 모두 이중화가 되어있어야 한다.

- API 서버를 여러 대로 나눈다.
- DB를 여러 대로 Replication한다

<br/>

SPOF는 서버 뿐만이 아니라 하드웨어에서도 발생할 수 있다.

- 전용선의 단락
- 네트워크의 대역폭 한계
(스위치의 용량 한계를 넘어서는 데이터 전송이 발생하는 경우,
 허용 한계를 넘어서는 순간부터 패킷 드랍이 발생)

> 따라서 하드웨어들도 이중화가 필요하다.

<br/>

### 자동화

사람의 손은 장애의 씨앗이 되기 때문에 IaC가 중요
*IaC (Infrastructure as code) : 코드를 통해 인프라를 관리하고 프로비저닝 하는 것

온프레미스 서비스

OS과 기본적 네트워크 설정 이외 모든 설정을 설치 스크립트 하나로 처리하도록 해야 한다. 
(chef, puppet, ansible 등을 사용해서 구현)

클라우드 서비스

특정 이미지를 모두 만들어두고, Terraform으로 인프라를 구성한 이후 ansible로 필요 설정을 할 수 있어야 한다.

<br/>

---

#### 강의 출처
The RED : 백엔드 에센셜 : 대용량 서비스를 위한 아키텍처 with Redis by 강대명
[https://github.com/charsyam/the_red_infra/blob/main/install_pyenv.sh](https://github.com/charsyam/the_red_infra/blob/main/install_pyenv.sh)
