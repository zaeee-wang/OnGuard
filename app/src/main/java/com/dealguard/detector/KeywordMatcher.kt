package com.dealguard.detector

import com.dealguard.domain.model.DetectionMethod
import com.dealguard.domain.model.ScamAnalysis
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeywordMatcher @Inject constructor() {

    private val scamKeywords = setOf(
        // 급전 관련
        "급전", "급하게", "돈필요", "빌려주세요", "대출",

        // 계좌/송금 관련
        "계좌번호", "송금", "입금", "이체",

        // 피싱 관련
        "인증번호", "OTP", "보안카드", "비밀번호",

        // 사칭 관련
        "경찰청", "검찰청", "금융감독원", "국세청",

        // 협박 관련
        "체포", "구속", "영장", "벌금",

        // 기타
        "당첨", "환급", "세금"
    )

    private val highRiskPatterns = listOf(
        Regex("\\d{3,4}-\\d{3,4}-\\d{4}"),  // 계좌번호 패턴
        Regex("\\d{3}-\\d{4}-\\d{4}"),       // 전화번호 패턴
        Regex("\\d{6}-\\d{7}")               // 주민번호 패턴
    )

    fun match(text: String): ScamAnalysis {
        val normalizedText = text.lowercase().replace("\\s".toRegex(), "")

        val detectedKeywords = scamKeywords.filter { keyword ->
            normalizedText.contains(keyword)
        }

        val hasHighRiskPattern = highRiskPatterns.any { pattern ->
            pattern.containsMatchIn(text)
        }

        val reasons = mutableListOf<String>()
        var confidence = 0f

        if (detectedKeywords.isNotEmpty()) {
            confidence += detectedKeywords.size * 0.15f
            reasons.add("스캠 키워드 ${detectedKeywords.size}개 발견")
        }

        if (hasHighRiskPattern) {
            confidence += 0.3f
            reasons.add("민감한 정보 패턴 발견")
        }

        confidence = confidence.coerceIn(0f, 1f)

        return ScamAnalysis(
            isScam = confidence > 0.5f,
            confidence = confidence,
            reasons = reasons,
            detectedKeywords = detectedKeywords,
            detectionMethod = DetectionMethod.RULE_BASED
        )
    }
}
