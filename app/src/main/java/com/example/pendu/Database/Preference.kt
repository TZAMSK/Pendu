package com.example.pendu.Database

data class Preference(var id : Int = 0, var langue: String, var difficulte: String){
    companion object {
        const val EASY = "Facile"
        const val MEDIUM = "Moyen"
        const val HARD = "Difficile"
    }
}