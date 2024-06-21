package com.example.dermaone.view.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dermaone.R
import com.example.dermaone.api.responses.HistoriesResponse

class HistoryAdapter (private val dataList: ArrayList<HistoriesResponse>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var onItemClick : ((HistoriesResponse) -> Unit)? = null
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.iv_history_image)
        val id = view.findViewById<TextView>(R.id.tv_history_id)
        val historyList = view.findViewById<LinearLayout>(R.id.history_list)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        Glide.with(holder.image.context)
            .load(currentItem.image)
            .into(holder.image)
        holder.id.text = "ID: ${currentItem.id}"
        holder.historyList.setOnClickListener{
            onItemClick?.invoke(currentItem)
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(data: ArrayList<HistoriesResponse>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

}