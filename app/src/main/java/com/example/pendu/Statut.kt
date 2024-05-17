package com.example.pendu

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Statut : AppCompatActivity() {

    lateinit var message:TextView
    lateinit var btnRejouer:Button
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statut)

        message = findViewById(R.id.message)
        btnRejouer = findViewById(R.id.btnRejouer)

        val extra = intent.extras
        val statut = extra?.getString("statut")
        val motCahe = extra?.getString("motCache")
        if (motCahe == null){
            message.text = statut
        }
        else
        {
            message.text = statut +" "+ motCahe

        }

        btnRejouer.setOnClickListener{

            finish()
        }

    }



}