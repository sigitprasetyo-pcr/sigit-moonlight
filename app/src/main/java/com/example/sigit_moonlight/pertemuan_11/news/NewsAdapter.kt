package com.example.sigit_moonlight.pertemuan_11.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sigit_moonlight.databinding.ItemNewsBinding

class NewsAdapter(private var listNews: List<NewsPost>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsPost) {
            binding.tvNewsTitle.text = news.title
            binding.tvNewsDescription.text = news.contentSnippet ?: ""
            
            val imageUrl = news.image?.small ?: news.image?.large ?: ""
            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.ivThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int = listNews.size

    fun updateData(newList: List<NewsPost>) {
        listNews = newList
        notifyDataSetChanged()
    }
}