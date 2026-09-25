package com.realme.otc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.cameraModeButton).setOnClickListener {
            startActivity(Intent(this, CameraModeActivity::class.java))
        }

        findViewById<Button>(R.id.viewerModeButton).setOnClickListener {
            startActivity(Intent(this, ViewerModeActivity::class.java))
        }

        findViewById<Button>(R.id.settingsButton).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
