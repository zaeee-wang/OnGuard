package com.dealguard.detector

import com.dealguard.domain.model.DetectionMethod
import com.dealguard.domain.model.ScamAnalysis
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HybridScamDetector @Inject constructor(
    private val keywordMatcher: KeywordMatcher,
    // private val mlClassifier: MlClassifier,  // TODO: Implement
    // private val urlAnalyzer: UrlAnalyzer      // TODO: Implement
) {

    suspend fun analyze(text: String): ScamAnalysis {
        // Rule-based detection first (fast)
        val keywordResult = keywordMatcher.match(text)

        if (keywordResult.isScam && keywordResult.confidence > 0.7f) {
            // High confidence from rules, return immediately
            return keywordResult
        }

        // TODO: If rule-based is uncertain, use ML classifier
        // val mlResult = mlClassifier.classify(text)

        // For now, return rule-based result
        return keywordResult
    }
}
