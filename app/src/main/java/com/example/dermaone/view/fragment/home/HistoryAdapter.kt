package com.example.dermaone.view.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermaone.R

class HistoryAdapter (private val data: ArrayList<HistoryData>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.iv_history_image)
        val date = view.findViewById<TextView>(R.id.tv_history_date)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.image.setImageResource(currentItem.image)
        holder.date.text = currentItem.date
    }

    override fun getItemCount(): Int {
        return data.size
    }
}