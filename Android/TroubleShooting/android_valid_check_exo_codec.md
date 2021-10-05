# ExoPlayer Codec 유효성 검증하기
Android에서 ExoPlayer로 영상을 스트리밍해주는 서비스를 만들다보면 내가 제공하는 영상들이 클라이언트 측에서 정상적으로 재생 되는지 궁금 할 수 있다.

실제로 안드로이드를 빌드 한 제조사 별로 해당 버전에서 지원하는 Video, Audio 코덱이 달라질 수 있기 때문에 당장 내가 가지고 있는 데모 기기에서 정상적으로 재생되는 영상이라고 해도 특정 기기에서는 정상적으로 재생 되지 않을 수 있다.

최상의 사용자 경험을 제공하기 위해선 이런 재생 오류를 사전에 예방 해야히겠지만, 그게 어려운 상황이라면 최소한 관련 문제가 발생했을 때 빠르게 리포트를 받아 문제에 대응 할 수 있어야 한다.

이 글에서는 이러한 문제를 재생 시점에 조기 탐지해 내는 방법에 대하여 소개하도록 한다.

<br/>

## Video Codec 호환 검증
> 이 글의 코드들은 ExoPlayer 2.13.2 버전의 ExoPlayer를 기준으로 작성 됐습니다.

Video 코덱 호환이 맞지 않음으로 인한 플레이어 에러는 즉시 ExoPlayer의 `onPlayerError()` 콜백이 호출되며, 재생이 중단 되기 때문에 <U>비교적 쉽게</U> 대응 할 수 있다.

`onPlayerError()`는 ExoPlayer를 통한 영상 재생 과정에서 문제가 발생하여 비디오 렌더링을 더 이상 진행 할 수 없을 때 호출 되는 에러 핸들링 콜백이며, `Player.EventListener` 인터페이스 안에 정의되어있다.

`onPlayerError()`에서 Video Codec 에러를 확인하는 방법은 다음과 같다. 

```java
//ExoPlayer의 이벤트 처리를 위한 클래스
public class ExoPlayerEventListener implements Player.EventListener {

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        if (error.type == ExoPlaybackException.TYPE_RENDERER) {
            // 비디오 코덱에 대한 렌더링 에러
            // error.rendererFormat의 codec, metadata 부분을 참조하여 리포트 하면 된다.
        }
    }    
}
```
> **+ 위 에러에 대한 `getMessage()` 출력 형태**
>
> `ExoPlaybackException: MediaCodecVideoRenderer error`

<br/>

## Audio Codec 검증하기
앞서 `onPlayerError`에서 렌더링 오류를 처리하면서 별다른 조건 분기를 하지 않고 비디오 코덱에 대한 예외로 처리를 한 것이 의아 할 것이다. 미디어에는 비디오만 있는 것이 아니기 때문이다. 일반적으로 미디어라면 비디오 외에도 오디오와 같은 정보를 추가적으로 검증해야한다.

동영상 재생 중 Audio Codec에 문제가 발생한다면, 마치 음소거 된 영상을 보는 것과 같은 경험을 하게 된다. 비록 재생 과정에서 문제가 생겼지만, 비디오 렌더링은 정상적으로 완료 되었으므로 `onPlayerError`의 호출 없이 프로세스가 흘러가게 된다.


그렇다면 Audio Codec에 대한 문제는 어떻게 확인 할 수 있을까?

