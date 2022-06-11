package com.task.notes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.notes.R
import com.task.notes.model.NoteModel

class NotesAdapter(
    private var onClickListener: (Int) -> Unit = {}
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var list = mutableListOf<NoteModel>()

    fun setOnClickListener(position: (Int) -> Unit) {
        onClickListener = position
    }

    fun deleteItem(position: Int): Int {
        list.removeAt(position)
        notifyItemRemoved(position)
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initAdapter(items: List<NoteModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val noteTitle = view.findViewById<TextView>(R.id.list_note_title)
        private val noteDescription = view.findViewById<TextView>(R.id.list_note_description)
        private val noteDate = view.findViewById<TextView>(R.id.note_date)

        fun bind(noteModel: NoteModel) {
            noteTitle.text = noteModel.title
            noteDescription.text = noteModel.description
            noteDate.text = noteModel.date
        }
    }

}