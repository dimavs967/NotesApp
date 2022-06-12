package com.task.notes.data.repository.note

import com.task.notes.data.db.NoteDao
import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
): INoteRepository {

    override suspend fun setNotes(notesModel: NotesModel) {
        noteDao.insertNotes(notesModel)
    }

    override suspend fun getNotes(): ArrayList<NoteModel>? {
        return noteDao.getNotes()?.list as ArrayList<NoteModel>?
    }

}