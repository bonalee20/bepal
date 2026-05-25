# 비팔톡 (BipTalk)

> 싸게 사고 비싸게 팔자

목표 주가에 도달하면 카카오톡으로 알림을 보내주는 주식 매도 알림 앱입니다.

---

## 주요 기능

- **카카오 로그인** - 카카오톡 계정으로 간편 로그인
- **종목 자동 등록 (AI)** - 매수 확인서 사진을 가져오면 ML Kit OCR + Gemini API로 종목명·가격·수량 자동 입력
- **종목 등록** - 구매 종목, 구매 가격, 수량, 목표 수익률 등록
- **현재가 조회** - 한국투자증권 API로 실시간 주가 조회
- **목표가 알림** - 목표 수익률 달성 시 카카오톡 나에게 보내기로 알림 전송
- **백그라운드 체크** - WorkManager로 앱이 꺼져 있어도 15분마다 자동 체크
- **고가 기준 체크** - 당일 최고가 기준으로 체크하여 순간적인 목표가 달성도 감지

---

## 기술 스택

| 구분 | 기술 |
|------|------|
| 언어 | Kotlin |
| UI | Jetpack Compose |
| 아키텍처 | MVVM |
| 네트워크 | Retrofit2 + Gson |
| 비동기 | Coroutines + StateFlow |
| 백그라운드 | WorkManager |
| 로그인 | Kakao SDK v2 |
| 알림 | Kakao Talk API (나에게 보내기) |
| 주가 데이터 | 한국투자증권 Open API |
| OCR | Google ML Kit (한국어 텍스트 인식) |
| AI 파싱 | Google Gemini API (gemini-1.5-flash) |

---

## AI 자동 입력 기능

### 동작 흐름
```
사용자가 매수 확인서 사진 촬영 / 갤러리에서 선택
        ↓
Google ML Kit (온디바이스 OCR)로 텍스트 추출
        ↓
Gemini API로 종목명 · 매수가 · 수량 · 증권사 파싱
        ↓
종목 등록 폼에 자동 입력
```

### 주요 파일
| 파일 | 역할 |
|------|------|
| `util/StockOcrParser.kt` | ML Kit OCR + Gemini 파싱 통합 처리 |
| `viewmodel/AddViewModel.kt` | 이미지 처리 요청 및 폼 자동 입력 |
| `ui/add/AddScreen.kt` | 이미지 피커 UI 및 로딩 상태 표시 |

### Gemini 프롬프트 구조
매수 확인서에서 추출된 텍스트를 Gemini에 전달하여 아래 항목을 JSON으로 파싱합니다.
```json
{
  "stockName": "삼성전자",
  "price": "75000",
  "quantity": "10",
  "site": "토스증권"
}
```

---

## 프로젝트 구조

```
com.example.myapplication
│
├── data
│   ├── model
│   │   ├── Stock.kt                # 종목 데이터 모델
│   │   ├── StockResponse.kt        # 주가 API 응답 모델
│   │   ├── StockInfoResponse.kt    # 종목 정보 API 응답 모델
│   │   └── StockSearchResult.kt    # 종목 검색 결과 모델
│   ├── remote
│   │   ├── StockApiService.kt      # 한국투자증권 주가 API
│   │   ├── StockSearchApiService.kt # 종목 검색 API (공공데이터포털)
│   │   └── TokenApiService.kt      # 한국투자증권 토큰 발급 API
│   └── repository
│       ├── StockRepository.kt      # 주가 조회 레포지토리 (싱글톤)
│       └── StockSearchRepository.kt # 종목 검색 레포지토리
│
├── ui
│   ├── add
│   │   ├── AddScreen.kt            # 종목 등록 화면 (AI 자동 입력 포함)
│   │   └── item
│   │       ├── AddField.kt
│   │       ├── AddTextField.kt
│   │       └── StockSearchField.kt # 종목 검색 필드
│   ├── home
│   │   ├── HomeScreen.kt           # 홈 화면
│   │   ├── LoginScreen.kt          # 로그인 화면
│   │   └── item
│   │       ├── BottomNavBar.kt
│   │       ├── StockCardItem.kt
│   │       └── StockListItem.kt
│   ├── alarm
│   │   └── AlarmScreen.kt          # 알람 히스토리 화면
│   ├── list
│   │   └── StockListScreen.kt      # 종목 목록 화면
│   └── theme
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
├── util
│   ├── FormatNumber.kt             # 숫자 포맷 유틸
│   ├── KakaoMessageSender.kt       # 카카오톡 메시지 전송
│   ├── StockAlertManager.kt        # 목표가 달성 체크 및 알림
│   ├── StockCheckWorker.kt         # WorkManager Worker
│   ├── StockOcrParser.kt           # ML Kit OCR + Gemini 파싱 (AI 자동 입력)
│   ├── StockSerializer.kt          # Stock 리스트 JSON 직렬화
│   └── WorkManagerScheduler.kt     # WorkManager 스케줄링
│
├── viewmodel
│   ├── HomeViewModel.kt            # 홈/종목 ViewModel
│   ├── AddViewModel.kt             # 종목 등록 ViewModel (OCR 처리 포함)
│   └── AuthViewModel.kt            # 로그인 ViewModel
│
├── MyApplication.kt                # Kakao SDK 초기화
└── MainActivity.kt                 # Navigation 설정
```

---

## 동작 흐름

```
1. 카카오 로그인
        ↓
2. 종목 등록
   ├── 직접 입력: 구매가, 수량, 목표수익률 직접 입력
   └── AI 자동 입력: 매수 확인서 사진 → OCR → Gemini 파싱 → 폼 자동 완성
        ↓
3. 한국투자증권 API로 현재가 조회
        ↓
4. WorkManager 15분 주기 백그라운드 실행
        ↓
5. 당일 고가 >= 목표가 달성 여부 체크
        ↓
6. 달성 시 카카오톡 나에게 보내기 알림 전송
```

---

## 환경 설정

### 1. 카카오 개발자 설정
- [Kakao Developers](https://developers.kakao.com) 에서 앱 생성
- Android 플랫폼 등록 (패키지명, 키 해시)
- 카카오 로그인 활성화
- 동의항목: `profile_nickname`, `talk_message` 설정

### 2. 한국투자증권 API 설정
- [KIS Developers](https://apiportal.koreainvestment.com) 에서 앱 등록
- 앱키/앱시크릿 발급
- `HomeViewModel.kt` 에 키 입력

### 3. Gemini API 설정
- [Google AI Studio](https://aistudio.google.com) 에서 API 키 발급 (무료)
- `util/StockOcrParser.kt` 의 `YOUR_GEMINI_API_KEY` 교체

### 4. 모의투자 환경
```kotlin
// StockRepository.kt
.baseUrl("https://openapivts.koreainvestment.com:29443/")

// 실제 투자
.baseUrl("https://openapi.koreainvestment.com:9443/")
```

---

## 주요 의존성

```toml
kakao = "2.20.6"
work = "2.9.0"

kakao-user = "com.kakao.sdk:v2-user"
kakao-talk = "com.kakao.sdk:v2-talk"
kakao-template = "com.kakao.sdk:v2-template"
androidx-work = "androidx.work:work-runtime-ktx"
retrofit = "com.squareup.retrofit2:retrofit"
gson = "com.squareup.retrofit2:converter-gson"

# AI 자동 입력
mlkit-text-korean = "com.google.mlkit:text-recognition-korean:16.0.1"
generativeai = "com.google.ai.client.generativeai:generativeai:0.9.0"
```
