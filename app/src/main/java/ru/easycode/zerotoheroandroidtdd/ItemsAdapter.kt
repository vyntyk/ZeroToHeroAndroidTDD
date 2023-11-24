package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ItemLayoutBinding

class ItemsAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private val itemsList = ArrayList<CharSequence>()
    fun  add(source: CharSequence){
        itemsList.add(source)
        notifyItemInserted(itemsList.size - 1)
    }
    fun save(bundle: Bundle){
        bundle.putCharSequenceArrayList("key",itemsList)
    }
    fun restore(bundle: Bundle){
        itemsList.addAll(bundle.getCharSequenceArrayList("key") ?:ArrayList())
        notifyItemRangeInserted(0, itemsList.size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }
}