package com.example.pendu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.pendu.Database.DatabaseHelper

class Preference : AppCompatActivity() {

    private lateinit var radioBtnFr: RadioButton
    private lateinit var radioBtnEn: RadioButton
    private lateinit var radioBtnEasy: RadioButton
    private lateinit var radioBtnMedium: RadioButton
    private lateinit var radioBtnHard: RadioButton
    private lateinit var btnDictionnaire: Button
    private lateinit var btnRetour: Button
    private lateinit var btnOK: Button
            private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        initializeViews()
        loadCheckedRadioButtons()
        setupListeners()
    }

    private fun initializeViews() {
        radioBtnFr = findViewById(R.id.RBfrancais)
        radioBtnEn = findViewById(R.id.RBanglais)
        radioBtnEasy = findViewById(R.id.RBfacile)
        radioBtnMedium = findViewById(R.id.RBmoyen)
        radioBtnHard = findViewById(R.id.RBdifficile)
        btnRetour = findViewById(R.id.btnRetour)
        btnOK = findViewById(R.id.btnOK)

        btnDictionnaire = findViewById(R.id.btnDictionnaire)
    }

    private fun setupListeners() {
        btnRetour.setOnClickListener {
            navigerVersAccueil()
        }

        btnDictionnaire.setOnClickListener {
            navigerVersDictionnaire()
        }

        btnOK.setOnClickListener {
            val intent = Intent(this, Jeu::class.java).apply {
                putExtra("language", if (radioBtnFr.isChecked) "Francais" else "Anglais")
                putExtra("difficulty", if (radioBtnEasy.isChecked) "Facile" else if (radioBtnMedium.isChecked) "Moyen" else "Difficile")
            }
            startActivity(intent)
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            saveCheckedRadioButtons()
        }
    }

    private fun navigerVersAccueil() {
        saveCheckedRadioButtons()
        startActivity(Intent(this, Accueil::class.java))
        finish()
    }

    private fun navigerVersDictionnaire() {
        saveCheckedRadioButtons()
        startActivity(Intent(this, Dictionnaire::class.java))
        finish()
    }


    /*
    private fun startGame() {
        val language = sharedPreferences.getString("language", "Francais")
        val difficulty = sharedPreferences.getString("difficulty", "Facile")

        val intent = Intent(this, Jeu::class.java)
        intent.putExtra("language", language)
        intent.putExtra("difficulty", difficulty)
        startActivity(intent)
    }

     */

    private fun saveCheckedRadioButtons() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("francaisChecked", radioBtnFr.isChecked)
        editor.putBoolean("anglaisChecked", radioBtnEn.isChecked)
        editor.putBoolean("facileChecked", radioBtnEasy.isChecked)
        editor.putBoolean("moyenChecked", radioBtnMedium.isChecked)
        editor.putBoolean("difficileChecked", radioBtnHard.isChecked)
        editor.apply()
    }

    private fun loadCheckedRadioButtons() {
        radioBtnFr.isChecked = sharedPreferences.getBoolean("francaisChecked", true)
        radioBtnEn.isChecked = sharedPreferences.getBoolean("anglaisChecked", false)
        radioBtnEasy.isChecked = sharedPreferences.getBoolean("facileChecked", true)
        radioBtnMedium.isChecked = sharedPreferences.getBoolean("moyenChecked", false)
        radioBtnHard.isChecked = sharedPreferences.getBoolean("difficileChecked", false)
    }
}
