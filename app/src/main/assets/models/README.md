# Gemma 3 모델 다운로드 안내

## 필요한 파일

| 파일명 | 크기 | 용도 |
|--------|------|------|
| `gemma3-270m-it-q4_0-web.task` | 249MB | On-device LLM 스캠 탐지 |

## 다운로드 방법

### Hugging Face에서 다운로드 (권장)

1. [Hugging Face Gemma 3 270M](https://huggingface.co/litert-community/gemma-3-270m-it) 접속
2. 로그인 후 라이선스 동의
3. `gemma3-270m-it-q4_0-web.task` 파일 다운로드
4. 이 폴더 (`app/src/main/assets/models/`)에 복사

### 파일 배치 확인

```
app/src/main/assets/models/
├── README.md                          # 이 파일
└── gemma3-270m-it-q4_0-web.task       # Gemma 모델 (직접 다운로드)
```

## 모델 없이 실행하기

**모델 파일이 없어도 앱은 정상 작동합니다!**

- LLM 기능은 자동으로 비활성화됨
- Rule-based 탐지만 사용됨 (KeywordMatcher + UrlAnalyzer)
- 앱 시작 시 로그에 경고만 표시됨

## 왜 Git에 포함되지 않나요?

| 이유 | 설명 |
|------|------|
| 파일 크기 | 249MB - GitHub 100MB 제한 초과 |
| Clone 속도 | 저장소 clone 시간 대폭 증가 |
| 라이선스 | Google Gemma 약관에 따라 개별 동의 필요 |

## 참고 링크

- [Google Gemma License](https://ai.google.dev/gemma/terms)
- [MediaPipe LLM Inference](https://developers.google.com/mediapipe/solutions/genai/llm_inference)
