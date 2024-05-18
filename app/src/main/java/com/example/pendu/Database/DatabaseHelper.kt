package com.example.pendu.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Pendu.db"

        private const val TABLE_HISTORIQUE = "Historique"
        private const val TABLE_DICTIONNAIRE = "Dictionnaire"

        private const val COLUMN_ID = "id"
        private const val COLUMN_MOT = "mot"
        private const val COLUMN_DIFFICULTE = "difficulté"
        private const val COLUMN_LANGAGE = "langage"
        private const val COLUMN_TEMPS = "temps"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_HISTORIQUE = "CREATE TABLE $TABLE_HISTORIQUE($COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_MOT TEXT, $COLUMN_DIFFICULTE TEXT, $COLUMN_TEMPS INTEGER)"
        db?.execSQL(CREATE_TABLE_HISTORIQUE)

        val CREATE_TABLE_DICTIONNAIRE = "CREATE TABLE $TABLE_DICTIONNAIRE($COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_MOT TEXT, $COLUMN_DIFFICULTE TEXT, $COLUMN_LANGAGE TEXT)"
        db?.execSQL(CREATE_TABLE_DICTIONNAIRE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}