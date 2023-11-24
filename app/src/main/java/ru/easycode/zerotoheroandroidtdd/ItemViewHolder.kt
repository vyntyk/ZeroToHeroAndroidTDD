package ru.easycode.zerotoheroandroidtdd

import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ItemLayoutBinding

class ItemViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(source: CharSequence){
        binding.elementTextView.text = source
    }
}