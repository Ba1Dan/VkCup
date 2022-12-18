package com.example.vkcup

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vkcup.databinding.ItemTagBinding

class TagsAdapter : Adapter<TagsAdapter.TagViewHolder>() {

    private val differ = AsyncListDiffer(this, TagDiffUtil())

    var tags: List<TagItem>
        set(value) = differ.submitList(value)
        get() = differ.currentList

    var clickTagListener: ((tagItem: TagItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickTagListener
        )
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])
    }

    override fun getItemCount(): Int = tags.size

    class TagViewHolder(
        private val binding: ItemTagBinding,
        private var clickTagListener: ((tagItem: TagItem) -> Unit)?
    ) : ViewHolder(binding.root) {

        fun bind(tagItem: TagItem) {
            binding.tvTag.text = tagItem.name
            binding.cardTag.setOnClickListener {
                clickTagListener?.invoke(tagItem)
            }
            if (tagItem.isSelected) {
                binding.add.setAnimation(R.raw.check)
                binding.divider.visibility = View.INVISIBLE
                binding.cardTag.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.orange)
                binding.add.playAnimation()
//                binding.cardTag.setCardBackgroundColor(binding.root.context.resources.getColorStateList(R.color.orange, binding.root.context.theme))
            } else {
                binding.add.setAnimation(R.raw.add)
                binding.divider.visibility = View.VISIBLE
                binding.cardTag.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.gray)
//                binding.cardTag.setCardBackgroundColor(binding.root.context.resources.getColorStateList(R.drawable.layout_background, binding.root.context.theme))
//                ))
                binding.add.playAnimation()
            }
        }
    }
}

data class TagItem(
    val id: Int,
    val name: String,
    var isSelected: Boolean
)