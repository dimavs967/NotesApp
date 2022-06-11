package com.task.notes.data.repository.note

import com.task.notes.data.local.db.NoteDao
import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun setNotes(notesModel: NotesModel) {
        noteDao.insertNotes(notesModel)
    }

    suspend fun getNotes(): ArrayList<NoteModel>? {
        val result = noteDao.getNotes()
        return suspendCoroutine {
            result.let { list ->
                it.resume(list.list as ArrayList<NoteModel>)
            }
        }
    }

}