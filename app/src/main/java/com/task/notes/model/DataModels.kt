package com.task.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NotesModel(
    val list: List<NoteModel>
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

data class NoteModel(
    val title: String,
    val description: String,
    val date: String
)