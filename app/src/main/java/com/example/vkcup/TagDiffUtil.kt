package com.example.vkcup

import androidx.recyclerview.widget.DiffUtil

class TagDiffUtil : DiffUtil.ItemCallback<TagItem>() {

    override fun areItemsTheSame(oldItem: TagItem, newItem: TagItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TagItem, newItem: TagItem): Boolean {
        return oldItem == newItem
    }
}