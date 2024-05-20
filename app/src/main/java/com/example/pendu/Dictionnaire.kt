package com.example.pendu

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pendu.Database.Dictionnaire
import com.example.pendu.Database.DatabaseHelper

class Dictionnaire : AppCompatActivity() {

    lateinit var motEditText: EditText
    lateinit var btnAdd: Button
    lateinit var btnDelete: Button
    lateinit var diffSpinner: Spinner
    lateinit var diffSpinner2: Spinner
    lateinit var listRecycler: RecyclerView
    lateinit var radioBtnFr: RadioButton
    lateinit var radioBtnEn: RadioButton
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var dictionnaireAdapter: DictionnaireRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionnaire)

        databaseHelper = DatabaseHelper(this)

        motEditText = findViewById(R.id.Add_mot)
        btnAdd = findViewById(R.id.btnAdd)
        btnDelete = findViewById(R.id.btnDelete)
        diffSpinner = findViewById(R.id.diff_spinner)
        diffSpinner2 = findViewById(R.id.diff_spinner2)
        listRecycler = findViewById(R.id.list_mot)
        radioBtnFr = findViewById(R.id.radioButtonFR)
        radioBtnEn = findViewById(R.id.radioButtonEN)

        val difficulties = arrayOf(Dictionnaire.EASY, Dictionnaire.MEDIUM, Dictionnaire.HARD)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficulties)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        diffSpinner.adapter = adapter
        diffSpinner2.adapter = adapter

        listRecycler.layoutManager = LinearLayoutManager(this)
        dictionnaireAdapter = DictionnaireRecyclerAdapter(mutableListOf())
        listRecycler.adapter = dictionnaireAdapter

        btnAdd.setOnClickListener {
            val motCree = motEditText.text.toString()
            val selectedDifficulty = diffSpinner.selectedItem.toString()
            val selectedLanguage = if (radioBtnFr.isChecked) Dictionnaire.FRENCH else Dictionnaire.ENGLISH
            val mot = Dictionnaire(0, motCree, selectedDifficulty, selectedLanguage)
            databaseHelper.Add_Mot(mot)
            dictionnaireAdapter.addMots(motCree)
            motEditText.text.clear()
        }

        btnDelete.setOnClickListener {

        }

        diffSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedDifficulty = parent.getItemAtPosition(position) as String
                updateListMot(selectedDifficulty)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateListMot(difficulte: String) {
        val langage = if (radioBtnFr.isChecked) Dictionnaire.FRENCH else Dictionnaire.ENGLISH
        val mots = databaseHelper.getMotsDictionnaire(difficulte, langage)
        dictionnaireAdapter.updateMots(mots)
    }
}
