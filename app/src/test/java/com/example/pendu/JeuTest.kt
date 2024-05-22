package com.example.pendu

import com.example.pendu.Database.Historique
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class JeuTest {

    var listHistorique: List<Historique> = emptyList()
    var jeu: Jeu = Jeu()
    var historyRecyclerAdapter: HistoryRecyclerAdapter = HistoryRecyclerAdapter(listHistorique)

    @Test
    fun testAfficherMot_initial() {
        jeu.lettresEssayées.clear()
        val displayedWord = jeu.afficherMot("TEST")
        assertEquals("####", displayedWord)
    }

    @Test
    fun testAfficherMot_afterGuess() {
        jeu.lettresEssayées.add('T')
        val displayedWord = jeu.afficherMot("TEST")
        assertEquals("T##T", displayedWord)
    }

    @Test
    fun testEssayerUneLettre_correctGuess() {
        jeu.pointage = 0
        jeu.nbErreurs = 0
        jeu.lettresEssayées.clear()

        jeu.essayerUneLettre('T')
        assertEquals(1, jeu.pointage)
        assertEquals(0, jeu.nbErreurs)
        assertTrue(jeu.lettresEssayées.contains('T'))
        assertEquals("T##T", jeu.txtMot.text)
    }

    @Test
    fun testEssayerUneLettre_incorrectGuess() {
        jeu.pointage = 0
        jeu.nbErreurs = 0
        jeu.lettresEssayées.clear()

        jeu.essayerUneLettre('X')
        assertEquals(0, jeu.pointage)
        assertEquals(1, jeu.nbErreurs)
        assertTrue(jeu.lettresEssayées.contains('X'))
        assertEquals("####", jeu.txtMot.text)
    }

    @Test
    fun testScoreCalculation() {
        jeu.pointage = 5
        jeu.nbErreurs = 2
        assertEquals(3, jeu.score())
    }

    @Test
    fun testResetGame() {
        jeu.pointage = 5
        jeu.nbErreurs = 2
        jeu.lettresEssayées.add('T')
        jeu.resetGame()

        assertEquals(0, jeu.pointage)
        assertEquals(0, jeu.nbErreurs)
        assertTrue(jeu.lettresEssayées.isEmpty())
        assertEquals("####", jeu.afficherMot(jeu.motÀDeviner))
    }

    @Test
    fun testGameWin() {
        jeu.lettresEssayées.addAll(listOf('T', 'E', 'S'))
        jeu.txtMot.text = "T#ST"
        jeu.essayerUneLettre('T')
        assertTrue(jeu.txtMot.text.toString().contains("TEST"))
    }

    @Test
    fun testAfficherMot_noGuesses() {
        jeu.lettresEssayées.clear()
        val displayedWord = jeu.afficherMot("HANGMAN")
        assertEquals("#######", displayedWord)
    }

    @Test
    fun testGameLost() {
        jeu.nbErreurs = 5
        jeu.txtMot.text = "####"
        jeu.essayerUneLettre('X')

        assertEquals("TEST", jeu.txtMot.text.toString())
    }

    @Test
    fun testTemps() {
        val tempsMillis = 3661000L
        val result = historyRecyclerAdapter.Temps(tempsMillis)
        assertEquals("01h 01m 01s", result)
    }

}