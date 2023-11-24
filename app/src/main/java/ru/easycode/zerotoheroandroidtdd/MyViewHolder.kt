package ru.easycode.zerotoheroandroidtdd

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.easycode.zerotoheroandroidtdd.databinding.ItemLayoutBinding

class MyViewHolder(private val binding: ItemLayoutBinding) : ViewHolder(binding.root) {
    fun bind(source: CharSequence) {
        binding.elementTextView.text = source
    }
}