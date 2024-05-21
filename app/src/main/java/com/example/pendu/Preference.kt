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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        databaseHelper = DatabaseHelper(this)

        radioBtnFr = findViewById(R.id.RBfrancais)
        radioBtnEn = findViewById(R.id.RBanglais)
        radioBtnEasy = findViewById(R.id.RBfacile)
        radioBtnMedium = findViewById(R.id.RBmoyen)
        radioBtnHard = findViewById(R.id.RBdifficile)
        btnRetour = findViewById(R.id.btnRetour)
        btnDictionnaire = findViewById(R.id.btnDictionnaire)

        loadPreferences()

        btnRetour.setOnClickListener {
            startActivity(Intent(this, Accueil::class.java))
            finish()
        }

        btnDictionnaire .setOnClickListener {
            startActivity(Intent(this, Dictionnaire::class.java))
            finish()
        }
    }

    private fun loadPreferences() {
        val language = databaseHelper.getLanguagePreference()
        val difficulty = databaseHelper.getDifficultePreference()

        language?.let { updateLanguageRadioButton(it) }
        difficulty?.let { updateDifficultyRadioButton(it) }
    }

    private fun updateLanguageRadioButton(language: String) {
        when (language) {
            "Francais" -> radioBtnFr.isChecked = true
            "Anglais" -> radioBtnEn.isChecked = true
        }
    }

    private fun updateDifficultyRadioButton(difficulty: String) {
        radioBtnEasy.isChecked = (difficulty == "Facile")
        radioBtnMedium.isChecked = (difficulty == "Moyen")
        radioBtnHard.isChecked = (difficulty == "Difficile")
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val language = when (view.id) {
                R.id.RBfrancais -> "Francais"
                R.id.RBanglais -> "Anglais"
                else -> return
            }

            val difficulty = when (view.id) {
                R.id.RBfacile -> "Facile"
                R.id.RBmoyen -> "Moyen"
                R.id.RBdifficile -> "Difficile"
                else -> return
            }

            databaseHelper.update_preference("language", language)
            databaseHelper.update_preference("difficulte", difficulty)

            // Start the Jeu activity with selected language and difficulty
            val intent = Intent(this, Jeu::class.java)
            intent.putExtra("language", language)
            intent.putExtra("difficulty", difficulty)
            startActivity(intent)
        }
    }
}
