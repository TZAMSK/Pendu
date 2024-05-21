package com.example.pendu.Database

data class Dictionnaire(val id:Int, val mot:String, val difficulté:String, val langage:String){
    companion object {
        const val EASY = "FACILE"
        const val MEDIUM = "MOYEN"
        const val HARD = "DIFFICILE"
        const val FRENCH = "FRANCAIS"
        const val ENGLISH = "ANGLAIS"
    }
}
