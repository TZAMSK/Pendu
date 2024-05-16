package com.example.pendu

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

var listeLettres = emptyArray<Button>()

//Liste des lettres pour les boutons
var listeMaj = arrayOf("A", "B", "C", "D", "E", "F", "G",
    "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
    "S", "T", "U", "V", "W", "X", "Y", "Z")

var pointage = 0
var nbErreurs = 0

val listeDeMots = arrayOf("arbre", "cheval", "pendu", "maison", "temps", "java",
    "kotlin", "apple", "jazz", "avion", "partager", "cercle",
    "math")

var motÀDeviner = listeDeMots.random()
var motÀDevinerMinuscule = motÀDeviner.lowercase()

var lettresEssayées = mutableListOf<Char>()

lateinit var txtScore: TextView
lateinit var txtMot: TextView
lateinit var imgPendu: ImageView
lateinit var btnRecommancer: Button

//Couleur
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

        for (letter in listeMaj) {
            val buttonId = resources.getIdentifier("btn$letter", "id", packageName)
            if (buttonId != 0) {
                val btnLettre = findViewById<Button>(buttonId)
                listeLettres += btnLettre
                btnLettre.setOnClickListener {
                    val lettre = btnLettre.text.toString()[0]
                    btnLettre.setBackgroundColor(COULEUR_CLIQUÉ)
                    btnLettre.isEnabled = false
                }
            }
        }

        btnRecommancer.setOnClickListener{
            réinitialiser()
            for(lettreBouton in listeLettres){
                lettreBouton.setBackgroundColor(COULEUR_NORMAL)
                lettreBouton.isEnabled = true
            }
            txtMot.setTextColor(Color.BLACK)
        }
    }

    fun afficherMot(mot:String){
        val motMasqué = mot.replace(Regex("[a-zA-z]"), "#")
        txtMot.text = motMasqué
    }

    fun réinitialiser(){
        pointage = 0
        nbErreurs = 0
        motÀDeviner = listeDeMots.random()
        motÀDevinerMinuscule = motÀDeviner.lowercase()
        lettresEssayées.clear()
        txtScore.text = pointage.toString()
    }
}