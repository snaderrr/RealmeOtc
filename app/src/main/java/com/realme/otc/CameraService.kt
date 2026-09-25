package com.realme.otc

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import net.majorkernelpanic.streaming.SessionBuilder
import net.majorkernelpanic.streaming.audio.AudioQuality
import net.majorkernelpanic.streaming.rtsp.RtspServer
import net.majorkernelpanic.streaming.video.VideoQuality

class CameraService : Service() {

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

        SessionBuilder.getInstance()
            .setContext(applicationContext)
            .setAudioEncoder(SessionBuilder.AUDIO_AAC)
            .setAudioQuality(AudioQuality(44100, 128000))
            .setVideoEncoder(SessionBuilder.VIDEO_H264)
            .setVideoQuality(VideoQuality(1280, 720, 30, 2_000_000))

        val server = RtspServer.getInstance()
        server.setPort(8554)
        server.start()

        return START_STICKY
    }
}
