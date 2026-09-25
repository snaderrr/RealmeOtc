package com.realme.otc

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.pedro.common.ConnectChecker
import com.pedro.library.rtsp.RtspServerCamera2

class CameraService : Service(), ConnectChecker {

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
                rtsp = RtspServerCamera2(this, true, this)
                rtsp?.apply {
                    prepareVideo(1280, 720, 30, 2_000_000)
                    prepareAudio(64_000, 32_000, true)
                    startStream()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        try {
            rtsp?.stopStream()
            rtsp?.stopPreview()
        } catch (_: Exception) {}
        rtsp = null
        super.onDestroy()
    }

    override fun onConnectionStarted(url: String) {}
    override fun onConnectionSuccess() {}
    override fun onConnectionFailed(reason: String) {}
    override fun onNewBitrate(bitrate: Long) {}
    override fun onDisconnect() {}
    override fun onAuthError() {}
    override fun onAuthSuccess() {}
}
