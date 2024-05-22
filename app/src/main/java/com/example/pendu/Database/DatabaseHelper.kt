package com.example.pendu.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Pendu.db"

        private const val TABLE_HISTORIQUE = "Historique"
        private const val TABLE_DICTIONNAIRE = "Dictionnaire"
        private const val TABLE_PREF = "Preference"

        private const val COLUMN_ID = "id"
        private const val COLUMN_MOT = "mot"
        private const val COLUMN_DIFFICULTÉ = "difficulté"
        private const val COLUMN_LANGAGE = "langage"
        private const val COLUMN_TEMPS = "temps"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_HISTORIQUE = "CREATE TABLE $TABLE_HISTORIQUE($COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_MOT TEXT, $COLUMN_DIFFICULTÉ TEXT, $COLUMN_TEMPS INTEGER)"
        db?.execSQL(CREATE_TABLE_HISTORIQUE)

        val CREATE_TABLE_DICTIONNAIRE = "CREATE TABLE $TABLE_DICTIONNAIRE($COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_MOT TEXT, $COLUMN_DIFFICULTÉ TEXT, $COLUMN_LANGAGE TEXT)"
        db?.execSQL(CREATE_TABLE_DICTIONNAIRE)

        val CREATE_TABLE_PREFERENCE = "CREATE TABLE $TABLE_PREF($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_DIFFICULTÉ TEXT, " + "$COLUMN_LANGAGE TEXT)"
        db?.execSQL(CREATE_TABLE_PREFERENCE)

        Pref(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_HISTORIQUE = "DROP TABLE IF EXISTS $TABLE_HISTORIQUE"
        db?.execSQL(DROP_TABLE_HISTORIQUE)

        val DROP_TABLE_DICTIONNAIRE = "DROP TABLE IF EXISTS $TABLE_DICTIONNAIRE"
        db?.execSQL(DROP_TABLE_DICTIONNAIRE)

        val DROP_TABLE_PREFERENCE = "DROP TABLE IF EXISTS $TABLE_PREF"
        db?.execSQL(DROP_TABLE_PREFERENCE)

         onCreate(db)
    }

    fun insertHistorique(historique: Historique){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MOT, historique.mot)
            put(COLUMN_DIFFICULTÉ, historique.difficulté)
            put(COLUMN_TEMPS, historique.temps)
        }
        db.insert(TABLE_HISTORIQUE, null, values)
        db.close()
    }

    fun getAllHistorique(): List<Historique>{
        val historiqueListe = mutableListOf<Historique>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_HISTORIQUE"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val mot = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOT))
            val difficulté = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIFFICULTÉ))
            val temps = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TEMPS))

            val historique = Historique(mot, difficulté, temps)
            historiqueListe.add(historique)
        }
        cursor.close()
        db.close()
        return historiqueListe
    }

    fun getAllDictionnaire(): List<Dictionnaire>{
        val dictionnaireListe = mutableListOf<Dictionnaire>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_DICTIONNAIRE"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val mot = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOT))
            val difficulté = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIFFICULTÉ))
            val langage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGAGE))

            val dictionnaire = Dictionnaire(id, mot, difficulté, langage)
            dictionnaireListe.add(dictionnaire)
        }
        cursor.close()
        db.close()
        return dictionnaireListe
    }

    fun getMotsDictionnaire(difficulté:String, langage:String):List<String>{
        val motsListe = mutableListOf<String>()
        val db = readableDatabase
        val selectionMot = "$COLUMN_DIFFICULTÉ = ? AND $COLUMN_LANGAGE = ?"
        val selectionArgs = arrayOf(difficulté, langage)
        val query = "SELECT $COLUMN_MOT FROM $TABLE_DICTIONNAIRE WHERE $selectionMot"
        val cursor = db.rawQuery(query, selectionArgs)
        while (cursor.moveToNext()){
            val mot = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOT))
            motsListe.add(mot)
        }
        cursor.close()
        db.close()
        return motsListe
    }

    private fun Pref(db: SQLiteDatabase?) {
        val pref = Preference(1,"Français", Preference.EASY)
        val valeur = ContentValues().apply {
            put(COLUMN_LANGAGE, pref.langue)
            put(COLUMN_DIFFICULTÉ, pref.difficulte)
        }
        db?.insert(TABLE_PREF, null, valeur)
    }

    fun update_preference(key: String, value: String) {
        val db = writableDatabase
        val values = ContentValues()
        val columnName = when (key) {
            "language" -> COLUMN_LANGAGE
            "difficulte" -> COLUMN_DIFFICULTÉ
            else -> return
        }
        values.put(columnName, value)

        try {
            db.update(TABLE_PREF, values, "$COLUMN_ID = ?", arrayOf("1"))
        } finally {
            db.close()
        }
    }
    fun getLanguagePreference(): String? {
        val db = readableDatabase
        val query = "SELECT $COLUMN_LANGAGE FROM $TABLE_PREF WHERE $COLUMN_ID = 1"
        val cursor = db.rawQuery(query, null)
        var language: String? = null
        if (cursor.moveToFirst()) {
            language = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGAGE))
        }
        cursor.close()
        db.close()
        return language
    }

    fun getDifficultePreference(): String? {
        val db = readableDatabase
        val query = "SELECT $COLUMN_DIFFICULTÉ FROM $TABLE_PREF WHERE $COLUMN_ID = 1"
        val cursor = db.rawQuery(query, null)
        var difficulte: String? = null
        if (cursor.moveToFirst()) {
            difficulte = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIFFICULTÉ))
        }
        cursor.close()
        db.close()
        return difficulte
    }

    fun Add_Mot(mot: Dictionnaire) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MOT, mot.mot)
            put(COLUMN_DIFFICULTÉ, mot.difficulté)
            put(COLUMN_LANGAGE, mot.langage)
        }
        db.insert(TABLE_DICTIONNAIRE, null, values)
        db.close()
    }

    fun Delete_Mot(mot: String) {
        val db = writableDatabase
        db.delete(TABLE_DICTIONNAIRE, "$COLUMN_MOT = ?", arrayOf(mot))
        db.close()
    }
}