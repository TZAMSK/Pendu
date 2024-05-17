package com.example.pendu

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

var listeLettres = emptyArray<Button>()

// Liste des lettres pour les boutons
val listeMaj = arrayOf(
    "A", "B", "C", "D", "E", "F", "G",
    "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
    "S", "T", "U", "V", "W", "X", "Y", "Z"
)

var pointage = 0
var nbErreurs = 0

val listeDeMots = arrayOf(
    "arbre", "cheval", "pendu", "maison", "temps", "java",
    "kotlin", "apple", "jazz", "avion", "partager", "cercle",
    "math"
)

var motÀDeviner = listeDeMots.random()
var motÀDevinerMinuscule = motÀDeviner.lowercase()

var lettresEssayées = mutableListOf<Char>()

lateinit var txtScore: TextView
lateinit var txtMot: TextView
lateinit var imgPendu: ImageView
lateinit var btnRecommancer: Button

// Couleur
val COULEUR_CLIQUÉ = 0xFF701010.toInt()
val COULEUR_NORMAL = 0xFF6200EE.toInt()

class Jeu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu)

        txtScore = findViewById(R.id.txtScore)
        txtMot = findViewById(R.id.txtMot)
        txtMot.setTextColor(Color.BLACK)
        imgPendu = findViewById(R.id.imgPendu)
        btnRecommancer = findViewById(R.id.btnRecommencer)

        txtMot.text = afficherMot(motÀDeviner)

        for (letter in listeMaj) {
            val buttonId = resources.getIdentifier("btn$letter", "id", packageName)
            if (buttonId != 0) {
                val btnLettre = findViewById<Button>(buttonId)
                listeLettres += btnLettre
                btnLettre.setOnClickListener {
                    val lettre = btnLettre.text.toString()[0]
                    btnLettre.setBackgroundColor(COULEUR_CLIQUÉ)
                    btnLettre.isEnabled = false
                    essayerUneLettre(lettre)
                }
            }
        }

        btnRecommancer.setOnClickListener {
            réinitialiser()
            for (lettreBouton in listeLettres) {
                lettreBouton.setBackgroundColor(COULEUR_NORMAL)
                lettreBouton.isEnabled = true
            }
            txtMot.setTextColor(Color.BLACK)
        }
    }

    fun afficherMot(mot: String): String {
        return mot.map { if (lettresEssayées.contains(it.uppercaseChar())) it else '#' }.joinToString("")
    }

    fun réinitialiser() {
        pointage = 0
        nbErreurs = 0
        motÀDeviner = listeDeMots.random()
        motÀDevinerMinuscule = motÀDeviner.lowercase()
        lettresEssayées.clear()
        txtScore.text = score().toString()
        txtMot.text = afficherMot(motÀDeviner)
        imgPendu.setImageResource(R.drawable.err01)
    }

    fun score(): Int {
        return pointage - nbErreurs
    }

    fun étatLettres(): CharArray {
        val motÀDevinerMajuscule = motÀDeviner.uppercase()
        val caractère = CharArray(motÀDevinerMajuscule.length)
        for (i in motÀDevinerMajuscule.indices) {
            val lettre = motÀDevinerMajuscule[i]
            if (lettresEssayées.contains(lettre)) {
                caractère[i] = lettre
            } else {
                caractère[i] = '#'
            }
        }
        return caractère
    }

    fun afficherÉtatLettres(état: String) {
        txtMot.text = état
    }

    fun afficherÉtatLettres(état: CharArray) {
        afficherÉtatLettres(String(état))
    }

    fun essayerUneLettre(lettre: Char): Boolean {
        val lettreMinuscule = lettre.lowercaseChar()
        if (motÀDevinerMinuscule.contains(lettreMinuscule)) {
            pointage++
            lettresEssayées.add(lettre.uppercaseChar())
            val état = étatLettres()
            afficherÉtatLettres(état)
            txtScore.text = score().toString()
            return true
        } else {
            nbErreurs++
            lettresEssayées.add(lettre.uppercaseChar())
            val état = étatLettres()
            afficherÉtatLettres(état)
            afficherPendu(nbErreurs)
            txtScore.text = score().toString()
            return false
        }
    }

    fun afficherPendu(nbErreurs: Int) {
        val drawableId = when (nbErreurs) {
            0 -> R.drawable.err01
            1 -> R.drawable.err01
            2 -> R.drawable.err02
            3 -> R.drawable.err03
            4 -> R.drawable.err04
            5 -> R.drawable.err05
            else -> {
                txtMot.text = motÀDeviner
                R.drawable.err06
            }
        }
        imgPendu.setImageResource(drawableId)
    }
}