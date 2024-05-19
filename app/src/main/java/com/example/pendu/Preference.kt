package com.example.pendu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.pendu.Database.DatabaseHelper

class Preference : AppCompatActivity() {

    lateinit var radioBtnFr: RadioButton
    lateinit var radioBtnEn: RadioButton
    lateinit var radioBtnEasy: RadioButton
    lateinit var radioBtnMedium: RadioButton
    lateinit var radioBtnHard: RadioButton
    lateinit var btn_dictionnaire: Button
    lateinit var btn_return: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        databaseHelper = DatabaseHelper(this)

        radioBtnFr = findViewById(R.id.RBfrancais)
        radioBtnEn = findViewById(R.id.RBanglais)
        radioBtnEasy = findViewById(R.id.RBfacile)
        radioBtnMedium = findViewById(R.id.RBmoyen)
        radioBtnHard = findViewById(R.id.RBdifficile)
        btn_dictionnaire = findViewById(R.id.btnDictionnaire)
        btn_return = findViewById(R.id.btnRetour)

        btn_return.setOnClickListener {
            startActivity(Intent(this, Accueil::class.java))
            finish()
        }

        btn_dictionnaire.setOnClickListener {
            startActivity(Intent(this, Dictionnaire::class.java))
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton && view.isChecked) {
            when (view.id) {
                R.id.RBfrancais, R.id.RBanglais -> {
                    val language = if (view.id == R.id.RBfrancais) "French" else "English"
                    databaseHelper.update_preference("language", language)
                }
                R.id.RBfacile, R.id.RBmoyen, R.id.RBdifficile -> {
                    val difficulty = when (view.id) {
                        R.id.RBfacile -> "Easy"
                        R.id.RBmoyen -> "Medium"
                        R.id.RBdifficile -> "Hard"
                        else -> ""
                    }
                    databaseHelper.update_preference("difficulty", difficulty)
                }
            }
        }
    }
}