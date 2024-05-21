package com.example.pendu

import android.content.Intent
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
    private lateinit var databaseHelper: DatabaseHelper

    companion object {
        val listeDeMots = mutableListOf<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        databaseHelper = DatabaseHelper(this)

        radioBtnFr = findViewById(R.id.RBfrancais)
        radioBtnEn = findViewById(R.id.RBanglais)
        radioBtnEasy = findViewById(R.id.RBfacile)
        radioBtnMedium = findViewById(R.id.RBmoyen)
        radioBtnHard = findViewById(R.id.RBdifficile)
        btnDictionnaire = findViewById(R.id.btnDictionnaire)
        btnRetour = findViewById(R.id.btnRetour)

        loadPreferences()

        btnRetour.setOnClickListener {
            startActivity(Intent(this, Accueil::class.java))
            finish()
        }

        btnDictionnaire.setOnClickListener {
            startActivity(Intent(this, Dictionnaire::class.java))
        }
    }

    private fun loadPreferences() {
        val language = databaseHelper.getLanguagePreference()
        val difficulte = databaseHelper.getDifficultePreference()

        language?.let {
            when (it) {
                "Francais" -> radioBtnFr.isChecked = true
                "Anglais" -> radioBtnEn.isChecked = true
            }
        }

        difficulte?.let {
            when (it) {
                "Facile" -> radioBtnEasy.isChecked = true
                "Moyen" -> radioBtnMedium.isChecked = true
                "Difficile" -> radioBtnHard.isChecked = true
            }
        }

        if (language != null && difficulte != null) {
            updateListDeMots(language, difficulte)
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton && view.isChecked) {
            val language = if (radioBtnFr.isChecked) "Francais" else if (radioBtnEn.isChecked) "Anglais" else ""
            val difficulte = when {
                radioBtnEasy.isChecked -> "Facile"
                radioBtnMedium.isChecked -> "Moyen"
                radioBtnHard.isChecked -> "Difficile"
                else -> ""
            }

            if (view.id == R.id.RBfrancais || view.id == R.id.RBanglais) {
                databaseHelper.update_preference("language", language)
            } else {
                databaseHelper.update_preference("difficulte", difficulte)
            }

            updateListDeMots(language, difficulte)
        }
    }

    private fun updateListDeMots(language: String, difficulte: String) {
        listeDeMots.clear()
        listeDeMots.addAll(databaseHelper.getMotsDictionnaire(language, difficulte))
    }
}
