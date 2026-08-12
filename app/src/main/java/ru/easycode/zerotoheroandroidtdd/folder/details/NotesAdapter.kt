package ru.easycode.zerotoheroandroidtdd.folder.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ItemNoteBinding

class NotesAdapter(
    private val clickListener: (NoteUi) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val list = mutableListOf<NoteUi>()

    fun update(newList: List<NoteUi>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteUi) {
            binding.noteTitleTextView.text = note.title
            binding.root.setOnClickListener { clickListener(note) }
        }
    }
}
