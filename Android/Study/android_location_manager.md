# 안드로이드에서 내 위치 받아오기
안드로이드에서 `LocationManager`를 이용해 사용자의 위치를 받아오는 방법에 대하여 간략히 소개하도록 한다.

<br/>

## 권한 추가
안드로이드 기기의 위치를 얻어오는 것은 **위험 권한**으로 분류 된다.

때문에 `Manifest.xml`에 다음과 같이 사용 할 권한 목록을 추가한 뒤, 런타임 시점에 다시 한 번 권한을 요청 해줘야 한다.

런타임 시 권한 요청하는 방법은 [[여기]](https://github.com/ParkSangGwon/TedPermission)를 참조 한다.

```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
```

요청 권한이 두 가지로 나뉘는 것을 볼 수 있는데, `CORSE_LOACTION`은 대략적인 위치를 받아오고자 할 때 사용하는 권한이며, `FINE_LOCATION`은 정확한 위치를 받아오고자 할 때 사용하는 권한이라고 한다.

<br/>

## LocationManager 선언
안드로이드에서 위치 서비스의 기본이 되는 `LocationManager`는 다음과 같이 시스템 서비스로부터 받아 올 수 있다.
```java
LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
```

<br/>

## 위치 업데이트 리스너 구현
위치가 업데이트 됐을 때 호출 될 콜백 리스너를 다음과 같이 선언해준다. 위치가 변경 됐을 때 호출 되는 `onLocationChange()` 콜백만 구현해도 기능 사용에는 큰 무리가 없다.

구현 코드를 보면 기존에 사용되는 `LocationListener`가 아닌 `LocationListenerCompat`를 구현한 것을 볼 수 있다. `LocationListenerCompat`는 `onStateChanged()`가 안드로이드 Q에서 `Deprecated` 됨에 따라 구현체에서 빠진 인터페이스이다. 

자세한 내용은 [[여기]](https://developer.android.com/reference/android/location/LocationListener#onStatusChanged(java.lang.String,%20int,%20android.os.Bundle))를 참고 해보도록 한다.

```java
private class GPSListener implements LocationListenerCompat {
    @Override
    public void onLocationChanged(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
 
        // 내 위치 = latitude, lontitude
    }
 
    @Override
    public void onProviderEnabled(String provider) {
 
    }
 
    @Override
    public void onProviderDisabled(String provider) {
 
    }
}
```

<br/>

## 위치 업데이트 요청 등록
다음과 같이 `locationManager` 인스턴스에 `requestLocationUpdates` 메서드를 사용하여 위치 변경 시 받을 콜백 정보를 등록 한다.

```java
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
						minTime, minDistance, gpsListener);
                        
locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
						minTime, minDistance, gpsListener);
```

`requestLocationUpdates`의 실행 인자를 순서대로 간략히 설명하자면,

1. **LocationManager.GPS_PROVIDER**
위치를 제공하기 위한 `Content Provider`. GPS와 NETWORK 두 가지의 버전이 있는데, GPS는 야외에서 위치 정보를 가져오기 위한 Provider이고, NETWORK는 실내에서 WIFI 등의 네트워크를 통해 위치를 가져오기 위한 Provider이다. GPS는 야외에서만 동작 가능하기 때문에 두 가지 버전의 Provider를 모두 사용하고, 응답이 가능한 부분으로 콜백을 받는 것이다.

2. 콜백을 수신하기까지의 최소 시간

3. 몇 미터를 움직인 뒤 응답을 받을 것인지

4. `LocationListener` 콜백 객체. 위에서 선언한 `GpsListener`의 인스턴스를 전달해주면 된다.


<br/>

## 내 마지막 위치 받아오기
다음과 같이 현재 나의 위치를 얻어 올 수 있다. 반환 된 Location 객체의 `latitude`, `longtitude` 정보를 통해 나의 위치를 알 수 있다.

```java
Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
```