### TrackSelector
`TrackSelector`, ExoPlayer를 사용하다보면 자주 볼 수 있는 키워드이다. [ExoPlayer 공식문서](https://exoplayer.dev/track-selection.html)에 따르면, `TrackSelector`는 미디어 트랙 중 재생되어야 할 트랙을 선택하는 클래스라고 정의 되어 있다.

여기서 말하는 미디어 트랙이란 쉽게 말하면 다음과 같다.

![](https://images.velog.io/images/eia51/post/c3fea2b8-b839-41f6-8ce4-7b7521ef0d96/track.jpg)

우리가 영상을 볼 때 빼놓을 수 없는, 단짝 같은 존재인 Control-bar는 어떻게 보면 UI로 나타내어진 미디어 트랙이라고 할 수 있다.

동그란 헤드 부분을 잡고 내가 재생 할 위치로 커서를 움직이면, 그 타임라인에 맞는 영상, 오디오, 자막 등이 렌더링 되어서 나온다.

이렇듯 ExoPlayer의 `TrackSelector`는 스트림으로 존재하는 영상, 오디오, 자막에 대한 정보 중 내가 플레이 할 타임라인을 선택하기 위한 클래스이다.

다시 Audio Codec 이야기로 돌아와서, 여기서 말하는 트랙, 정확히는 '오디오' 트랙에 대한 정보가 없는 경우 재생 과정에서 오디오에 문제가 발생한 상황이라고 볼 수 있다.

### Track 정보 확인하기
#### ◇ Player.EventListener#onTracksChanged
트랙 정보는 일반적으로 `Player.EventListener`에 정의 되어 있는 `onTracksChanged()`에서 변경 내용을 확인 할 수 있다.

그러나 여기서 확인 가능한 트랙 정보는 온전히 신뢰 할 수는 없다. 파라미터의 `TrackGroupArray` 안에 Audio, Video 코덱에 대한 정보가 `Format` 클래스로 래핑되어 들어있는데, 여기로 전달 된 코덱들이 전부 재생에 성공 한 유효한 코덱이라는 보장이 없기 때문이다. 오디오 디코더 초기화 과정에서 에러가 난 코덱이라고 해도 이 부분에서는 별 다른 에러 메세지 없이 `audio/코뎅명` 형태로 참조가 가능하다.

미디어 파일에 Audio Codec이 아예 없는 케이스는 이곳에서 걸러 낼 수 있겠지만, Codec 간 호환성 확인은 어렵다.

#### ◇ AnalyticsListener#onAudioDecoderInitialized
플레이 패턴에 대한 분석을 위해 존재하는 `AnalyticsListener`에 정의 된 `onAudioDecoderInitialized()` `onVideoDecoderInitialized()`를 통해서도 코덱 확인이 가능하다. 그러나 역시 이 경우에도 문제는 있다.

`on***DecoderInitialized()` 콜백은 정상적으로 재생 준비가 완료 됐을 때 호출 되는 콜백이다. 심지어 변경이 없을 시 최초 1회만 호출 되는 콜백이다.

재생 준비가 완료 됐을 때 호출 된다 함은 내가 호환하지 않는 코덱에 대한 처리를 이곳에서 수행 할 수 없다는 의미이기도 하다. (실제로 지원하지 않는 코덱을 재생 시도하면 해당 콜백이 호출 되지 않는다.)

#### ◇ TrackSelector 커스터마이징
추가적인 자료 조사보단, 차라리 기존에 배포 된 TrackSelector를 커스터마이징 하여 사용하는 것이 당장의 처리나, 이후 확장성 측면에서도 유리 할 것 같아서 직접 커스터마이징 하도록 한다.


**1. DefaultTrackSelector 확장 클래스 생성**
```java
public class CustomTrackSelector extends DefaultTrackSelector {

}
```
프로젝트 경로에 위와 같이 ExoPlayer에서 기본적으로 사용하는 DefaultTrackSelector를 상속받는 클래스를 정의한다.


**2. selectAllTracks 재정의**
확장 클래스를 정의했다면, 이제 실제 트랙을 선택하는 부분을 커스터마이징 해야 한다.
ExoPlayer 의존성에 포함 된 `DefaultTrackSelector`의 위치로 이동하여 `selectAllTracks()` 함수를 복사해서 우리의 확장 클래스에 붙여넣어준다.

이 함수를 보면 VideoRenderer에 대한 선택, AudioRenderer에 대한 선택, 자막 및 기타 Renderer에 대한 선택이 순차적으로 작성 된 for문에 의해서 확인 되는 것을 볼 수 있다.

![](https://images.velog.io/images/eia51/post/13d53d4d-13de-4650-96d0-6d6b523ccc02/lol.jpg)

> 처음 이 함수를 보고 **"이렇게 끔찍한 혼종이 있다니..!"** 하는 생각을 감출 수 없었다.

같은 배열을 3번에 거쳐 나눠 돌면서 비디오, 오디오, 자막 등의 정보를 초기화 하는 모습이
 너무 비효율적으로 비춰졌기 때문이다.
 
 한참의 고민 끝에  TODO
 
본론으로 돌아와, selectAllTracks의 Audio Track을 셀렉트 하는 부분의 코드를 다음과 같이 변경해준다. 

```java
@Nullable AudioTrackScore selectedAudioTrackScore = null;
@Nullable String selectedAudioLanguage = null;
int selectedAudioRendererIndex = C.INDEX_UNSET;
for (int i = 0; i < rendererCount; i++) {
    if (C.TRACK_TYPE_AUDIO == mappedTrackInfo.getRendererType(i)) {
        boolean enableAdaptiveTrackSelection =
                params.allowMultipleAdaptiveSelections || !seenVideoRendererWithMappedTracks;
        @Nullable
        Pair<ExoTrackSelection.Definition, AudioTrackScore> audioSelection =
                selectAudioTrack(
                        mappedTrackInfo.getTrackGroups(i),
                        rendererFormatSupports[i],
                        rendererMixedMimeTypeAdaptationSupports[i],
                        params,
                        enableAdaptiveTrackSelection);
        if (audioSelection != null &&
                (selectedAudioTrackScore == null || audioSelection.second.compareTo(selectedAudioTrackScore) > 0)) {
            Dlog.e("jyDBG", "Support Audio format");
            if (selectedAudioRendererIndex != C.INDEX_UNSET) {
                // We've already made a selection for another audio renderer, but it had a lower
                // score. Clear the selection for that renderer.
                definitions[selectedAudioRendererIndex] = null;
            }
            ExoTrackSelection.Definition definition = audioSelection.first;
            definitions[i] = definition;
            // We assume that audio tracks in the same group have matching language.
            selectedAudioLanguage = definition.group.getFormat(definition.tracks[0]).language;
            selectedAudioTrackScore = audioSelection.second;
            selectedAudioRendererIndex = i;
        }
        //실제 우리가 눈여겨 봐야 하는 부분이다. <------- ★★★
        else {
            TrackGroupArray tga = mappedTrackInfo.getTrackGroups(i);
            Dlog.e("jyDBG", "Not support audio format! : " + tga);
            String audioCodecInfo = "not_confirmed";
            for (int i_tga = 0; i_tga < tga.length; i_tga++) {
                TrackGroup tg = tga.get(i_tga);
                for (int i_fm = 0; i_fm < tg.length; i_fm++) {
                    audioCodecInfo = tg.getFormat(i_fm).sampleMimeType;
                }
            }

            callback.report(audioCodecInfo);
            callback.forcesNextPlay();
        }
    }
}

```

위 코드를 보면 별표를 해놓은 부분 아래가 Audio Codec 문제를 감지하기 위해 추가한 코드이다.

실제로 Audio Codec에 문제가 생겼을 때 (호환이 맞지 않는다던지, 코덱이 없다던지..) audioSelection은 `null` 값을 갖는다.

`Pair<ExoTrackSelection.Definition, AudioTrackScore>` 타입의 audioSelection을 만들어주는 `selectAudioTrack`의 내부를 보면, 선택 된(유효한) 그룹 인덱스가 없을 때 `null`을 반환하도록 작성 되어 있기 때문이다.

우리는 이 부분을 활용 해서 예외처리를 할 수 있다.


> 지금 코로나 백신을 맞은 직후인데, 몸이 너무 아프니 나머지는 내일 작성해야지...

<br/>

## 마무리
위에서 소개한 비디오, 오디오 코덱 문제로 인한 재생 실패 리포트는 말 그대로 문제에 대한 내용을 개발자가 리포트 받아 재발하지 않도록 하는 것을 목표로 한다.

이와 별개로 재생 실패가 됐을 때 이에 따른 적절한 후속 처리도 필요할 것이다. (예를 들면 팝업 메세지와 함께 이전 화면으로 보내주는 식의 처리..)

플레이어를 통해 영상을 재생하는 프로그램을 만드는 것은 간단하지만, 이를 사용자 경험과 성능에 대한 최적화 측면까지 고려하면서 작업하는 것은 쉽지 않은 일이다.

그러나 훌륭한 프로그래머가 되고자 한다면, 단순 기능 구현에 그치지 않고, 발생 할 수 있는 문제들을 예측하고, 사전에 방지 할 수 있는 방어적 코딩을 하는 습관을 들이는 것이 필요 할 것 같다.
