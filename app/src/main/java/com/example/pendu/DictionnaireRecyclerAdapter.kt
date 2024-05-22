package com.example.pendu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DictionnaireRecyclerAdapter(private val dictionnaireList: MutableList<String>) :
    RecyclerView.Adapter<DictionnaireRecyclerAdapter.DictionnaireViewHolder>() {

    var selectedItem: String? = null
    class DictionnaireViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val afficheMot: TextView = itemView.findViewById(R.id.affiche_mot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionnaireViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.dictionnaire_list, parent, false)
        return DictionnaireViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DictionnaireViewHolder, position: Int) {
        val currentMot = dictionnaireList[position]
        holder.afficheMot.text = currentMot
        holder.itemView.setOnClickListener {
            selectedItem = currentMot
            notifyDataSetChanged()
        }
    }
    fun addMots(mot: String) {
        dictionnaireList.add(mot)
        notifyItemInserted(dictionnaireList.size - 1)
    }

    fun updateMots(nouveauMots: List<String>) {
        dictionnaireList.clear()
        dictionnaireList.addAll(nouveauMots)
        notifyDataSetChanged()
    }

    fun deleteMot(word: String) {
        val isRemoved = dictionnaireList.removeIf { it == word }
        if (isRemoved) {
            notifyDataSetChanged()
        }
    }
    override fun getItemCount() = dictionnaireList.size
}

