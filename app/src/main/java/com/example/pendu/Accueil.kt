package com.example.pendu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Accueil : AppCompatActivity() {

    private lateinit var txtWelcome : TextView
    private lateinit var btn_play: Button
    private lateinit var btn_history: Button
    private lateinit var btn_setting: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accueil)

        btn_play = findViewById(R.id.btnPlay)
        btn_history = findViewById(R.id.btnHistory)
        btn_setting = findViewById(R.id.btnSetting)

        btn_play.setOnClickListener {
            val intent = Intent(this, Jeu::class.java)

            startActivity(intent)
        }

        btn_history.setOnClickListener {
            val intent = Intent(this, Historique::class.java)
            startActivity(intent)
        }

        btn_setting.setOnClickListener {
            val intent = Intent(this, Preference::class.java)
            startActivity(intent)
        }
    }
}
