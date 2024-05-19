package com.example.pendu

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dictionnaire : AppCompatActivity() {

    lateinit var mot : EditText
    lateinit var btnADD : Button
    lateinit var btnDELETE : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionnaire)

        mot = findViewById(R.id.Add_mot)
        btnADD = findViewById(R.id.btnAdd)
        btnDELETE = findViewById(R.id.btnDelete)

        btnADD.setOnClickListener {
            var mot_cree = mot.text.toString()
        }

    }
}