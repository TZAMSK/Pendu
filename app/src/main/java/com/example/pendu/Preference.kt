package com.example.pendu

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.util.Locale

class Preference : AppCompatActivity() {

    lateinit var radioBtnFr : RadioButton
    lateinit var radioBtnEn : RadioButton
    lateinit var radioBtnEasy : RadioButton
    lateinit var radioBtnMedium : RadioButton
    lateinit var radioBtnHard : RadioButton
    lateinit var btn_dictionnaire : Button
    lateinit var btn_return : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        radioBtnFr = findViewById(R.id.RBfrancais)
        radioBtnEn = findViewById(R.id.RBanglais)
        radioBtnEasy = findViewById(R.id.RBfacile)
        radioBtnMedium = findViewById(R.id.RBmoyen)
        radioBtnHard = findViewById(R.id.RBdifficile)
        btn_dictionnaire = findViewById(R.id.btnDictionnaire)
        btn_return = findViewById(R.id.btnRetour)


        btn_return.setOnClickListener {
            val intent = Intent(this, Accueil::class.java)
            startActivity(intent)
            finish()
        }
        /*btn_dictionnaire.setOnClickListener {
            val intent = Intent(this, Dictionnaire::class.java)
            startActivity(intent)
        }*/
    }
}