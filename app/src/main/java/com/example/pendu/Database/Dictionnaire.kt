package com.example.pendu.Database

data class Dictionnaire(val id:Int, val mot:String, val difficulté:String, val langage:String){
    companion object {
        const val EASY = "Facile"
        const val MEDIUM = "Moyen"
        const val HARD = "Difficile"
        const val FRENCH = "Francais"
        const val ENGLISH = "Anglais"
    }
}
