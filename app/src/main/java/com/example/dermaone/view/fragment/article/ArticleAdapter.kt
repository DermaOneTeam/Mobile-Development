package com.example.dermaone.view.fragment.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dermaone.R
import com.example.dermaone.api.responses.ArticlesResponse
import com.example.dermaone.api.responses.HistoriesResponse

class ArticleAdapter(private val data: ArrayList<ArticlesResponse>): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    var onItemClick : ((ArticlesResponse) -> Unit)? = null
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.iv_article_image)
        val title = view.findViewById<TextView>(R.id.tv_article_title)
        val description = view.findViewById<TextView>(R.id.tv_article_description)
        val articleList = view.findViewById<LinearLayout>(R.id.article_list)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_list, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        Glide.with(holder.image.context)
            .load(currentItem.gambar)
            .into(holder.image)
        holder.title.text = currentItem.judul
        holder.description.text = currentItem.isi
        holder.articleList.setOnClickListener{
            onItemClick?.invoke(currentItem)
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
    fun setData(data: ArrayList<ArticlesResponse>){
        data.clear()
        data.addAll(data)
        notifyDataSetChanged()
    }
}