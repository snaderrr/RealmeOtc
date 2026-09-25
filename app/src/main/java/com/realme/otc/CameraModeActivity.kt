package com.realme.otc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.Inet4Address
import java.net.NetworkInterface

class CameraModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_mode)

        val ipText = findViewById<TextView>(R.id.ipText)
        val startButton = findViewById<Button>(R.id.startServerButton)
        val stopButton = findViewById<Button>(R.id.stopServerButton)

        startButton.setOnClickListener {
            val intent = Intent(this, CameraService::class.java)
            startForegroundService(intent)
            val ip = getLocalIpAddress()
            ipText.text = "Сервер запущен\n\nURL для подключения:\nrtsp://$ip:8554/live"
        }

        stopButton.setOnClickListener {
            stopService(Intent(this, CameraService::class.java))
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
