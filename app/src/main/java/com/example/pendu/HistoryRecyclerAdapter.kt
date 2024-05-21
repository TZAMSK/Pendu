package com.example.pendu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pendu.Database.Historique

class HistoryRecyclerAdapter(var histo_recycler : List<Historique>):
    RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var txtMot : TextView
            var txtTemps : TextView
            var txtDifficulte : TextView

            init {
                txtMot = itemView.findViewById(R.id.txtMot)
                txtTemps = itemView.findViewById(R.id.txtTemps)
                txtDifficulte = itemView.findViewById(R.id.txtDifficulte)
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        var itemView:View = LayoutInflater.from(parent.context).inflate(R.layout.histo_list, parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtMot.text = histo_recycler[position].mot
        holder.txtTemps.text = Temps(histo_recycler[position].temps)
        holder.txtDifficulte.text = histo_recycler[position].difficulté
    }


    override fun getItemCount(): Int {
        return histo_recycler.size
    }

    fun Temps(temps:Long):String{
        val heures = (temps / 3600000) % 24
        val minutes = (temps / 60000) % 60
        val secondes = (temps / 1000) % 60

        return String.format("%02dh %02dm %02ds", heures, minutes, secondes)

    }
}