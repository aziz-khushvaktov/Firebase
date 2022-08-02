package com.example.android_firebase.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_firebase.databinding.ItemPostListBinding
import com.example.android_firebase.model.Post

class PosterAdapter :
    ListAdapter<Post,
            PosterAdapter.Vh>(MyDiffUtil()) {

    inner class Vh(
        private var itemHistoryBinding: ItemPostListBinding,
        var context: Context,
    ) :
        RecyclerView.ViewHolder(itemHistoryBinding.root) {

        fun onBind(post: Post) {
            itemHistoryBinding.apply {
                tvTitle.text = post.title
                tvBody.text = post.body
                if (post.img.isNotEmpty()) {
                    Glide.with(context)
                        .load(post.img)
                        .into(ivPicture)
                    ivPicture.isVisible = true
                }else {
                    ivPicture.isVisible = false
                }
            }
        }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPostListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}