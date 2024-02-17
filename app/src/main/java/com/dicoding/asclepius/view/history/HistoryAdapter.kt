package com.dicoding.asclepius.view.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.ModelHistory
import com.dicoding.asclepius.databinding.ItemHistoryBinding

class HistoryAdapter: ListAdapter<ModelHistory, HistoryAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class NewsViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(history: ModelHistory) {
            binding.apply {
                tvLabel.text = history.result
                tvScoring.text = history.scoring + "%"

                Glide.with(itemView.context)
                    .load(history.image)
                    .into(binding.imgHistory)

            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ModelHistory> =
            object : DiffUtil.ItemCallback<ModelHistory>() {
                override fun areItemsTheSame(oldUser: ModelHistory, newUser: ModelHistory): Boolean {
                    return oldUser.id == newUser.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: ModelHistory, newUser: ModelHistory): Boolean {
                    return oldUser == newUser
                }
            }
    }
}