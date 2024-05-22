package com.example.pendu

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class JeuTest {

    var jeu: Jeu = Jeu()

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
}