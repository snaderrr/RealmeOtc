package com.realme.otc

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ViewerModeActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer_mode)

        prefs = getSharedPreferences("viewer", MODE_PRIVATE)
        val urlInput = findViewById<EditText>(R.id.urlInput)
        val connectButton = findViewById<Button>(R.id.connectButton)
        val saveButton = findViewById<Button>(R.id.saveButton)

        urlInput.setText(prefs.getString("url", ""))

        saveButton.setOnClickListener {
            val url = urlInput.text.toString().trim()
            if (url.isNotEmpty()) {
                prefs.edit().putString("url", url).apply()
                Toast.makeText(this, "Адрес сохранён", Toast.LENGTH_SHORT).show()
            }
        }

        connectButton.setOnClickListener {
            val url = urlInput.text.toString().trim()
            if (url.isEmpty()) {
                Toast.makeText(this, "Введите адрес камеры", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            prefs.edit().putString("url", url).apply()
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }
    }
}
