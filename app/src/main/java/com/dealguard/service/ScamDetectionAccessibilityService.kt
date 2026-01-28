package com.dealguard.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScamDetectionAccessibilityService : AccessibilityService() {

    private val targetPackages = setOf(
        "com.kakao.talk",
        "org.telegram.messenger",
        "com.whatsapp",
        "com.facebook.orca"
    )

    override fun onServiceConnected() {
        super.onServiceConnected()

        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED or
                    AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or
                    AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
            notificationTimeout = 100
        }

        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // 패키지 체크
        val packageName = event.packageName?.toString()
        if (packageName !in targetPackages) return

        // 이벤트 타입 체크
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED,
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                processEvent(event)
            }
            else -> return
        }
    }

    private fun processEvent(event: AccessibilityEvent) {
        val node = rootInActiveWindow ?: return

        try {
            val extractedText = extractTextFromNode(node)
            if (extractedText.isNotBlank()) {
                // TODO: Analyze text for scam detection
            }
        } finally {
            node.recycle()
        }
    }

    private fun extractTextFromNode(node: AccessibilityNodeInfo): String {
        val textBuilder = StringBuilder()

        // Extract text from current node
        node.text?.let { textBuilder.append(it).append(" ") }
        node.contentDescription?.let { textBuilder.append(it).append(" ") }

        // Recursively extract from children
        for (i in 0 until node.childCount) {
            node.getChild(i)?.let { child ->
                textBuilder.append(extractTextFromNode(child))
                child.recycle()
            }
        }

        return textBuilder.toString().trim()
    }

    override fun onInterrupt() {
        // Handle interruption
    }
}
