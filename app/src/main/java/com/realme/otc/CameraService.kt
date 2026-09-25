package com.realme.otc

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class CameraService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel("cam", "Camera", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(ch)
        }
        val notif = NotificationCompat.Builder(this, "cam")
            .setContentTitle("Realme otc")
            .setContentText("Камера работает в фоне")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(1, notif)
        return START_STICKY
    }
}
