package com.example.dermaone.view.fragment.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermaone.R

class ArticleAdapter(private val data: ArrayList<ArticleData>): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.iv_article_image)
        val title = view.findViewById<TextView>(R.id.tv_article_title)
        val description = view.findViewById<TextView>(R.id.tv_article_description)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.image.setImageResource(currentItem.image)
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return data.size
    }
}