package com.dealguard.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.WindowManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverlayService : Service() {

    private var windowManager: WindowManager? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO: Create and show overlay view
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun getWindowType(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove overlay views
    }
}
