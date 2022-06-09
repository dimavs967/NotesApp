package com.task.notes.data.repository

import com.task.notes.data.db.NoteDao
import com.task.notes.model.NoteModel
import com.task.notes.model.NotesModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun setNotes(notesModel: NotesModel) {
        noteDao.insertNotes(notesModel)
    }

    suspend fun getNotes(): NotesModel {
        val result = noteDao.getNotes()

        delay(1000L)
        return suspendCoroutine {
            result.let { list ->
                it.resume(list)
            }
//            if (result != null) {
//                it.resume(result)
//            }
        }
    }

}