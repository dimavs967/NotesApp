package com.task.notes.data.repository.note

import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel

interface INoteRepository {

    suspend fun setNotes(notesModel: NotesModel)

    suspend fun getNotes(): ArrayList<NoteModel>?

}