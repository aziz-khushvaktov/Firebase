package com.example.android_firebase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_firebase.databinding.ItemPostListBinding
import com.example.android_firebase.model.Post

class PosterAdapter:
    RecyclerView.Adapter<PosterAdapter.PosterViewHolder>() {

    var items: ArrayList<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        return PosterViewHolder(ItemPostListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(posts: ArrayList<Post>) {
        items.clear()
        items.addAll(posts)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    class PosterViewHolder(var binding: ItemPostListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(post: Post) {
            binding.apply {
                tvTitle.text = post.title
                tvBody.text = post.body
            }
        }
    }
}