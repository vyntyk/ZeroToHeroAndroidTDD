package ru.easycode.zerotoheroandroidtdd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private val items: List<String>,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val elementTextView: TextView =
            itemView.findViewById(R.id.elementTextView)

        private val deleteButton: Button =
            itemView.findViewById(R.id.deleteButton)

        fun bind(item: String) {
            elementTextView.text = item
            deleteButton.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }
}
