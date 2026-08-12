package ru.easycode.zerotoheroandroidtdd.folder.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ItemFolderBinding

class FoldersAdapter(
    private val clickListener: (FolderUi) -> Unit
) : RecyclerView.Adapter<FoldersAdapter.FolderViewHolder>() {

    private val list = mutableListOf<FolderUi>()

    fun update(newList: List<FolderUi>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val binding = ItemFolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FolderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class FolderViewHolder(private val binding: ItemFolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(folder: FolderUi) {
            binding.folderTitleTextView.text = folder.title
            binding.folderCountTextView.text = folder.notesCount.toString()
            binding.root.setOnClickListener { clickListener(folder) }
        }
    }
}
