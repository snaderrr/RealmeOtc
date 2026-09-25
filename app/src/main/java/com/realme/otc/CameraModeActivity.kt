package com.realme.otc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.majorkernelpanic.streaming.SessionBuilder
import net.majorkernelpanic.streaming.audio.AudioQuality
import net.majorkernelpanic.streaming.rtsp.RtspServer
import net.majorkernelpanic.streaming.video.VideoQuality
import java.net.Inet4Address
import java.net.NetworkInterface

class CameraModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_mode)

        val ipText = findViewById<TextView>(R.id.ipText)
        val startButton = findViewById<Button>(R.id.startServerButton)
        val stopButton = findViewById<Button>(R.id.stopServerButton)

        // Настраиваем сессию один раз при создании Activity
        SessionBuilder.getInstance()
            .setContext(applicationContext)
            .setAudioEncoder(SessionBuilder.AUDIO_AAC)
            .setAudioQuality(AudioQuality(44100, 128000))
            .setVideoEncoder(SessionBuilder.VIDEO_H264)
            .setVideoQuality(VideoQuality(1280, 720, 30, 2_000_000))

        startButton.setOnClickListener {
            startService(Intent(this, RtspServer::class.java))
            val ip = getLocalIpAddress()
            ipText.text = "Сервер запущен\n\nURL для подключения:\nrtsp://$ip:8554"
        }

        stopButton.setOnClickListener {
            stopService(Intent(this, RtspServer::class.java))
            ipText.text = "Сервер остановлен"
        }
    }

    private fun getLocalIpAddress(): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val iface = interfaces.nextElement()
                val addrs = iface.inetAddresses
                while (addrs.hasMoreElements()) {
                    val addr = addrs.nextElement()
                    if (!addr.isLoopbackAddress && addr is Inet4Address) {
                        return addr.hostAddress ?: "0.0.0.0"
                    }
                }
            }
        } catch (_: Exception) {}
        return "0.0.0.0"
    }
}
