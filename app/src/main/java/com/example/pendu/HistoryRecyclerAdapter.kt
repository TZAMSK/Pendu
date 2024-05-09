package com.example.pendu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryRecyclerAdapter(var histo_recycler : ArrayList<History>):
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
        holder.txtTemps.text = "${histo_recycler[position].temps} seconds"
        holder.txtDifficulte.text = histo_recycler[position].difficulte.name
    }


    override fun getItemCount(): Int {
        return histo_recycler.size
    }
}