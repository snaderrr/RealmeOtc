package com.realme.otc

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.pedro.rtplibrary.rtsp.RtspServerCamera2

class CameraService : Service() {

    private var rtsp: RtspServerCamera2? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel("cam", "Camera", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(ch)
        }
        val notif = NotificationCompat.Builder(this, "cam")
            .setContentTitle("Realme otc")
            .setContentText("Камера раздаёт поток")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(1, notif)

        if (rtsp == null) {
            try {
                val r = RtspServerCamera2(this, true)
                r.prepareVideo(1280, 720, 30, 2_000_000)
                r.prepareAudio(64_000, 32_000, true, false)
                r.startStream()
                rtsp = r
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        rtsp = null
        super.onDestroy()
    }
}
