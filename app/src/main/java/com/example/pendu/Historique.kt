package com.example.pendu

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pendu.Database.DatabaseHelper
import com.example.pendu.Database.Historique

class Historique : AppCompatActivity() {
    lateinit var listHistorique: List<Historique>
    lateinit var recycler: RecyclerView
    lateinit var dataBasehelper: DatabaseHelper
    lateinit var btnRetour2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique)

        btnRetour2 = findViewById(R.id.btnRetour2)
        recycler = findViewById(R.id.histo_recycle)
        dataBasehelper = DatabaseHelper(this)
        listHistorique = dataBasehelper.getAllHistorique()

        var historiqueAdapter = HistoryRecyclerAdapter(listHistorique)
        var layoutManager = LinearLayoutManager(applicationContext)
        recycler.layoutManager = layoutManager
        recycler.adapter = historiqueAdapter

        btnRetour2.setOnClickListener{
            finish()
        }
    }
}