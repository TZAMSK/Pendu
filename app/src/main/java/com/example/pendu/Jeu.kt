package com.example.pendu

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pendu.Database.DatabaseHelper
import com.example.pendu.Database.Historique

class Jeu : AppCompatActivity() {

    private lateinit var txtScore: TextView
    lateinit var txtMot: TextView
    private lateinit var imgPendu: ImageView
    private lateinit var btnRecommencer: Button
    private var listeLettres = emptyArray<Button>()
    var lettresEssayées = mutableListOf<Char>()

    var pointage = 0
    var nbErreurs = 0
    private val listeMaj = arrayOf(
        "A", "B", "C", "D", "E", "F", "G",
        "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
        "S", "T", "U", "V", "W", "X", "Y", "Z"
    )

    private var tempsDebut: Long = 0
    private var tempsEcouler: Long = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable{
        override fun run() {
            val tempsMtn = System.currentTimeMillis()
            tempsEcouler =tempsMtn - tempsDebut
            handler.postDelayed(this,1000)
        }
    }
    var tempsFormater: String = ""

    private lateinit var motsList: List<String>
    lateinit var motÀDeviner: String
    private lateinit var motÀDevinerMinuscule: String

    private val COULEUR_CLIQUÉ = 0xFF701010.toInt()
    private val COULEUR_NORMAL = 0xFF6200EE.toInt()

    private lateinit var language: String
    private lateinit var difficulty: String
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu)
        commencerTemps()


        language = intent.getStringExtra("language") ?: "Francais"
        difficulty = intent.getStringExtra("difficulty") ?: "Facile"

        databaseHelper = DatabaseHelper(this)

        // Fetch the list of words from the database based on language and difficulty
        motsList = databaseHelper.getMotsDictionnaire(difficulty, language)

        if(motsList.isEmpty()){
            Toast.makeText(this,"Il n'y a pas de mots  en ${language} et ${difficulty}", Toast.LENGTH_LONG).show()
            throw IllegalStateException("Il n'y a pas de mots en $language et $difficulty")
            finish()
            return
        }

        // Select a word randomly from the list
        motÀDeviner = motsList.random()
        motÀDevinerMinuscule = motÀDeviner.lowercase()

        initierComposants()
        initierMot()
        initierBoutons()
    }

    private fun initierComposants() {
        txtScore = findViewById(R.id.txtScore)
        txtMot = findViewById(R.id.txtMot)
        imgPendu = findViewById(R.id.imgPendu)
        btnRecommencer = findViewById(R.id.btnRecommencer)

        txtMot.setTextColor(Color.BLACK)
        btnRecommencer.setOnClickListener { resetGame() }
    }

    private fun initierMot() {
        txtMot.text = afficherMot(motÀDeviner)
    }

    private fun initierBoutons() {
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
    }

    fun afficherMot(mot: String): String {
        return mot.map { if (lettresEssayées.contains(it.uppercaseChar())) it else '#' }.joinToString("")
    }

    fun resetGame() {
        pointage = 0
        nbErreurs = 0
        // Select a new word randomly from the list
        motÀDeviner = motsList.random()
        motÀDevinerMinuscule = motÀDeviner.lowercase()
        lettresEssayées.clear()
        txtScore.text = score().toString()
        txtMot.text = afficherMot(motÀDeviner)
        imgPendu.setImageResource(R.drawable.err01)

        for (lettreBouton in listeLettres) {
            lettreBouton.setBackgroundColor(COULEUR_NORMAL)
            lettreBouton.isEnabled = true
        }
        txtMot.setTextColor(Color.BLACK)
    }

    fun score(): Int {
        return pointage - nbErreurs
    }

    private fun étatLettres(): CharArray {
        val motÀDevinerMajuscule = motÀDeviner.uppercase()
        val caractère = CharArray(motÀDevinerMajuscule.length)
        for (i in motÀDevinerMajuscule.indices) {
            val lettre = motÀDevinerMajuscule[i]
            caractère[i] = if (lettresEssayées.contains(lettre)) lettre else '#'
        }
        return caractère
    }

    private fun afficherÉtatLettres(état: CharArray) {
        txtMot.text = String(état)
    }

    fun essayerUneLettre(lettre: Char) {
        val lettreMinuscule = lettre.lowercaseChar()
        if (motÀDevinerMinuscule.contains(lettreMinuscule)) {
            pointage++
            lettresEssayées.add(lettre.uppercaseChar())
            afficherÉtatLettres(étatLettres())
            txtScore.text = score().toString()

            Log.d("mot pour l'instant", txtMot.text.toString().lowercase())
            Log.d("mot cache", motÀDeviner)
            if (txtMot.text.toString().lowercase() == motÀDeviner) {
                afficherFin(getString(R.string.txt_winner))
            }
        } else {
            nbErreurs++
            lettresEssayées.add(lettre.uppercaseChar())
            afficherÉtatLettres(étatLettres())
            afficherPendu(nbErreurs)
            txtScore.text = score().toString()
        }
    }

    private fun afficherFin(statut: String) {
        val intention = Intent(this, Statut::class.java)
        intention.putExtra("statut", statut)
        if (nbErreurs >= 6) {
            intention.putExtra("motCache", motÀDevinerMinuscule)
        }
        startActivity(intention)
        resetGame()
        recreate()
    }

    private fun afficherPendu(nbErreurs: Int) {
        val drawableId = when (nbErreurs) {
            0 -> R.drawable.err01
            1 -> R.drawable.err02
            2 -> R.drawable.err03
            3 -> R.drawable.err04
            4 -> R.drawable.err05
            5 -> R.drawable.err06
            else -> {
                txtMot.text = motÀDeviner
                afficherFin(getString(R.string.txt_loser))
                R.drawable.err06
            }
        }
        imgPendu.setImageResource(drawableId)
    }

    override fun onDestroy() {
        super.onDestroy()
        arreterTemps()

        var historique = Historique(motÀDeviner,difficulty,tempsEcouler)
        databaseHelper.insertHistorique(historique)

    }
    private fun commencerTemps(){
        tempsDebut = System.currentTimeMillis()
        handler.post(runnable)
    }
    private fun arreterTemps():Long{
        handler.removeCallbacks(runnable)
        tempsEcouler = System.currentTimeMillis() - tempsDebut
       /* val totalSeconds = tempsEcouler / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        tempsFormater = String.format("%d:%02d", minutes, seconds)
        */
        Log.d("temps",tempsEcouler.toString())
        return tempsEcouler
    }


}
